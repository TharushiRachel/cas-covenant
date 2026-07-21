package com.itechro.cas.security;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itechro.cas.exception.BaseException;
import com.itechro.cas.exception.impl.AppsErrorMessage;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ExceptionMessageFinder;
import com.itechro.cas.model.dto.audit.WebAuditDTO;
import com.itechro.cas.model.dto.master.UserDTO;
import com.itechro.cas.model.dto.master.UserLoginFailedResponse;
import com.itechro.cas.model.security.CasUserDetails;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.model.security.LoginCredentials;
import com.itechro.cas.service.audit.WebAuditService;
import com.itechro.cas.service.master.SystemParameterService;
import com.itechro.cas.util.PasswordUtil;
import com.itechro.cas.util.WebAuditUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.crypto.Cipher;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.util.*;
import java.util.stream.Collectors;


public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger LOG = LoggerFactory.getLogger(JWTLoginFilter.class);

    private SecurityService securityService;
    private PrivateKey privateKey;
    private SystemParameterService systemParameterService;
    private Boolean activeDirectoryEnabled;
    private WebAuditService webAuditService;
    private ExceptionMessageFinder exceptionMessageFinder;


    public JWTLoginFilter(String url, AuthenticationManager authManager, SecurityService securityService,
                          SystemParameterService systemParameterService, PrivateKey privateKey,
                          WebAuditService webAuditService, ExceptionMessageFinder exceptionMessageFinder) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.securityService = securityService;
        this.systemParameterService = systemParameterService;
        activeDirectoryEnabled = this.systemParameterService.isActiveDirectoryEnabled();
        this.privateKey = privateKey;
        this.webAuditService = webAuditService;
        this.exceptionMessageFinder = exceptionMessageFinder;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws
            AuthenticationException, IOException, ServletException {
        Authentication auth = null;
        LoginCredentials credentials = null;
        try {

//            LOG.info("-----------------> JWTLoginFilter : attemptAuthentication -------------->");

            credentials = new ObjectMapper().readValue(req.getInputStream(),
                    LoginCredentials.class);
            String decodedPassword = rsaDecodePassword(credentials.getPassword(), privateKey);
            String userIdentifiableToken = credentials.getUsername();
            if (activeDirectoryEnabled) {
                String combinedUserDetailsStr = PasswordUtil.getCombinedUserDetailsStr(userIdentifiableToken, decodedPassword);
                UserDTO userDTO = this.securityService.getUserByUsername(combinedUserDetailsStr);

                SortedSet<String> privileges = userDTO.getPrivileges();
                List<GrantedAuthority> authorityList = privileges.stream().map((privilege) -> new SimpleGrantedAuthority
                        (privilege)).collect(Collectors.toList());
                CasUserDetails userDetails = new CasUserDetails(userDTO);
                auth = new UsernamePasswordAuthenticationToken(userDetails, null, authorityList);
            } else {
                //                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userIdentifiableToken,
//                        decodedPassword,
//                        Collections.emptyList());
//                auth = getAuthenticationManager().authenticate(token);

                UserDTO userDTO = this.securityService.getUserDetailsByUserAD(userIdentifiableToken);

                SortedSet<String> privileges = userDTO.getPrivileges();
                List<GrantedAuthority> authorityList = privileges.stream().map((privilege) -> new SimpleGrantedAuthority
                        (privilege)).collect(Collectors.toList());
                CasUserDetails userDetails = new CasUserDetails(userDTO);
                auth = new UsernamePasswordAuthenticationToken(userDetails, null, authorityList);
            }
        } catch (Exception e) {
            String message = null;

            LOG.info("Login Request :: {}", credentials);

            if (e instanceof BaseException) {
                BaseException baseException = (BaseException) e;
                List<AppsErrorMessage> errorMessages = this.exceptionMessageFinder.getMessages(baseException.getAppsErrorMessages());
                if (!errorMessages.isEmpty()) {
                    message = errorMessages.get(0).getErrorMessage();
                } else {
                    message = "Login error. Please contact system administrator";
                }
            } else if (e instanceof JsonMappingException) {
                message = "Malformed login request";
                logger.warn("Invalid login request JSON format", e);
            } else if (e instanceof BadCredentialsException) {
                message = "Invalid login credentials";
                logger.warn("Invalid login credentials", e);
            } else {
                message = "Unknown login error. Please contact system administrator";
                logger.warn(message);
                logger.warn(e);
            }

            AuthenticationException ae = new AuthenticationException(message, e) {
                @Override
                public synchronized Throwable initCause(Throwable cause) {
                    return super.initCause(e);
                }
            };

            unsuccessfulAuthentication(req, res, ae);

        }

        return auth;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication authentication) throws
            IOException, ServletException {

        UserDTO user = null;
        Date date = new Date();
        CredentialsDTO credentialsDTO = new CredentialsDTO();

//        LOG.info("-----------------> JWTLoginFilter : attemptAuthentication -------------->");

        try {
            if (activeDirectoryEnabled) {
                String userName = authentication.getName();
                String userPassword = this.securityService.getUserCredentials(userName);
                String combinedUserDetailsStr = PasswordUtil.getCombinedUserDetailsStr(userName, userPassword);
                user = securityService.getUserByUsername(combinedUserDetailsStr);
            } else {
                user = securityService.getUserDetailsByUserAD(authentication.getName());
            }
            credentialsDTO.setUserID(user.getUserID());
            credentialsDTO.setUserName(user.getUserName());
            credentialsDTO.setDivCode(user.getDivCode());
            credentialsDTO.setUpmGroupCode(user.getUpmGroupCode());
            credentialsDTO.setAgent(user.isAgent());

            //Audit
            WebAuditDTO webAuditDTO = WebAuditUtils.constructLogInAudit(credentialsDTO, date);
            webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        } catch (AppsException e) {
            LOG.warn("Error in post authentication user extraction for request : {} : ", req);
        }

        TokenAuthenticationService.addAuthentication(res, user);
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {

        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType(MediaType.APPLICATION_JSON.toString());

        UserLoginFailedResponse loginResponse = new UserLoginFailedResponse();
        loginResponse.setMessage(failed.getMessage());
        response.setStatus(401);
        response.getWriter().write(objectMapper.writeValueAsString(loginResponse));

    }

    private String rsaDecodePassword(String password, PrivateKey privateKey) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(password)));
    }

}

package com.itechro.cas.security;

import com.itechro.cas.exception.impl.AppsCommonErrorCode;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.AppsRuntimeException;
import com.itechro.cas.model.dto.master.UserDTO;
import com.itechro.cas.model.security.CasUserDetails;
import com.itechro.cas.model.security.LoginCredentials;
import com.itechro.cas.service.master.SystemParameterService;
import com.itechro.cas.util.PasswordUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

@Service
public class JWTAuthenticationFilter extends GenericFilterBean {

    private static final Logger LOG = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
    private String[] ignoredAntPatterns;
    private SecurityService securityService;
    private SystemParameterService systemParameterService;
    private Boolean activeDirectoryEnabled;


    public JWTAuthenticationFilter(SecurityService securityService,
                                   SystemParameterService systemParameterService, String[] ignoredAntPatterns) {
        this.ignoredAntPatterns = ignoredAntPatterns;
        this.securityService = securityService;
        this.systemParameterService = systemParameterService;
        activeDirectoryEnabled = this.systemParameterService.isActiveDirectoryEnabled();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
            ServletException {
        Authentication authentication = null;
        try {
            if (request instanceof HttpServletRequest && isAuthenticatedRoute(request)) {
                LoginCredentials username = (LoginCredentials) TokenAuthenticationService.authenticateAndGetUsername(
                        (HttpServletRequest) request);
                if (username != null) {
                    authentication = this.getAuthenticationFromUsername(username);
                }
            }
        } catch (ExpiredJwtException e) {
            LOG.info("INFO: doFilter | JWTAuthenticationFilter | {} ", e.getMessage());
        }  catch (io.jsonwebtoken.JwtException e) {
            LOG.error("INFO: doFilter | JWTAuthenticationFilter | Invalid JWT: {}", e.getMessage(), e);
        }  catch (Exception e) {
            LOG.warn("INFO: doFilter | JWTAuthenticationFilter | {} ", e.getMessage());
        } finally {
            try{
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                LOG.error(" doFilter | JWTAuthenticationFilter | Error during filter chain processing: {}", e.getMessage());
                throw new ServletException("doFilter | JWTAuthenticationFilter | Error during filter chain processing", e);
            }

        }
    }

    private boolean isAuthenticatedRoute(ServletRequest request) {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        for (String ignoredPattern : this.ignoredAntPatterns) {
            if ((new AntPathRequestMatcher(ignoredPattern)).matches(httpReq)) {
                return false;
            }
        }
        return true;
    }

    private Authentication getAuthenticationFromUsername(LoginCredentials account) {
        UserDTO user;
        try {

//            LOG.info("-----------------> JWTAuthenticationFilter : getAuthenticationFromUsername --------------> {}" , account);

            Boolean activeDirectoryEnabled = this.systemParameterService.isActiveDirectoryEnabled();
            if (activeDirectoryEnabled) {
                String userPassword = this.securityService.getUserCredentials(account.getUsername());
                String combinedUserDetailsStr = PasswordUtil.getCombinedUserDetailsStr(account.getUsername(), userPassword);
                user = securityService.getUserByUsername(combinedUserDetailsStr);
            } else {
                user = securityService.getUserDetailsByUserAD(account.getUsername());
            }
        } catch (AppsException e) {
            if (e.containsErrorCode(AppsCommonErrorCode.APPS_EMPTY_RESULT)) {
                LOG.warn("User with name {} does not exist", account);
            } else {
                LOG.warn("Getting user for {} failed", account);
                throw new AppsRuntimeException();
            }
            return null;
        }

        SortedSet<String> privileges = user.getPrivileges();
        List<GrantedAuthority> authorityList = privileges.stream().map((privilege) -> new SimpleGrantedAuthority
                (privilege)).collect(Collectors.toList());
        CasUserDetails userDetails = new CasUserDetails(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorityList);
        return authentication;

    }


}

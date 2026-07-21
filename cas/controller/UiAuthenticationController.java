package com.itechro.cas.controller;

import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.model.dto.master.UserDTO;
import com.itechro.cas.model.security.ApiKeys;
import com.itechro.cas.model.security.LoginCredentials;
import com.itechro.cas.model.security.TokenDTO;
import com.itechro.cas.security.SecurityService;
import com.itechro.cas.security.TokenAuthenticationService;
import com.itechro.cas.service.master.SystemParameterService;
import com.itechro.cas.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UiAuthenticationController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(UiAuthenticationController.class);

    @Autowired
    private String publicKeyString;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private SystemParameterService systemParameterService;


    @ResponseExceptionHandler
    @RequestMapping(value = "${api.prefix}/public_key", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<ApiKeys> getPublicKey() throws Exception {
        ApiKeys apiKeys = new ApiKeys();
        apiKeys.setPublicKey(publicKeyString);
        return new ResponseEntity<ApiKeys>(apiKeys, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "${api.prefix}/getToken", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<TokenDTO> getToken(HttpServletRequest request) throws Exception {

        TokenDTO tokenDTO = new TokenDTO();
        HttpStatus httpStatus;
        LoginCredentials userCredentials = new LoginCredentials();

        try {
            userCredentials = TokenAuthenticationService.authenticateAndGetUsername(request);

            LOG.debug("generate jwt token using refresh token started for user {} ", userCredentials.getUsername());
            Boolean activeDirectoryEnabled = this.systemParameterService.isActiveDirectoryEnabled();
            UserDTO user;
            if (activeDirectoryEnabled) {
                String userPassword = this.securityService.getUserCredentials(userCredentials.getUsername());
                String combinedUserDetailsStr = PasswordUtil.getCombinedUserDetailsStr(userCredentials.getUsername(), userPassword);
                user = securityService.getUserByUsername(combinedUserDetailsStr);
            } else {
                user = securityService.getUserDetailsByUserAD(userCredentials.getUsername());
            }
            tokenDTO.setToken(TokenAuthenticationService.generateAccessToken(user));
            tokenDTO.setRefreshToken(TokenAuthenticationService.generateRefreshToken(user));

            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            LOG.debug("generate jwt token using refresh token error for user {} ", userCredentials.getUsername(), e);
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(tokenDTO, httpStatus);
    }
}

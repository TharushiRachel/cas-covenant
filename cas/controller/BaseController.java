package com.itechro.cas.controller;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.security.CasUserDetails;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityService.class);

    protected CredentialsDTO getCredentialsDTO() throws AccessDeniedException {

        CredentialsDTO credentialsDTO = new CredentialsDTO();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null){
            LOG.info("Authentication is null");
        }else {
            LOG.info("Authentication {}", authentication);
            LOG.info("Authentication Principle {}", authentication.getPrincipal().toString());
        }

        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new AccessDeniedException(AppsConstants.AuthorizationError.USER_UNAUTHORIZED.getDescription());
        }

        CasUserDetails userDetails = (CasUserDetails) authentication.getPrincipal();

        credentialsDTO.setUserID(userDetails.getUserId());
        credentialsDTO.setUserName(userDetails.getUsername());
        credentialsDTO.setUpmGroupCode(userDetails.getUpmGroupCode());
        credentialsDTO.setDivCode(userDetails.getDivCode());
        credentialsDTO.setDisplayName(userDetails.getDisplayName());
        credentialsDTO.setRequestIpAddress(request.getRemoteAddr());
        credentialsDTO.setAuthorities(userDetails.getAuthorities());
        credentialsDTO.setAgent(userDetails.isAgent());
        credentialsDTO.setIsAssistant(userDetails.getIsAssistant());

        return credentialsDTO;
    }
}

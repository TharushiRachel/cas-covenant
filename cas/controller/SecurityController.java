package com.itechro.cas.controller;

import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.model.dto.integration.response.UpmDetailResponse;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.security.SecurityService;
import com.itechro.cas.service.master.SystemParameterService;
import com.itechro.cas.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "${api.prefix}/security")
public class SecurityController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityController.class);

    @Autowired
    private SecurityService securityService;

    @Autowired
    private SystemParameterService systemParameterService;

    @RequestMapping(value = "/log-out", headers = "Accept=application/json", method = RequestMethod.POST)
    public void expireUserCache() {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : expire cache for user : {} ", credentialsDTO.getUserName());

        try {
            String userDetailsStr;
            if (this.systemParameterService.isActiveDirectoryEnabled()) {
                String userCredentials = this.securityService.getUserCredentials(credentialsDTO.getUserName());
                userDetailsStr = PasswordUtil.getCombinedUserDetailsStr(credentialsDTO.getUserName(), userCredentials);
            } else {
                userDetailsStr = credentialsDTO.getUserName();
            }
            this.securityService.expireUserFromCache(userDetailsStr);
        } catch (Exception e) {
            LOG.warn("user cache expiration failed for user {}", credentialsDTO.getUserName(), e);
        }

        LOG.info("END : expire cache for user : {} ", credentialsDTO.getUserName());
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/destroyUserCache", headers = "Accept=application/json", method = RequestMethod.GET)
    public void destroyUserCache() throws Exception {
        Date date = new Date();
        LOG.info("START : Destroy user cache {}", date);
        this.securityService.destroyUserCache();
        LOG.info("END : Destroy user cache {}", date);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getUPMDetails/{adUserID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<UpmDetailResponse> getUPCSectionDTOByID(@PathVariable String adUserID) throws Exception {

        LOG.info("START : Get UPM details by ad user ID: {}", adUserID);

        UpmDetailResponse userUPMDetails = this.securityService.getUserUPMDetails(adUserID);

        LOG.info("END : Get UPM details by ad user ID: {}", adUserID);

        return new ResponseEntity<>(userUPMDetails, HttpStatus.OK);

    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getUPMDetailsById/{adUserID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<UpmDetailResponse> getUPMDetailsById(@PathVariable String adUserID) throws Exception {

        LOG.info("START : Get UPM details by ad user ID: {}", adUserID);

        UpmDetailResponse userUPMDetails = this.securityService.getUserUPMDetailsById(adUserID);

        LOG.info("END : Get UPM details by ad user ID: {}", adUserID);

        return new ResponseEntity<>(userUPMDetails, HttpStatus.OK);

    }
}

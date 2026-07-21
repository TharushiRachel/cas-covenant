package com.itechro.cas.controller.externalsite;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.model.dto.externalsite.ExternalSiteDTO;
import com.itechro.cas.model.dto.systemintegration.SystemIntegrationDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.externalsite.ExternalSiteService;
import com.itechro.cas.service.integration.IntegrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "${api.prefix}/externalSite")
public class ExternalSiteController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(ExternalSiteController.class);
    @Autowired
    private ExternalSiteService externalSiteService;

    @Autowired
    private IntegrationService integrationService;

    @ResponseExceptionHandler
    @RequestMapping(value = "/getExternalSiteDetails", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<ExternalSiteDTO> getExternalSiteDetailController() throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get External Site Detail : {}, user {}", credentialsDTO.getUserID());

        ExternalSiteDTO externalSiteDTO = this.externalSiteService.getExternalSiteDetailService(credentialsDTO);

        LOG.info("END : Get External Site Detail  : {}, user {}", externalSiteDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(externalSiteDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getUserApplicationDetails", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<SystemIntegrationDTO>> getUserApplicationDetails() throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get All User Application Details : user {}", credentialsDTO.getUserID());

        List<SystemIntegrationDTO> systemIntegrationDTOList = integrationService.getUserApplicationList(credentialsDTO.getUserID());

        LOG.info("END : Get All User Application Details : user {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(systemIntegrationDTOList, HttpStatus.OK);
    }
}

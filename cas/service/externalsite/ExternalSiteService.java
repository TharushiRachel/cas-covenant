package com.itechro.cas.service.externalsite;

import com.itechro.cas.model.dto.externalsite.ExternalSiteDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.acae.ACAEBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ExternalSiteService {

    private static final Logger LOG = LoggerFactory.getLogger(ACAEBaseService.class);

    @Value("${apps.integration.load.external.site.url}")
    private String externalSiteURL;

    @Value("${apps.integration.load.external.site.enable}")
    private boolean externalSiteEnable;

    public ExternalSiteDTO getExternalSiteDetailService(CredentialsDTO credentialsDTO) throws Exception {

        LOG.info("START : Get External Site Detail Service: {} , user {}",  credentialsDTO);

        ExternalSiteDTO externalSiteDTO = new ExternalSiteDTO();

        externalSiteDTO.setEnable(externalSiteEnable);
        externalSiteDTO.setUrl(externalSiteURL);

        LOG.info("END : Get External Site Detail Service: {} , user {}", credentialsDTO);

        return externalSiteDTO;
    }
}

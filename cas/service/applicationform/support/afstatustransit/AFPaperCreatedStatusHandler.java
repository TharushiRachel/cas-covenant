package com.itechro.cas.service.applicationform.support.afstatustransit;

import com.itechro.cas.config.CasProperties;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.applicationform.ApplicationForm;
import com.itechro.cas.model.dto.applicationform.ApplicationFormStatusChangeRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.applicationform.support.AFStatusHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AFPaperCreatedStatusHandler extends AFStatusHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AFPaperCreatedStatusHandler.class);

    public AFPaperCreatedStatusHandler(ApplicationForm applicationForm, CredentialsDTO credentialsDTO, ApplicationFormStatusChangeRQ applicationFormStatusChangeRQ, CasProperties casProperties) {
        super(applicationForm, credentialsDTO, applicationFormStatusChangeRQ, casProperties);
    }


    @Override
    public void recordStatusHistory() throws AppsException {
    }

}

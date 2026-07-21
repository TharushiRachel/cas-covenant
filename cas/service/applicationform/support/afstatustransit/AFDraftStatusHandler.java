package com.itechro.cas.service.applicationform.support.afstatustransit;

import com.itechro.cas.config.CasProperties;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.applicationform.ApplicationForm;
import com.itechro.cas.model.dto.applicationform.ApplicationFormStatusChangeRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.applicationform.support.AFStatusHandler;

public class AFDraftStatusHandler extends AFStatusHandler {

    public AFDraftStatusHandler(ApplicationForm applicationForm, CredentialsDTO credentialsDTO, ApplicationFormStatusChangeRQ applicationFormStatusChangeRQ, CasProperties casProperties) {
        super(applicationForm, credentialsDTO, applicationFormStatusChangeRQ, casProperties);
    }

    @Override
    public void updateApplicationForm() throws AppsException {
        applicationForm.setAssignUserUpmID(applicationFormStatusChangeRQ.getAssignUserUpmID());
        applicationForm.setAssignUser(applicationFormStatusChangeRQ.getAssignUser());
        applicationForm.setAssignUserID(applicationFormStatusChangeRQ.getAssignUserID());
        applicationForm.setAssignUserUpmGroupCode(applicationFormStatusChangeRQ.getAssignUserUpmGroupCode());
        applicationForm.setAssignUserDisplayName(applicationFormStatusChangeRQ.getAssignUserDisplayName());
        applicationForm.setAssignUserDivCode(applicationFormStatusChangeRQ.getAssignUserDivCode());
    }
}

package com.itechro.cas.service.lead.support.leadstatustransit;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.config.CasProperties;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.lead.Lead;
import com.itechro.cas.model.dto.lead.LeadStatusUpdateRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.lead.support.LeadStatusHandler;

public class LeadDeclineHandler extends LeadStatusHandler {

    public LeadDeclineHandler(Lead lead, LeadStatusUpdateRQ leadStatusUpdateRQ, CredentialsDTO credentialsDTO, CasProperties casProperties) {
        super(lead, leadStatusUpdateRQ, credentialsDTO, casProperties);
    }

    @Override
    public boolean isValidStatusTransition() {
        return lead.getLeadStatus() == DomainConstants.LeadStatus.SUBMITTED;
    }

    @Override
    public void updateLead() throws AppsException {
        lead.setAssignUserID(credentialDTO.getUserName());
    }
}

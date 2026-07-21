package com.itechro.cas.service.notification.support;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.email.EmailSendRequest;
import com.itechro.cas.model.dto.lead.LeadStatusTransitionRQ;
import com.itechro.cas.model.dto.lead.LeadStatusUpdateRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.email.CASEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

public class LeadEmailSender {

    private CASEmailService casEmailService;

    private Environment environment;

    private LeadStatusUpdateRQ leadStatusUpdateRQ;

    private LeadStatusTransitionRQ leadStatusTransitionRQ;

    private CredentialsDTO credentialsDTO;

    private static final Logger LOG = LoggerFactory.getLogger(LeadEmailSender.class);

    public LeadEmailSender(LeadStatusTransitionRQ leadStatusTransitionRQ) {
        this.leadStatusTransitionRQ = leadStatusTransitionRQ;
        this.credentialsDTO = leadStatusTransitionRQ.getCredentialsDTO();

    }

    public void sendLeadUpdateEmail() {
        this.sendEmail();
    }

    private void sendEmail() {

        String subject = "Business Lead Generation System - Lead Ref No - ".concat(leadStatusTransitionRQ.getLeadRefNo()).concat(" - ").concat(DomainConstants.LeadStatusForEmailSubject.resolveStatus(leadStatusTransitionRQ.getNewStatus().toString()).getDescription());
        EmailSendRequest emailSendRequest = new EmailSendRequest();
        emailSendRequest.setSubject(subject);
        emailSendRequest.setPurpose(DomainConstants.EmailPurpose.OTHER);
        for (String address : leadStatusTransitionRQ.getToAddresses()) {
            emailSendRequest.addToAddress(address);
        }
        emailSendRequest.setSendType(DomainConstants.EmailSendType.HTML);
        emailSendRequest.setTemplateName("lead_update");
        Map<String, Object> params = new HashMap<>();
        params.put("leadStatusTransitionRQ", leadStatusTransitionRQ);
        emailSendRequest.setParams(params);

        try {
            this.casEmailService.sendMailAsync(emailSendRequest, credentialsDTO);
        } catch (AppsException e) {
            LOG.error("ERROR : Email sending failed : {}", emailSendRequest, e);
        }

    }

    public void setCasEmailService(CASEmailService casEmailService) {
        this.casEmailService = casEmailService;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


}

package com.itechro.cas.service.notification.support;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.applicationform.ApplicationFormStatusTransitionRQ;
import com.itechro.cas.model.dto.email.EmailSendRequest;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperStatusTransitionRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.email.CASEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

public class ApplicationFormEmailSender {

    private CASEmailService casEmailService;

    private Environment environment;

    private ApplicationFormStatusTransitionRQ applicationFormStatusTransitionRQ;

    private CredentialsDTO credentialsDTO;

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationFormEmailSender.class);

    public ApplicationFormEmailSender (ApplicationFormStatusTransitionRQ applicationFormStatusTransitionRQ) {
        this.applicationFormStatusTransitionRQ = applicationFormStatusTransitionRQ;
        this.credentialsDTO = applicationFormStatusTransitionRQ.getCredentialsDTO();
    }

    public void sendApplicationFormUpdateEmail() {
        this.sendEmail();
    }

    private void sendEmail() {

        String subject = "Business Lead Generation System - Application Form Ref No - ".concat(applicationFormStatusTransitionRQ.getAfRefNumber()).concat(" - ").concat(DomainConstants.ApplicationFormStatusForEmailSubject.resolveStatus(applicationFormStatusTransitionRQ.getNewStatus().toString()).getDescription());
        EmailSendRequest emailSendRequest = new EmailSendRequest();
        emailSendRequest.setSubject(subject);
        emailSendRequest.setPurpose(DomainConstants.EmailPurpose.OTHER);
        for (String address : applicationFormStatusTransitionRQ.getToAddresses()) {
            emailSendRequest.addToAddress(address);

        }
        emailSendRequest.setSendType(DomainConstants.EmailSendType.HTML);
        emailSendRequest.setTemplateName("application_form_update");
        Map<String, Object> params = new HashMap<>();
        params.put("applicationFormStatusTransitionRQ", applicationFormStatusTransitionRQ);
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

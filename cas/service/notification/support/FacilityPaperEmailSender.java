package com.itechro.cas.service.notification.support;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.email.EmailSendRequest;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperStatusTransitionRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.email.*;
import com.itechro.cas.service.integration.IntegrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

public class FacilityPaperEmailSender {

    private CASEmailService casEmailService;

    private Environment environment;

    private FacilityPaperStatusTransitionRQ facilityPaperStatusTransitionRQ;

    private CredentialsDTO credentialsDTO;

    @Autowired
    private IntegrationService integrationService;

    private static final Logger LOG = LoggerFactory.getLogger(FacilityPaperEmailSender.class);

    public FacilityPaperEmailSender(FacilityPaperStatusTransitionRQ facilityPaperStatusTransitionRQ) {
        this.facilityPaperStatusTransitionRQ = facilityPaperStatusTransitionRQ;
        this.credentialsDTO = facilityPaperStatusTransitionRQ.getCredentialsDTO();
    }

    public void sendFacilityPaperUpdateEmail() {
        this.sendEmail();
    }

    private void sendEmail() {

        String subject = "CAS Paper Reference Number - ".concat(facilityPaperStatusTransitionRQ.getFacilityPaperRefNumber()).concat(" - ").concat(DomainConstants.FacilityPaperStatusForEmailSubject.resolveStatus(facilityPaperStatusTransitionRQ.getNewStatus().toString()).getDescription());
        EmailSendRequest emailSendRequest = new EmailSendRequest();
        emailSendRequest.setSubject(subject);
        emailSendRequest.setPurpose(DomainConstants.EmailPurpose.OTHER);
        for (String address : facilityPaperStatusTransitionRQ.getToAddresses()) {
            emailSendRequest.addToAddress(address);
            LOG.error("INFO : Email sending address : {}", address);
        }

        for(String cCAddress : facilityPaperStatusTransitionRQ.getCcAddress()){
            emailSendRequest.addCcAddress(cCAddress);
            LOG.error("INFO : Email sending cCAddress : {}", cCAddress);
        }

        emailSendRequest.setSendType(DomainConstants.EmailSendType.HTML);
        emailSendRequest.setTemplateName("facility_paper_update");
        Map<String, Object> params = new HashMap<>();
        params.put("facilityPaperStatusTransitionRQ", facilityPaperStatusTransitionRQ);
        emailSendRequest.setParams(params);

        try {
            LOG.error("INFO : Email sending success : {}", emailSendRequest);
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

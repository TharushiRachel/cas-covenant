package com.itechro.cas.service.notification.support;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.coveringApproval.CoveringApprovalStatusTransitionRQ;
import com.itechro.cas.model.dto.email.EmailSendRequest;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.email.CASEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author tharushi
 */
public class CoveringApprovalEmailSender {

    private CASEmailService casEmailService;

    private Environment environment;

    private CredentialsDTO credentialsDTO;

    private CoveringApprovalStatusTransitionRQ coveringApprovalStatusTransitionRQ;

    private static final Logger LOG = LoggerFactory.getLogger(CoveringApprovalEmailSender.class);

    public CoveringApprovalEmailSender(CoveringApprovalStatusTransitionRQ coveringApprovalStatusTransitionRQ) {
        this.coveringApprovalStatusTransitionRQ = coveringApprovalStatusTransitionRQ;
        this.credentialsDTO =coveringApprovalStatusTransitionRQ.getCredentialsDTO();
    }


    public void sendCoveringApprovalUpdateEmail(){
        this.sendEmail();
    }

    private void sendEmail() {
        LOG.info("Preparing email content for: {}", coveringApprovalStatusTransitionRQ);
        if (coveringApprovalStatusTransitionRQ != null) {
            String subject = "Business Lead Generation System - Covering Approval Ref No - ".concat(coveringApprovalStatusTransitionRQ.getCovAppRefNumber()).concat(" - ").concat(DomainConstants.CoveringApprovalStatusForEmailSubject.resolveStatus(coveringApprovalStatusTransitionRQ.getNewStatus().toString()).getDescription());
            EmailSendRequest emailSendRequest = new EmailSendRequest();
            emailSendRequest.setSubject(subject);
            emailSendRequest.setPurpose(DomainConstants.EmailPurpose.OTHER);

            for (String address : coveringApprovalStatusTransitionRQ.getToAddresses()) {
                emailSendRequest.addToAddress(address);
            }

            emailSendRequest.setSendType(DomainConstants.EmailSendType.HTML);
            emailSendRequest.setTemplateName("covering_approval_update");
            Map<String, Object> params = new HashMap<>();
            params.put("coveringApprovalStatusTransitionRQ", coveringApprovalStatusTransitionRQ);
            emailSendRequest.setParams(params);

            try {
                casEmailService.sendMailAsync(emailSendRequest, credentialsDTO);
            } catch (AppsException e) {
                LOG.error("ERROR : Email sending failed : {}", emailSendRequest, e);
            }
        }
    }

    public void setCasEmailService(CASEmailService casEmailService) {
        this.casEmailService = casEmailService;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}

package com.itechro.cas.service.notification.support;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.email.EmailSendRequest;
import com.itechro.cas.model.dto.master.UserDTO;
import com.itechro.cas.service.email.CASEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

public class PasswordSender {

    private static final Logger LOG = LoggerFactory.getLogger(PasswordSender.class);

    private static final String NEW_USER_MURAPADAYA_EMAIL_SUBJECT = "apps.notification.murapadaya.generated.email.subject";

    private static final String MURAPADAYA_RESET_EMAIL_SUBJECT = "apps.notification.murapadaya.reset.email.subject";

    private UserDTO userDTO;

    private String password;

    private CASEmailService casEmailService;

    private boolean isPasswordReset;

    private Environment environment;

    public PasswordSender(UserDTO userDTO, String password, boolean isPasswordReset) {
        this.userDTO = userDTO;
        this.password = password;
        this.isPasswordReset = isPasswordReset;
    }

    public void sendPassword() {
        this.init();
        this.sendEmail();
    }

    public void init() {
    }

    private void sendEmail() {
        EmailSendRequest emailSendRequest = new EmailSendRequest();

        if (this.isPasswordReset) {
            emailSendRequest.setSubject(environment.getProperty(MURAPADAYA_RESET_EMAIL_SUBJECT));
        } else {
            emailSendRequest.setSubject(environment.getProperty(NEW_USER_MURAPADAYA_EMAIL_SUBJECT));
        }

        emailSendRequest.setPurpose(DomainConstants.EmailPurpose.OTHER);
        emailSendRequest.addToAddress(this.userDTO.getEmail());

        Map<String, Object> params = new HashMap<>();
        params.put("user", this.userDTO);
        params.put("password", this.password);
        params.put("isPasswordReset", this.isPasswordReset);
        params.put("systemURL", this.environment.getProperty("apps.cas.agent.login.url"));

        String clientName = this.environment.getProperty("apps.cas.default.client.name").toLowerCase();
//        params.put("logoUrl", this.environment.getProperty("apps.iharvest." + clientName + ".external.logo.url"));

        emailSendRequest.setParams(params);
        emailSendRequest.setSendType(DomainConstants.EmailSendType.HTML);
        emailSendRequest.setTemplateName("password_change");
        emailSendRequest.setClientName(clientName.toUpperCase());
        try {
            this.casEmailService.sendMail(emailSendRequest);
        } catch (AppsException e) {
            //LOG.error("EMail sending failed : {}", emailSendRequest, e);
        }
    }

    public CASEmailService getCasEmailService() {
        return casEmailService;
    }

    public void setCasEmailService(CASEmailService casEmailService) {
        this.casEmailService = casEmailService;
    }
}

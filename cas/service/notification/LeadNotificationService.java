package com.itechro.cas.service.notification;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.notification.EmailUnSubscribeDao;
import com.itechro.cas.dao.notification.jdbc.EmailUnSubscribeJdbcDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.notification.EmailUnSubscribe;
import com.itechro.cas.model.dto.integration.request.UmpDetailLoadByAdIdRQ;
import com.itechro.cas.model.dto.integration.response.UpmDetailResponse;
import com.itechro.cas.model.dto.lead.LeadStatusTransitionRQ;
import com.itechro.cas.model.dto.lead.LeadStatusUpdateRQ;
import com.itechro.cas.model.dto.master.UserDTO;
import com.itechro.cas.model.dto.notification.EmailUnSubscribeDTO;
import com.itechro.cas.model.dto.notification.EmailUnSubscribeRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.email.CASEmailService;
import com.itechro.cas.service.integration.IntegrationService;
import com.itechro.cas.service.notification.support.LeadEmailSender;
import com.itechro.cas.service.notification.support.PasswordSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LeadNotificationService implements EnvironmentAware {

    private static final Logger LOG = LoggerFactory.getLogger(LeadNotificationService.class);

    private Environment environment;

    @Autowired
    private CASEmailService casEmailService;

    @Autowired
    private EmailUnSubscribeDao emailUnSubscribeDao;

    @Autowired
    private EmailUnSubscribeJdbcDao emailUnSubscribeJdbcDao;

    @Autowired
    private IntegrationService integrationService;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void notifyLeadUpdate(LeadStatusTransitionRQ emailRQ) throws AppsException {

        LOG.info("START : Lead update email notification for assigned user: {} ", emailRQ.getUserName());
        EmailUnSubscribeDTO emailUnSubscribeDTO = null;
        LeadEmailSender leadEmailSender = new LeadEmailSender(emailRQ);
        emailUnSubscribeDTO = emailUnSubscribeJdbcDao.getEmailUnSubscribeDTOByUser(emailRQ.getUserName(), null);

        if (emailRQ.getToAddresses() == null) {
            UmpDetailLoadByAdIdRQ rq = new UmpDetailLoadByAdIdRQ();
            rq.setAdUserID(emailRQ.getUserName());
            UpmDetailResponse response = integrationService.getUpmDetailsByAdUserIdAndAppCode(rq);

            if (response.getEmail() != null) {
                List<String> emailAddressFromUpm = new ArrayList<>();
                emailAddressFromUpm.add(response.getEmail());
                emailRQ.setToAddresses(emailAddressFromUpm);

                if (emailUnSubscribeDTO != null) {
                    if (emailUnSubscribeDTO.getStatus() == AppsConstants.Status.ACT) {
                        LOG.info("END : Lead update email notification unsubscribe by assigned user: {} ", emailRQ.getUserName());
                    } else {

                        leadEmailSender.setCasEmailService(casEmailService);
                        leadEmailSender.setEnvironment(environment);
                        leadEmailSender.sendLeadUpdateEmail();
                        LOG.info("END : Lead update email notification for assigned user: {} ", emailRQ.getUserName());
                    }
                } else {
                    leadEmailSender.setCasEmailService(casEmailService);
                    leadEmailSender.setEnvironment(environment);
                    leadEmailSender.sendLeadUpdateEmail();
                    LOG.info("END : Lead update email notification for assigned user: {} ", emailRQ.getUserName());
                }

            } else {
//                LOG.info("ERROR : Email not found for assigned user: {} ", emailRQ.getUserName());
            }
        } else {
            if (emailUnSubscribeDTO != null) {
                if (emailUnSubscribeDTO.getStatus() == AppsConstants.Status.ACT) {
                    LOG.info("END : Lead update email notification unsubscribe by assigned user: {} ", emailRQ.getUserName());
                } else {

                    leadEmailSender.setCasEmailService(casEmailService);
                    leadEmailSender.setEnvironment(environment);
                    leadEmailSender.sendLeadUpdateEmail();
                    LOG.info("END : Lead update email notification for assigned user: {} ", emailRQ.getUserName());
                }
            } else {
                leadEmailSender.setCasEmailService(casEmailService);
                leadEmailSender.setEnvironment(environment);
                leadEmailSender.sendLeadUpdateEmail();
                LOG.info("END : Lead update email notification for assigned user: {} ", emailRQ.getUserName());
            }
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public EmailUnSubscribeDTO updateEmailNotificationGetStatus(EmailUnSubscribeRQ emailUnSubscribeRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("Start : Lead update email notification status change by user: {} ", emailUnSubscribeRQ.getUserName());
        EmailUnSubscribe emailUnSubscribe = null;
        EmailUnSubscribeDTO emailUnSubscribeDTO = null;
        Date date = new Date();

        emailUnSubscribeDTO = emailUnSubscribeJdbcDao.getEmailUnSubscribeDTOByUser(emailUnSubscribeRQ.getUserName(), emailUnSubscribeRQ.getUserEmail());

        if (emailUnSubscribeDTO != null) {
            emailUnSubscribe = emailUnSubscribeDao.getOne(emailUnSubscribeDTO.getEmailUnSubscribeID());
            if (emailUnSubscribeRQ.isEmailNotificationDisable()) {
                emailUnSubscribe.setStatus(AppsConstants.Status.ACT);
            } else {
                emailUnSubscribe.setStatus(AppsConstants.Status.INA);
            }
            emailUnSubscribe.setModifiedDate(date);
            emailUnSubscribe.setModifiedBy(credentialsDTO.getUserName());
        } else {
            emailUnSubscribe = new EmailUnSubscribe();
            emailUnSubscribe.setUserName(emailUnSubscribeRQ.getUserName());
            emailUnSubscribe.setUserEmail(emailUnSubscribeRQ.getUserEmail());
            emailUnSubscribe.setCreatedBy(credentialsDTO.getUserName());
            emailUnSubscribe.setCreatedDate(date);
            if (emailUnSubscribeRQ.isEmailNotificationDisable()) {
                emailUnSubscribe.setStatus(AppsConstants.Status.ACT);
            } else {
                emailUnSubscribe.setStatus(AppsConstants.Status.INA);
            }
        }

        emailUnSubscribe = emailUnSubscribeDao.saveAndFlush(emailUnSubscribe);
        emailUnSubscribeDTO = new EmailUnSubscribeDTO(emailUnSubscribe);

        LOG.info("END : Lead update email notification status change by user: {} ", emailUnSubscribeRQ.getUserName());
        return emailUnSubscribeDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void notifyPasswordChange(UserDTO userDTO, String password, boolean isPasswordReset) {
        LOG.info("START : Password change notification for user : {} ", userDTO);
        PasswordSender passwordSender = new PasswordSender(userDTO, password, isPasswordReset);
        passwordSender.setCasEmailService(this.casEmailService);
        passwordSender.sendPassword();
        LOG.info("END : Password change notification for user : {} ", userDTO);
    }
}

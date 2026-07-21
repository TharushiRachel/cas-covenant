package com.itechro.cas.service.notification;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.notification.EmailUnSubscribeDao;
import com.itechro.cas.dao.notification.jdbc.EmailUnSubscribeJdbcDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.coveringApproval.CoveringApprovalStatusTransitionRQ;
import com.itechro.cas.model.dto.integration.request.UmpDetailLoadByAdIdRQ;
import com.itechro.cas.model.dto.integration.response.UpmDetailResponse;
import com.itechro.cas.model.dto.notification.EmailUnSubscribeDTO;
import com.itechro.cas.service.email.CASEmailService;
import com.itechro.cas.service.integration.IntegrationService;
import com.itechro.cas.service.notification.support.CoveringApprovalEmailSender;
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
import java.util.List;

/**
 *
 *
 * @author tharushi
 */
@Service
public class CoveringApprovalNotificationService implements EnvironmentAware {

    private static final Logger LOG = LoggerFactory.getLogger(CoveringApprovalNotificationService.class);

    private Environment environment;

    @Autowired
    private CASEmailService casEmailService;

    @Autowired
    private EmailUnSubscribeDao emailUnSubscribeDao;

    @Autowired
    private EmailUnSubscribeJdbcDao emailUnSubscribeJdbcDao;

    @Autowired
    private IntegrationService integrationService;
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void notifyCoveringApprovalUpdate(CoveringApprovalStatusTransitionRQ emailRQ) throws AppsException {

        EmailUnSubscribeDTO emailUnSubscribeDTO = null;
        CoveringApprovalEmailSender coveringApprovalEmailSender = new CoveringApprovalEmailSender(emailRQ);
        emailUnSubscribeDTO = emailUnSubscribeJdbcDao.getEmailUnSubscribeDTOByUser(emailRQ.getUserName(),null);

        if(emailRQ.getToAddresses() == null || emailRQ.getToAddresses().isEmpty()){
            UmpDetailLoadByAdIdRQ rq = new UmpDetailLoadByAdIdRQ();
            rq.setAdUserID(emailRQ.getUserName());
            UpmDetailResponse response = integrationService.getUpmDetailsByAdUserIdAndAppCode(rq);

            if(response.getEmail() != null){
                List<String> emailAddressFromUpm = new ArrayList<>();
                emailAddressFromUpm.add(response.getEmail());
                emailRQ.setToAddresses(emailAddressFromUpm);

                if(emailUnSubscribeDTO != null){

                    if (emailUnSubscribeDTO.getStatus() == AppsConstants.Status.ACT) {
                        LOG.info("END : Covering Approval update email notification unsubscribe by assigned user: {} ", emailRQ.getUserName());
                    } else {
                        coveringApprovalEmailSender.setCasEmailService(casEmailService);
                        coveringApprovalEmailSender.setEnvironment(environment);
                        coveringApprovalEmailSender.sendCoveringApprovalUpdateEmail();
                        LOG.info("END : CoveringApprovalEmailSender update email notification for assigned user: {} ", emailRQ.getUserName());
                    }
                } else {
                    coveringApprovalEmailSender.setCasEmailService(casEmailService);
                    coveringApprovalEmailSender.setEnvironment(environment);
                    coveringApprovalEmailSender.sendCoveringApprovalUpdateEmail();
                    LOG.info("END : Covering Approval update email notification for assigned user: {} ", emailRQ.getUserName());
                }
            } else {
                LOG.info("ERROR : Email not found for assigned user: {} ", emailRQ.getUserName());
            }
        } else {
            if(emailUnSubscribeDTO != null) {
                if(emailUnSubscribeDTO.getStatus() == AppsConstants.Status.ACT){
                    LOG.info("END : Application Form update email notification unsubscribe by assigned user: {} ", emailRQ.getUserName());
                } else {
                    coveringApprovalEmailSender.setCasEmailService(casEmailService);
                    coveringApprovalEmailSender.setEnvironment(environment);
                    coveringApprovalEmailSender.sendCoveringApprovalUpdateEmail();
                    LOG.info("END : Covering Approval update email notification for assigned user: {} ", emailRQ.getUserName());
                }
            } else {
                coveringApprovalEmailSender.setCasEmailService(casEmailService);
                coveringApprovalEmailSender.setEnvironment(environment);
                coveringApprovalEmailSender.sendCoveringApprovalUpdateEmail();
                LOG.info("END : Covering Approval update email notification for assigned user: {} ", emailRQ.getUserName());
            }
        }
    }
}



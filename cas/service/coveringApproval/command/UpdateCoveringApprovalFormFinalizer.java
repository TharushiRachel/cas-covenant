package com.itechro.cas.service.coveringApproval.command;

import com.itechro.cas.commons.command.CommandExecutor;
import com.itechro.cas.config.CasProperties;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.coveringApproval.CoveringApprovalStatusTransitionRQ;
import com.itechro.cas.service.applicationform.command.UpdateApplicationFormFinalizer;
import com.itechro.cas.service.notification.CoveringApprovalNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author tharushi
 */

@Component
public class UpdateCoveringApprovalFormFinalizer extends CommandExecutor<CoveringApprovalModificationContext> {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateCoveringApprovalFormFinalizer.class);

    @Autowired
    private CoveringApprovalNotificationService notificationService;

    @Autowired
    private CasProperties casProperties;

    @Override
    public CoveringApprovalModificationContext execute(CoveringApprovalModificationContext context) throws AppsException {
        if(context.getCoveringApprovalStatusTransitionRQ() != null && !context.getCoveringApprovalStatusTransitionRQ().isEmpty()) {
            for(CoveringApprovalStatusTransitionRQ emailRQ : context.getCoveringApprovalStatusTransitionRQ()) {
                if(casProperties.isAllowSendingEmail()){
                    LOG.info("Sending email notification: {}", emailRQ);
                    notificationService.notifyCoveringApprovalUpdate(emailRQ);
                } else {
                    LOG.warn("Email sending is not allowed by CasProperties.");
                }
            }
            LOG.info("Finished sending all emails for update.");
        } else {
            LOG.warn("No CoveringApprovalStatusTransitionRQ found in context.");
        }
        return context;
    }

}

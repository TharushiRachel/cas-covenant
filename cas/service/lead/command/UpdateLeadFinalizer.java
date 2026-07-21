package com.itechro.cas.service.lead.command;

import com.itechro.cas.commons.command.CommandExecutor;
import com.itechro.cas.config.CasProperties;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.lead.LeadStatusTransitionRQ;
import com.itechro.cas.service.notification.LeadNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateLeadFinalizer extends CommandExecutor<LeadModificationContext> {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateLeadFinalizer.class);

    @Autowired
    private LeadNotificationService notificationService;

    @Autowired
    private CasProperties casProperties;

    @Override
    public LeadModificationContext execute(LeadModificationContext context) throws AppsException {

        if (context.getLeadStatusTransitionRQ() != null) {
            for (LeadStatusTransitionRQ emailRQ : context.getLeadStatusTransitionRQ()) {
                if (casProperties.isAllowSendingEmail()) {
                    notificationService.notifyLeadUpdate(emailRQ);
                }
            }
            LOG.info("SENT Lead Emails for update {} ", context.getLeadStatusTransitionRQ());
        }
        return context;
    }


}

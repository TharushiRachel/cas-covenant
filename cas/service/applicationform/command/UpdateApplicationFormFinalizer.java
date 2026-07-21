package com.itechro.cas.service.applicationform.command;

import com.itechro.cas.commons.command.CommandExecutor;
import com.itechro.cas.config.CasProperties;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.applicationform.ApplicationFormStatusTransitionRQ;
import com.itechro.cas.service.notification.ApplicationFormNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateApplicationFormFinalizer extends CommandExecutor<ApplicationFormModificationContext> {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateApplicationFormFinalizer.class);

    @Autowired
    private ApplicationFormNotificationService notificationService;

    @Autowired
    private CasProperties casProperties;

    @Override
    public ApplicationFormModificationContext execute(ApplicationFormModificationContext context) throws AppsException {
        if (context.getApplicationFormStatusTransitionRQ() != null) {
            for (ApplicationFormStatusTransitionRQ emailRQ : context.getApplicationFormStatusTransitionRQ()) {
                if (casProperties.isAllowSendingEmail()) {
                    notificationService.notifyApplicationFormUpdate(emailRQ);
                }
            }
            LOG.info("SENT Emails for update {} ", context.getApplicationFormStatusTransitionRQ());
        }
        return context;
    }

}

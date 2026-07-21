package com.itechro.cas.service.faclititypaper.command;

import com.itechro.cas.commons.command.CommandExecutor;
import com.itechro.cas.config.CasProperties;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperStatusTransitionRQ;
import com.itechro.cas.service.notification.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateFacilityPaperFinalizer extends CommandExecutor<FacilityPaperModificationContext> {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateFacilityPaperFinalizer.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private CasProperties casProperties;

    @Override
    public FacilityPaperModificationContext execute(FacilityPaperModificationContext context) throws AppsException {

        LOG.info("Facility paper updated, {}", context.getFacilityPaperUpdateDto());
        if (context.getFacilityPaperStatusTransitionRQ() != null) {
            for (FacilityPaperStatusTransitionRQ emailRQ : context.getFacilityPaperStatusTransitionRQ()) {
                if (casProperties.isAllowSendingEmail()) {
                    notificationService.notifyFacilityPaperUpdate(emailRQ);
                }
            }
            LOG.info("SENT Emails for update {} ", context.getFacilityPaperStatusTransitionRQ());
        }
        return context;
    }

}

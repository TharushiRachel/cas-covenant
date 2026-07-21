package com.itechro.cas.service.faclititypaper.command;

import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperStatusTransitionRQ;

public interface EmailNotificationSendable {

    void sendEmailNotification(FacilityPaperModificationContext context) throws AppsException;

}

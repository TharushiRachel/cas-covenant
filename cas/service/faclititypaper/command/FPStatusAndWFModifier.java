package com.itechro.cas.service.faclititypaper.command;

import com.itechro.cas.commons.command.CommandExecutor;
import com.itechro.cas.exception.impl.AppsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class FPStatusAndWFModifier extends CommandExecutor<FacilityPaperModificationContext> {

    @Autowired
    private UpdateFacilityPaperInitializer updateFacilityPaperInitializer;

    @Autowired
    private UpdateFacilityPaperStatus updateFacilityPaperStatus;

    @Autowired
    private UpdateFacilityPaperWorkflowRouting updateFacilityPaperWorkflowRouting;

    @Autowired
    private UpdateFacilityPaperFinalizer updateFacilityPaperFinalizer;

    @Override
    public FacilityPaperModificationContext execute(FacilityPaperModificationContext context) throws AppsException {
        return super.execute(context);
    }

    @PostConstruct
    public void initialize() {
        addCommand(updateFacilityPaperInitializer);
        addCommand(updateFacilityPaperStatus);
        addCommand(updateFacilityPaperWorkflowRouting);
        addCommand(updateFacilityPaperFinalizer);
    }

}

package com.itechro.cas.service.applicationform.command.replicate;

import com.itechro.cas.commons.command.CommandExecutor;
import com.itechro.cas.exception.impl.AppsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ApplicationFormReplicateExecutor extends CommandExecutor<ApplicationFormReplicateContext> {

    @Autowired
    NewApplicationFormInitializer newApplicationFormInitializer;

    @Autowired
    BasicInformationReplicator basicInformationReplicator;

    @Autowired
    FacilitySecuritiesReplicator facilitySecuritiesReplicator;

    @Autowired
    SupportingDocumentReplicator supportingDocumentReplicator;

    @Autowired
    TopicsReplicator topicsReplicator;

    @Override
    public ApplicationFormReplicateContext execute(ApplicationFormReplicateContext context) throws AppsException {
        return super.execute(context);
    }

    @PostConstruct
    public void initialize() {
        addCommand(newApplicationFormInitializer);
        addCommand(basicInformationReplicator);
        addCommand(facilitySecuritiesReplicator);
        addCommand(supportingDocumentReplicator);
        addCommand(topicsReplicator);
    }
}

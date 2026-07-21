package com.itechro.cas.service.lead.command;

import com.itechro.cas.commons.command.CommandExecutor;
import com.itechro.cas.exception.impl.AppsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LeadStatusModifier extends CommandExecutor<LeadModificationContext> {

    @Autowired
    private UpdateLeadFinalizer updateLeadFinalizer;

    @Override
    public LeadModificationContext execute(LeadModificationContext context) throws AppsException {
        return super.execute(context);
    }

    @PostConstruct
    public void initialize() {

        addCommand(updateLeadFinalizer);
    }

}

package com.itechro.cas.service.applicationform.command;

import com.itechro.cas.commons.command.CommandExecutor;
import com.itechro.cas.exception.impl.AppsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
public class AFStatusModifier extends CommandExecutor<ApplicationFormModificationContext> {

    @Autowired
    private UpdateApplicationFormFinalizer updateApplicationFormFinalizer;

    @Override
    public ApplicationFormModificationContext execute(ApplicationFormModificationContext context) throws AppsException {
        return super.execute(context);
    }

    @PostConstruct
    public void initialize() {

        addCommand(updateApplicationFormFinalizer);
    }

}

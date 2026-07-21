package com.itechro.cas.service.coveringApproval.command;

import com.itechro.cas.commons.command.CommandExecutor;
import com.itechro.cas.exception.impl.AppsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 *
 *
 * @author tharushi
 */

@Component
public class CoveringApprovalStatusModifier extends CommandExecutor<CoveringApprovalModificationContext> {

    @Autowired
    private UpdateCoveringApprovalFormFinalizer updateCoveringApprovalFormFinalizer;

    @Override
    public CoveringApprovalModificationContext execute(CoveringApprovalModificationContext context) throws AppsException {
        return super.execute(context);
    }

    @PostConstruct
    public void initialize(){
        addCommand(updateCoveringApprovalFormFinalizer);
    }
}

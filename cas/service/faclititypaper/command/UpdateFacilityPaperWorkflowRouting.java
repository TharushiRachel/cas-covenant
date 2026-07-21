package com.itechro.cas.service.faclititypaper.command;

import com.itechro.cas.commons.command.CommandExecutor;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.facilitypaper.FPWorkflowRouting;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperStatusTransitionRQ;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperUpdateDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UpdateFacilityPaperWorkflowRouting extends CommandExecutor<FacilityPaperModificationContext> {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateFacilityPaperWorkflowRouting.class);

    @Override
    public FacilityPaperModificationContext execute(FacilityPaperModificationContext context) throws AppsException {
        FacilityPaper facilityPaper = context.getFacilityPaper();
        CredentialsDTO credentialsDTO = new CredentialsDTO();
        FacilityPaperUpdateDTO updateDto = context.getFacilityPaperUpdateDto();
        if (updateDto.getRoutingStatus() != null) {
            FPWorkflowRouting fpWorkflowRouting = new FPWorkflowRouting();
            fpWorkflowRouting.setAssignUserID(updateDto.getAssignUserID());
            fpWorkflowRouting.setAssignUser(updateDto.getAssignUser());
            fpWorkflowRouting.setAssignUserDisplayName(updateDto.getAssignUserDisplayName());
            fpWorkflowRouting.setAssignUserUpmID(updateDto.getAssignUserUpmID());
            fpWorkflowRouting.setAssignUserUpmGroupCode(updateDto.getAssignUserUpmGroupCode());
            fpWorkflowRouting.setAssignDepartmentCode(updateDto.getAssignDepartmentCode());
            fpWorkflowRouting.setStatus(AppsConstants.Status.ACT);
            fpWorkflowRouting.setRoutingStatus(updateDto.getRoutingStatus());
            if (updateDto.getActionMessage() != null) {
                fpWorkflowRouting.setRoutingRemarks(updateDto.getActionMessage());
            }
            fpWorkflowRouting.setAssignDate(context.getDate());
            fpWorkflowRouting.setAuthorityLevel(updateDto.getAuthorityLevel());
            fpWorkflowRouting.setCreatedBy(credentialsDTO.getUserName());
            fpWorkflowRouting.setCreatedDate(new Date());
            int cycleCount = 1;
            if (facilityPaper.getCurrentCycle() != null) {
                cycleCount = facilityPaper.getCurrentCycle() + 1;
            }
            fpWorkflowRouting.setCycle(cycleCount);

            facilityPaper.addFpWorkflowRouting(fpWorkflowRouting);
            facilityPaper.setCurrentCycle(cycleCount);
            facilityPaper.setCurrentAuthorityLevel(updateDto.getAuthorityLevel());

        } else {
            LOG.info("NO routing changes {}", updateDto);
        }

        return context;
    }


    public void sendEmailNotification(FacilityPaperModificationContext context) throws AppsException {
        FacilityPaperUpdateDTO updateDto = context.getFacilityPaperUpdateDto();
        FacilityPaperStatusTransitionRQ emailRQ = new FacilityPaperStatusTransitionRQ();
        emailRQ.setCredentialsDTO(context.getCredentialsDto());
        if (updateDto.getAssignUserDisplayName() != null) {
            emailRQ.setUserName(updateDto.getAssignUserDisplayName());
        } else {
            emailRQ.setUserName(updateDto.getAssignUser());
        }
        emailRQ.setUserName(updateDto.getAssignUser());
        emailRQ.setNewStatus(context.getFacilityPaperUpdateDto().getFacilityPaperStatus());
        emailRQ.setFacilityPaperID(context.getFacilityPaper().getFacilityPaperID());
        emailRQ.setFacilityPaperRefNumber(updateDto.getFpRefNumber());
        emailRQ.getToAddresses().add(updateDto.getAssignUserEmailAddress());
        emailRQ.setRoutingStatus(updateDto.getRoutingStatus());
        context.addFacilityPaperStatusTransitionRQ(emailRQ);
    }
}

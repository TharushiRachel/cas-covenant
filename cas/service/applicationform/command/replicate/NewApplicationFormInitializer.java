package com.itechro.cas.service.applicationform.command.replicate;

import com.itechro.cas.commons.command.CommandExecutor;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.applicationform.ApplicationFormDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.applicationform.AFStatusHistory;
import com.itechro.cas.model.domain.applicationform.ApplicationForm;
import com.itechro.cas.model.domain.esg.EnvironmentalRiskData;
import com.itechro.cas.model.dto.applicationform.ReplicateApplicationFormRQ;
import com.itechro.cas.model.dto.esg.EnvironmentalRiskDataDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NewApplicationFormInitializer extends CommandExecutor<ApplicationFormReplicateContext> {

    private static final Logger LOG = LoggerFactory.getLogger(NewApplicationFormInitializer.class);

    @Autowired
    private ApplicationFormDao applicationFormDao;

    @Override
    public ApplicationFormReplicateContext execute(ApplicationFormReplicateContext context) throws AppsException {
        ReplicateApplicationFormRQ replicateApplicationFromFormRQ = context.getReplicateApplicationFromFormRQ();

        ApplicationForm currentApplicationForm = applicationFormDao.getOne(replicateApplicationFromFormRQ.getOriginalApplicationFormID());
        context.setCurrentApplicationForm(currentApplicationForm);

        CredentialsDTO credentialsDTO = context.getCredentialsDto();
        ApplicationForm newApplicationForm = new ApplicationForm();

        newApplicationForm.setBranchCode(context.getReplicateApplicationFromFormRQ().getBranchCode());
        newApplicationForm.setAfRefNumber(context.getNewApplicationFormRefNumber());
        newApplicationForm.setCurrentApplicationFormStatus(DomainConstants.ApplicationFormStatus.DRAFT);
        newApplicationForm.setFormType(context.getReplicateApplicationFromFormRQ().getFormType());
        newApplicationForm.setWorkFlowTemplate(context.getWorkFlowTemplate());

        newApplicationForm.setAssignUser(context.getReplicateApplicationFromFormRQ().getAssignUser());
        newApplicationForm.setAssignUserID(context.getReplicateApplicationFromFormRQ().getAssignUserID());
        newApplicationForm.setCreatedUserID(context.getReplicateApplicationFromFormRQ().getCreatedUserID());
        newApplicationForm.setAssignUserDivCode(context.getReplicateApplicationFromFormRQ().getAssignUserDivCode());
        newApplicationForm.setAssignUserUpmID(context.getReplicateApplicationFromFormRQ().getAssignUserUpmID());
        newApplicationForm.setAssignUserDisplayName(context.getReplicateApplicationFromFormRQ().getAssignUserDisplayName());
        newApplicationForm.setAssignUserUpmGroupCode(context.getReplicateApplicationFromFormRQ().getAssignUserUpmGroupCode());

        newApplicationForm.setCreatedBy(credentialsDTO.getUserName());
        newApplicationForm.setCreatedUserDisplayName(context.getReplicateApplicationFromFormRQ().getCreatedUserDisplayName());
        newApplicationForm.setCreatedDate(context.getDate());
        newApplicationForm.setIsESGPaper(AppsConstants.YesNo.N);
        newApplicationForm.setIsESGApproved(AppsConstants.YesNo.N);

//        if (context.getCurrentApplicationForm().getApprovedESGScore() != null) {
//            newApplicationForm.setApprovedESGScore(context.getCurrentApplicationForm().getApprovedESGScore());
//        }
        AFStatusHistory afStatusHistory = new AFStatusHistory();
        afStatusHistory.setActionMessage("Draft Application From by " + replicateApplicationFromFormRQ.getAssignUserDisplayName());
        afStatusHistory.setApplicationFormStatus(replicateApplicationFromFormRQ.getCurrentApplicationFormStatus());
        afStatusHistory.setAssignUserID(replicateApplicationFromFormRQ.getAssignUserID());
        afStatusHistory.setAssignUser(replicateApplicationFromFormRQ.getAssignUser());
        afStatusHistory.setAssignUserDisplayName(replicateApplicationFromFormRQ.getAssignUserDisplayName());
        afStatusHistory.setAssignUserUpmID(replicateApplicationFromFormRQ.getAssignUserUpmID());
        afStatusHistory.setAssignUserUpmGroupCode(replicateApplicationFromFormRQ.getAssignUserUpmGroupCode());
        afStatusHistory.setAssignUserDivCode(replicateApplicationFromFormRQ.getAssignUserDivCode());
        afStatusHistory.setUpdatedUserDisplayName(replicateApplicationFromFormRQ.getAssignUserDisplayName());
        afStatusHistory.setUpdateBy(credentialsDTO.getUserName());
        afStatusHistory.setUpdateDate(context.getDate());
        newApplicationForm.addAFStatusHistory(afStatusHistory);

//        if (!context.getCurrentApplicationForm().getEnvironmentalRisks().isEmpty()) {
//            Date date = new Date();
//
//            List<EnvironmentalRiskDataDTO> prevEnvRiskCategories = context.getCurrentApplicationForm().getEnvironmentalRisks().stream()
//                    .sorted(Comparator.comparingInt(EnvironmentalRiskData::getRiskDataId))
//                    .map(EnvironmentalRiskDataDTO::new).collect(Collectors.toList());
//
//            for (EnvironmentalRiskDataDTO envRisk : prevEnvRiskCategories) {
//                EnvironmentalRiskData replicateEnvRisk = new EnvironmentalRiskData();
//                replicateEnvRisk.setRiskCategoryId(envRisk.getRiskCategoryId());
//                replicateEnvRisk.setCategoryParentId(envRisk.getCategoryParentId());
//                replicateEnvRisk.setDescription(envRisk.getDescription());
//                replicateEnvRisk.setScore(envRisk.getScore());
//                replicateEnvRisk.setType(envRisk.getType());
//                replicateEnvRisk.setCreatedBy(credentialsDTO.getUserName());
//                replicateEnvRisk.setCreatedDate(date);
//                newApplicationForm.addEnvironmentalRisk(replicateEnvRisk);
//            }
//        }

        context.setNewApplicationForm(newApplicationForm);
        LOG.info("New Application Form Initialized {} ", replicateApplicationFromFormRQ);
        return context;
    }

}

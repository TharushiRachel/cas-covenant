package com.itechro.cas.service.casmaster.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.casmaster.UpmGroupDao;
import com.itechro.cas.dao.casmaster.WorkFlowTemplateDao;
import com.itechro.cas.dao.casmaster.WorkFlowTemplateDataDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.casmaster.UpmGroup;
import com.itechro.cas.model.domain.casmaster.WorkFlowTemplate;
import com.itechro.cas.model.domain.casmaster.WorkFlowTemplateData;
import com.itechro.cas.model.dto.casmaster.UpdateLowerLevelRQ;
import com.itechro.cas.model.dto.casmaster.WorkFlowTemplateDTO;
import com.itechro.cas.model.dto.casmaster.WorkFlowTemplateDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class UPMGroupApproveHandler extends MasterDataApprovalHandler {

    private static final Logger LOG = LoggerFactory.getLogger(UPMGroupApproveHandler.class);

    private UpmGroupDao upmGroupDao;

    private WorkFlowTemplateDataDao workFlowTemplateDataDao;

    private WorkFlowTemplateDao workFlowTemplateDao;

    @Override
    public void transitStatus() throws AppsException {
        this.init();
        if (this.isValidApproval()) {
            this.updateApproveStatus();
            this.updateLowerLevelData();
            LOG.info("UPM Group ApproveStatus change : {} : {}", approveRejectRQ, credentialsDTO);
        } else {
            LOG.error("ERROR : UPM Group approve status change detect : Invalid status transition : {} : {}",
                    approveRejectRQ, credentialsDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_INVALID_APPROVE_STATUS_CHANGE);
        }
    }

    @Override
    protected void updateApproveStatus() throws AppsException {
        UpmGroup upmGroup = null;
        Date date = new Date();

        upmGroup = upmGroupDao.getOne(approveRejectRQ.getApproveRejectDataID());
        hasUserAuthorizedForApproval(upmGroup);
        upmGroup.setModifiedBy(credentialsDTO.getUserName());
        upmGroup.setModifiedDate(date);
        upmGroup.setApproveStatus(approveRejectRQ.getApproveStatus());
        upmGroup.setApprovedBy(credentialsDTO.getUserName());
        upmGroup.setApprovedDate(date);
        if (approveRejectRQ.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED) {
            if (upmGroup.getParentRecordID() != null) {
                UpmGroup parentUpmGroup = upmGroupDao.getOne(upmGroup.getParentRecordID());
                parentUpmGroup.setStatus(AppsConstants.Status.INA);
                parentUpmGroup.setModifiedDate(date);
                parentUpmGroup.setModifiedBy(credentialsDTO.getUserName());
                this.upmGroupDao.saveAndFlush(parentUpmGroup);

                updateLowerLevelRQ = new UpdateLowerLevelRQ(approveRejectRQ.getApproveRejectDataID(), upmGroup.getParentRecordID(),
                        credentialsDTO, date);
                this.needToUpdateLowerLevel = true;
            }
        } else {
            upmGroup.setStatus(AppsConstants.Status.INA);
        }
        response = upmGroupDao.saveAndFlush(upmGroup);
    }

    @Override
    protected boolean isValidApproval() {
        return true;
    }

    @Override
    protected void updateLowerLevelData() {
        if (needToUpdateLowerLevel) {

            LOG.info("START: Update Workflow template Data based on changes {} : {}", approveRejectRQ, updateLowerLevelRQ);
            List<Integer> workflowIDs = new ArrayList<>();
            List<WorkFlowTemplateData> workFlowTemplateDataList = workFlowTemplateDataDao.findAllByPreviousUPMGroup_UpmGroupID(updateLowerLevelRQ.getPreviousEntityID());
            for (WorkFlowTemplateData templateData : workFlowTemplateDataList) {
                if (!workflowIDs.contains(templateData.getWorkFlowTemplate().getWorkFlowTemplateID())) {
                    workflowIDs.add(templateData.getWorkFlowTemplate().getWorkFlowTemplateID());
                }
            }

            workFlowTemplateDataList = workFlowTemplateDataDao.findAllByPreviousUPMGroup_UpmGroupID(updateLowerLevelRQ.getPreviousEntityID());
            for (WorkFlowTemplateData templateData : workFlowTemplateDataList) {
                if (!workflowIDs.contains(templateData.getWorkFlowTemplate().getWorkFlowTemplateID())) {
                    workflowIDs.add(templateData.getWorkFlowTemplate().getWorkFlowTemplateID());
                }
            }

            workFlowTemplateDataList = workFlowTemplateDataDao.findAllByUpmGroup_UpmGroupID(updateLowerLevelRQ.getPreviousEntityID());
            for (WorkFlowTemplateData templateData : workFlowTemplateDataList) {
                if (!workflowIDs.contains(templateData.getWorkFlowTemplate().getWorkFlowTemplateID())) {
                    workflowIDs.add(templateData.getWorkFlowTemplate().getWorkFlowTemplateID());
                }
            }

            List<WorkFlowTemplate> workFlowTemplateList = this.workFlowTemplateDao.findAllById(workflowIDs);

            for (WorkFlowTemplate workFlowTemplate : workFlowTemplateList) {
                if (workFlowTemplate.getStatus() == AppsConstants.Status.ACT) {
                    if (workFlowTemplate.getApproveStatus() == DomainConstants.MasterDataApproveStatus.PENDING) {
                        for (WorkFlowTemplateData templateData : workFlowTemplate.getWorkFlowTemplateDataSet()) {
                            if (Objects.equals(templateData.getUpmGroup().getUpmGroupID(), updateLowerLevelRQ.getPreviousEntityID())) {
                                templateData.setUpmGroup(this.upmGroupDao.getOne(updateLowerLevelRQ.getNewEntityID()));
                            }
                            if (templateData.getPreviousUPMGroup() != null && Objects.equals(templateData.getPreviousUPMGroup().getUpmGroupID(), updateLowerLevelRQ.getPreviousEntityID())) {
                                templateData.setPreviousUPMGroup(this.upmGroupDao.getOne(updateLowerLevelRQ.getNewEntityID()));
                            }
                            if (templateData.getNextUPMGroup() != null && Objects.equals(templateData.getNextUPMGroup().getUpmGroupID(), updateLowerLevelRQ.getPreviousEntityID())) {
                                templateData.setNextUPMGroup(this.upmGroupDao.getOne(updateLowerLevelRQ.getNewEntityID()));
                            }
                        }
                    }
                    if (workFlowTemplate.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED) {
                        WorkFlowTemplateDTO templateDTO = new WorkFlowTemplateDTO(workFlowTemplate);
                        templateDTO.setWorkFlowTemplateID(null);
                        for (WorkFlowTemplateDataDTO templateDataDto : templateDTO.getWorkFlowTemplateDataDTOList()) {
                            if (Objects.equals(templateDataDto.getUpmGroupID(), updateLowerLevelRQ.getPreviousEntityID())) {
                                templateDataDto.setUpmGroupID(updateLowerLevelRQ.getNewEntityID());
                            }
                            if (Objects.equals(templateDataDto.getPreviousUPMGroupID(), updateLowerLevelRQ.getPreviousEntityID())) {
                                templateDataDto.setPreviousUPMGroupID(updateLowerLevelRQ.getNewEntityID());
                            }
                            if (Objects.equals(templateDataDto.getNextUPMGroupID(), updateLowerLevelRQ.getPreviousEntityID())) {
                                templateDataDto.setNextUPMGroupID(updateLowerLevelRQ.getNewEntityID());
                            }
                        }

                        workFlowTemplate = new WorkFlowTemplate();
                        workFlowTemplate.setCreatedBy(credentialsDTO.getUserName());
                        workFlowTemplate.setCreatedDate(date);

                        workFlowTemplate.setApproveStatus(approveRejectRQ.getApproveStatus());
                        workFlowTemplate.setApprovedBy(credentialsDTO.getUserName());
                        workFlowTemplate.setApprovedDate(date);

                        for (WorkFlowTemplateDataDTO dataDTO : templateDTO.getWorkFlowTemplateDataDTOList()) {
                            WorkFlowTemplateData templateData = null;
                            templateData = new WorkFlowTemplateData();
                            workFlowTemplate.addWorkFlowTemplateData(templateData);
                            templateData.setDisplayOrder(dataDTO.getDisplayOrder());
                            templateData.setUpmGroup(this.upmGroupDao.getOne(dataDTO.getUpmGroupID()));
                            if (dataDTO.getNextUPMGroupID() != null) {
                                templateData.setNextUPMGroup(this.upmGroupDao.getOne(dataDTO.getNextUPMGroupID()));
                            }
                            if (dataDTO.getPreviousUPMGroupID() != null) {
                                templateData.setPreviousUPMGroup(this.upmGroupDao.getOne(dataDTO.getPreviousUPMGroupID()));
                            }
                        }

                        workFlowTemplate.setCode(templateDTO.getCode());
                        workFlowTemplate.setDescription(templateDTO.getDescription());
                        workFlowTemplate.setWorkFlowTemplateName(templateDTO.getWorkFlowTemplateName());
                        workFlowTemplate.setStatus(templateDTO.getStatus());
                        workFlowTemplateDao.saveAndFlush(workFlowTemplate);
                    }
                }
            }

            LOG.info("END: Update Workflow template Data based on changes {} : {}", approveRejectRQ, updateLowerLevelRQ);
        }
    }

    public void setUpmGroupDao(UpmGroupDao upmGroupDao) {
        this.upmGroupDao = upmGroupDao;
    }

    public void setWorkFlowTemplateDataDao(WorkFlowTemplateDataDao workFlowTemplateDataDao) {
        this.workFlowTemplateDataDao = workFlowTemplateDataDao;
    }

    public void setWorkFlowTemplateDao(WorkFlowTemplateDao workFlowTemplateDao) {
        this.workFlowTemplateDao = workFlowTemplateDao;
    }
}

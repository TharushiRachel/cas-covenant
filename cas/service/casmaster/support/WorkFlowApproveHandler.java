package com.itechro.cas.service.casmaster.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.casmaster.WorkFlowTemplateDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.casmaster.WorkFlowTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class WorkFlowApproveHandler extends MasterDataApprovalHandler {

    private static final Logger LOG = LoggerFactory.getLogger(WorkFlowApproveHandler.class);

    private WorkFlowTemplateDao workFlowTemplateDao;

    @Override
    public void transitStatus() throws AppsException {
        this.init();
        if (this.isValidApproval()) {
            this.updateApproveStatus();
            this.updateLowerLevelData();
            LOG.info("Work Flow ApproveStatus change : {} : {}", approveRejectRQ, credentialsDTO);
        } else {
            LOG.error("ERROR : Work Flow ApproveStatus change detect : Invalid status transition : {} : {}",
                    approveRejectRQ, credentialsDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_INVALID_APPROVE_STATUS_CHANGE);
        }
    }

    @Override
    protected void updateApproveStatus() throws AppsException {
        WorkFlowTemplate workFlowTemplate = null;
        Date date = new Date();

        workFlowTemplate = workFlowTemplateDao.getOne(approveRejectRQ.getApproveRejectDataID());
        hasUserAuthorizedForApproval(workFlowTemplate);
        workFlowTemplate.setModifiedBy(credentialsDTO.getUserName());
        workFlowTemplate.setModifiedDate(date);
        workFlowTemplate.setApproveStatus(approveRejectRQ.getApproveStatus());
        workFlowTemplate.setApprovedBy(credentialsDTO.getUserName());
        workFlowTemplate.setApprovedDate(date);
        if (approveRejectRQ.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED) {
            if (workFlowTemplate.getParentRecordID() != null) {
                WorkFlowTemplate parentWorkFlowTemplate = workFlowTemplateDao.getOne(workFlowTemplate.getParentRecordID());
                parentWorkFlowTemplate.setStatus(AppsConstants.Status.INA);
                parentWorkFlowTemplate.setModifiedDate(date);
                parentWorkFlowTemplate.setModifiedBy(credentialsDTO.getUserName());
                this.workFlowTemplateDao.saveAndFlush(parentWorkFlowTemplate);
            }
        } else {
            workFlowTemplate.setStatus(AppsConstants.Status.INA);
        }
        response = workFlowTemplateDao.saveAndFlush(workFlowTemplate);
    }

    @Override
    protected boolean isValidApproval() {
        return true;
    }

    @Override
    protected void updateLowerLevelData() {

    }

    public void setWorkFlowTemplateDao(WorkFlowTemplateDao workFlowTemplateDao) {
        this.workFlowTemplateDao = workFlowTemplateDao;
    }
}

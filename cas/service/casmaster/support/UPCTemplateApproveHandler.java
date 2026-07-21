package com.itechro.cas.service.casmaster.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.casmaster.UpcTemplateDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.casmaster.UpcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class UPCTemplateApproveHandler extends MasterDataApprovalHandler {

    private static final Logger LOG = LoggerFactory.getLogger(UPCTemplateApproveHandler.class);

    private UpcTemplateDao upcTemplateDao;

    @Override
    public void transitStatus() throws AppsException {
        this.init();

        if (this.isValidApproval()) {
            this.updateApproveStatus();
            this.updateLowerLevelData();
            LOG.info("UPC template ApproveStatus change : {} : {}", approveRejectRQ, credentialsDTO);
        } else {
            LOG.error("ERROR : UPC template approve status change detect : Invalid status transition : {} : {}",
                    approveRejectRQ, credentialsDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_INVALID_APPROVE_STATUS_CHANGE);
        }
    }

    @Override
    protected void updateApproveStatus() throws AppsException {
        UpcTemplate upcTemplate = null;
        Date date = new Date();

        upcTemplate = upcTemplateDao.getOne(this.approveRejectRQ.getApproveRejectDataID());
        hasUserAuthorizedForApproval(upcTemplate);
        upcTemplate.setModifiedBy(credentialsDTO.getUserName());
        upcTemplate.setModifiedDate(date);
        upcTemplate.setApproveStatus(this.approveRejectRQ.getApproveStatus());
        upcTemplate.setApprovedBy(credentialsDTO.getUserName());
        upcTemplate.setApprovedDate(date);

        if (this.approveRejectRQ.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED) {
            if (upcTemplate.getParentRecordID() != null) {
                UpcTemplate parentUpcTemplate = upcTemplateDao.getOne(upcTemplate.getParentRecordID());
                parentUpcTemplate.setStatus(AppsConstants.Status.INA);
                parentUpcTemplate.setModifiedDate(date);
                parentUpcTemplate.setModifiedBy(credentialsDTO.getUserName());
                this.upcTemplateDao.saveAndFlush(parentUpcTemplate);
            }
        } else {
            upcTemplate.setStatus(AppsConstants.Status.INA);
        }
        response = upcTemplateDao.saveAndFlush(upcTemplate);
    }

    @Override
    protected boolean isValidApproval() {
        return true;
    }

    @Override
    protected void updateLowerLevelData() {

    }

    public void setUpcTemplateDao(UpcTemplateDao upcTemplateDao) {
        this.upcTemplateDao = upcTemplateDao;
    }
}

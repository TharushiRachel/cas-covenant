package com.itechro.cas.service.casmaster.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.casmaster.AFTopicDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.casmaster.AFTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class AFTopicApproveHandler extends MasterDataApprovalHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AFTopicApproveHandler.class);

    private AFTopicDao afTopicDao;

    @Override
    public void transitStatus() throws AppsException {
        this.init();

        if (this.isValidApproval()) {
            this.updateApproveStatus();
            this.updateLowerLevelData();
            LOG.info("Application Form Topic ApproveStatus change : {} : {}", approveRejectRQ, credentialsDTO);
        } else {
            LOG.error("ERROR : Application Form Topic ApproveStatus change detect : Invalid status transition : {} : {}",
                    approveRejectRQ, credentialsDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_INVALID_APPROVE_STATUS_CHANGE);
        }
    }

    @Override
    protected void updateApproveStatus() throws AppsException {
        AFTopic afTopic = null;
        Date date = new Date();

        afTopic = afTopicDao.getOne(approveRejectRQ.getApproveRejectDataID());
        hasUserAuthorizedForApproval(afTopic);
        afTopic.setModifiedBy(credentialsDTO.getUserName());
        afTopic.setModifiedDate(date);
        afTopic.setApproveStatus(approveRejectRQ.getApproveStatus());
        afTopic.setApprovedDate(date);
        afTopic.setApprovedBy(credentialsDTO.getUserName());

        if (approveRejectRQ.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED) {
            if (afTopic.getParentRecordID() != null) {
                AFTopic parentAFTopic = afTopicDao.getOne(afTopic.getParentRecordID());
                parentAFTopic.setStatus(AppsConstants.Status.INA);
                parentAFTopic.setModifiedDate(date);
                parentAFTopic.setModifiedBy(credentialsDTO.getUserName());
                this.afTopicDao.saveAndFlush(parentAFTopic);
            }
        } else {
            afTopic.setStatus(AppsConstants.Status.INA);
        }
        response = afTopicDao.saveAndFlush(afTopic);

    }

    @Override
    protected boolean isValidApproval() {
        return true;
    }

    @Override
    protected void updateLowerLevelData() {

    }

    public void setAfTopicDao(AFTopicDao afTopicDao) {
        this.afTopicDao = afTopicDao;
    }
}

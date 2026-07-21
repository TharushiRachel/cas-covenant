package com.itechro.cas.service.casmaster.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.casmaster.UserPoolDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.casmaster.UserDa;
import com.itechro.cas.model.domain.casmaster.UserPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommitteePoolApproveHandler extends MasterDataApprovalHandler{

    private static final Logger LOG = LoggerFactory.getLogger(UserDaApproveHandler.class);

    private UserPoolDao userPoolDao;

    @Override
    public void transitStatus() throws AppsException {
        this.init();

        if (this.isValidApproval()) {
            this.updateApproveStatus();
            this.updateLowerLevelData();
            LOG.info("User DA ApproveStatus change : {} : {}", approveRejectRQ, credentialsDTO);
        } else {
            LOG.error("ERROR : User DA ApproveStatus change detect : Invalid status transition : {} : {}",
                    approveRejectRQ, credentialsDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_INVALID_APPROVE_STATUS_CHANGE);
        }
    }

    @Override
    protected void updateApproveStatus() throws AppsException {
        UserPool userPool = null;
        userPool = userPoolDao.getOne(approveRejectRQ.getApproveRejectDataID());
        hasUserAuthorizedForApproval(userPool);
        userPool.setModifiedBy(credentialsDTO.getUserName());
        userPool.setModifiedDate(date);
        userPool.setApproveStatus(approveRejectRQ.getApproveStatus());
        userPool.setApprovedBy(credentialsDTO.getUserName());
        userPool.setApprovedDate(date);
        if (approveRejectRQ.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED) {
            if (userPool.getParentRecordID() != null) {
                UserPool parentUserPool = userPoolDao.getOne(userPool.getParentRecordID());
                parentUserPool.setUserStatus(AppsConstants.Status.INA);
                parentUserPool.setModifiedBy(credentialsDTO.getUserName());
                parentUserPool.setModifiedDate(date);

                userPoolDao.save(parentUserPool);
            }
        } else {
            userPool.setUserStatus(AppsConstants.Status.INA);
        }
        response = userPoolDao.save(userPool);
    }

    @Override
    protected boolean isValidApproval() {
        return true;
    }

    @Override
    protected void updateLowerLevelData() {
        if (needToUpdateLowerLevel) {

        }
    }

    public void setUserPoolDao(UserPoolDao userPoolDao) {
        this.userPoolDao = userPoolDao;
    }
}

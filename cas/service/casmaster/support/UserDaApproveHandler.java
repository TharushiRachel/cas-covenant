package com.itechro.cas.service.casmaster.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.casmaster.UserDaDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.casmaster.UserDa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDaApproveHandler extends MasterDataApprovalHandler {

    private static final Logger LOG = LoggerFactory.getLogger(UserDaApproveHandler.class);

    private UserDaDao userDaDao;

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
        UserDa userDa = null;
        userDa = userDaDao.getOne(approveRejectRQ.getApproveRejectDataID());
        hasUserAuthorizedForApproval(userDa);
        userDa.setModifiedBy(credentialsDTO.getUserName());
        userDa.setModifiedDate(date);
        userDa.setApproveStatus(approveRejectRQ.getApproveStatus());
        userDa.setApprovedBy(credentialsDTO.getUserName());
        userDa.setApprovedDate(date);
        if (approveRejectRQ.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED) {
            if (userDa.getParentRecordID() != null) {
                UserDa parentUserDa = userDaDao.getOne(userDa.getParentRecordID());
                parentUserDa.setStatus(AppsConstants.Status.INA);
                parentUserDa.setModifiedBy(credentialsDTO.getUserName());
                parentUserDa.setModifiedDate(date);
                this.userDaDao.saveAndFlush(parentUserDa);
            }
        } else {
            userDa.setStatus(AppsConstants.Status.INA);
        }
        response = userDaDao.saveAndFlush(userDa);
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

    public void setUserDaDao(UserDaDao userDaDao) {
        this.userDaDao = userDaDao;
    }
}

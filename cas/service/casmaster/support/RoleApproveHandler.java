package com.itechro.cas.service.casmaster.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.master.RoleDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.master.Role;
import com.itechro.cas.model.dto.casmaster.UpdateLowerLevelRQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoleApproveHandler extends MasterDataApprovalHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RoleApproveHandler.class);

    private RoleDao roleDao;

    @Override
    public void transitStatus() throws AppsException {
        this.init();
        if (this.isValidApproval()) {
            this.updateApproveStatus();
            this.updateLowerLevelData();
            LOG.info("Role ApproveStatus change : {} : {}", approveRejectRQ, credentialsDTO);
        } else {
            LOG.error("ERROR : Role approve status change detect : Invalid status transition : {} : {}",
                    approveRejectRQ, credentialsDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_INVALID_APPROVE_STATUS_CHANGE);
        }
    }

    @Override
    protected void updateApproveStatus() throws AppsException {
        Role role = null;

        role = roleDao.getOne(approveRejectRQ.getApproveRejectDataID());
        hasUserAuthorizedForApproval(role);
        role.setModifiedBy(credentialsDTO.getUserName());
        role.setModifiedDate(date);
        role.setApproveStatus(approveRejectRQ.getApproveStatus());
        role.setApprovedBy(credentialsDTO.getUserName());
        role.setApprovedDate(date);
        if (approveRejectRQ.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED) {
            if (role.getParentRecordID() != null) {
                Role parentRole = roleDao.getOne(role.getParentRecordID());
                parentRole.setStatus(AppsConstants.Status.INA);
                parentRole.setModifiedDate(date);
                parentRole.setModifiedBy(credentialsDTO.getUserName());
                this.roleDao.saveAndFlush(parentRole);

                updateLowerLevelRQ = new UpdateLowerLevelRQ(approveRejectRQ.getApproveRejectDataID(), role.getParentRecordID(),
                        credentialsDTO, date);
                needToUpdateLowerLevel = true;
            }
        } else {
            role.setStatus(AppsConstants.Status.INA);
        }
        response = roleDao.saveAndFlush(role);
    }

    @Override
    protected boolean isValidApproval() {
        return true;
    }

    @Override
    protected void updateLowerLevelData() {
        if (needToUpdateLowerLevel) {
            //TODO: No need for sampath bank,
        }

    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
}

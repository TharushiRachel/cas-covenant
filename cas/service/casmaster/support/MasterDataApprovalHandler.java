package com.itechro.cas.service.casmaster.support;

import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.common.Approvable;
import com.itechro.cas.model.domain.common.ApprovableEntity;
import com.itechro.cas.model.dto.casmaster.ApproveRejectRQ;
import com.itechro.cas.model.dto.casmaster.UpdateLowerLevelRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public abstract class MasterDataApprovalHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MasterDataApprovalHandler.class);

    protected CredentialsDTO credentialsDTO;

    protected Date date;

    protected boolean needToUpdateLowerLevel;

    protected UpdateLowerLevelRQ updateLowerLevelRQ;

    protected ApproveRejectRQ approveRejectRQ;

    protected Approvable response;

    protected boolean approveRestrictedForOwnUser;


    protected void init() throws AppsException {
        LOG.info("START: ApprovalProcess {}", approveRejectRQ);
    }

    public abstract void transitStatus() throws AppsException;

    protected abstract void updateApproveStatus() throws AppsException;

    protected abstract boolean isValidApproval();

    protected abstract void updateLowerLevelData();

    public void setApproveRestrictedForOwnUser(Boolean approveRestrictedForOwnUser) {
        this.approveRestrictedForOwnUser = approveRestrictedForOwnUser;
    }

    public void setCredentialsDTO(CredentialsDTO credentialsDTO) {
        this.credentialsDTO = credentialsDTO;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setApproveRejectRQ(ApproveRejectRQ approveRejectRQ) {
        this.approveRejectRQ = approveRejectRQ;
    }

    public Approvable getResponse() {
        return response;
    }

    public void hasUserAuthorizedForApproval(ApprovableEntity approvableEntity) throws AppsException {
        if (approveRestrictedForOwnUser) {
            if (StringUtils.isNotBlank(approvableEntity.getModifiedBy())) {
                if (approvableEntity.getModifiedBy().equalsIgnoreCase(credentialsDTO.getUserName())) {
                    LOG.error("ERROR: User has no authority to approve thier own authority, {}, {}", approveRejectRQ, credentialsDTO);
                    throw new AppsException(ICasErrorCode.APPS_EXCEPTION_INVALID_APPROVE_USER_CANNOT_APPROVE_OWN_CHANGES);
                }
            }
            if (approvableEntity.getCreatedBy().equalsIgnoreCase(credentialsDTO.getUserName())) {
                LOG.error("ERROR: User has no authority to approve thier own authority, {}, {}", approveRejectRQ, credentialsDTO);
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_INVALID_APPROVE_USER_CANNOT_APPROVE_OWN_CHANGES);
            }
        }
    }
}

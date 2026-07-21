package com.itechro.cas.service.casmaster.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.casmaster.CreditFacilityTemplateDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.casmaster.CftInterestRate;
import com.itechro.cas.model.domain.casmaster.CftSupportingDoc;
import com.itechro.cas.model.domain.casmaster.CftVitalInfo;
import com.itechro.cas.model.domain.casmaster.CreditFacilityTemplate;
import com.itechro.cas.model.dto.casmaster.CftVitalInfoDTO;
import com.itechro.cas.model.dto.casmaster.UpdateLowerLevelRQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class CFTemplateApproveHandler extends MasterDataApprovalHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CFTemplateApproveHandler.class);

    private CreditFacilityTemplateDao creditFacilityTemplateDao;

    @Override
    public void transitStatus() throws AppsException {
        this.init();

        if (this.isValidApproval()) {
            this.updateApproveStatus();
            this.updateLowerLevelData();
            LOG.info("Credit Facility Template ApproveStatus change : {} : {}", approveRejectRQ, credentialsDTO);
        } else {
            LOG.error("ERROR : Credit Facility Template ApproveStatus change detect : Invalid status transition : {} : {}",
                    approveRejectRQ, credentialsDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_INVALID_APPROVE_STATUS_CHANGE);
        }
    }

    @Override
    protected void updateApproveStatus() throws AppsException {
        CreditFacilityTemplate creditFacilityTemplate = null;
        Date date = new Date();

        creditFacilityTemplate = creditFacilityTemplateDao.getOne(approveRejectRQ.getApproveRejectDataID());
        hasUserAuthorizedForApproval(creditFacilityTemplate);
        creditFacilityTemplate.setModifiedBy(credentialsDTO.getUserName());
        creditFacilityTemplate.setModifiedDate(date);
        creditFacilityTemplate.setApproveStatus(approveRejectRQ.getApproveStatus());
        creditFacilityTemplate.setApprovedDate(date);
        creditFacilityTemplate.setApprovedBy(credentialsDTO.getUserName());

        for (CftSupportingDoc doc : creditFacilityTemplate.getCftSupportingDocs()) {
            doc.setModifiedBy(credentialsDTO.getUserName());
            doc.setModifiedDate(date);
            doc.setApproveStatus(approveRejectRQ.getApproveStatus());
            doc.setApprovedDate(date);
            doc.setApprovedBy(credentialsDTO.getUserName());
        }
        for (CftVitalInfo vitalInfo : creditFacilityTemplate.getCftVitalInfos()) {
            vitalInfo.setModifiedBy(credentialsDTO.getUserName());
            vitalInfo.setModifiedDate(date);
        }
        for (CftInterestRate interestRate : creditFacilityTemplate.getCftInterestRates()) {
            interestRate.setModifiedBy(credentialsDTO.getUserName());
            interestRate.setModifiedDate(date);
            interestRate.setApproveStatus(approveRejectRQ.getApproveStatus());
            interestRate.setApprovedDate(date);
            interestRate.setApprovedBy(credentialsDTO.getUserName());
        }

        if (approveRejectRQ.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED) {
            if (creditFacilityTemplate.getParentRecordID() != null) {
                CreditFacilityTemplate parentCreditFacilityTemplate = creditFacilityTemplateDao.getOne(creditFacilityTemplate.getParentRecordID());
                parentCreditFacilityTemplate.setStatus(AppsConstants.Status.INA);
                parentCreditFacilityTemplate.setModifiedDate(date);
                parentCreditFacilityTemplate.setModifiedBy(credentialsDTO.getUserName());
                this.creditFacilityTemplateDao.saveAndFlush(parentCreditFacilityTemplate);
            }
        } else {
            creditFacilityTemplate.setStatus(AppsConstants.Status.INA);
        }
        response = creditFacilityTemplateDao.saveAndFlush(creditFacilityTemplate);

    }

    @Override
    protected boolean isValidApproval() {
        return true;
    }

    @Override
    protected void updateLowerLevelData() {

    }

    public void setCreditFacilityTemplateDao(CreditFacilityTemplateDao creditFacilityTemplateDao) {
        this.creditFacilityTemplateDao = creditFacilityTemplateDao;
    }
}

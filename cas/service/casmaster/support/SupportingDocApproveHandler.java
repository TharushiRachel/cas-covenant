package com.itechro.cas.service.casmaster.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.casmaster.CftSupportingDocDao;
import com.itechro.cas.dao.casmaster.CreditFacilityTemplateDao;
import com.itechro.cas.dao.casmaster.CreditFacilityTypeDao;
import com.itechro.cas.dao.casmaster.SupportingDocDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.casmaster.*;
import com.itechro.cas.model.dto.casmaster.CftInterestRateDTO;
import com.itechro.cas.model.dto.casmaster.CftSupportingDocDTO;
import com.itechro.cas.model.dto.casmaster.CreditFacilityTemplateDTO;
import com.itechro.cas.model.dto.casmaster.UpdateLowerLevelRQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SupportingDocApproveHandler extends MasterDataApprovalHandler {

    private static final Logger LOG = LoggerFactory.getLogger(SupportingDocApproveHandler.class);

    private SupportingDocDao supportingDocDao;

    private CftSupportingDocDao cftSupportingDocDao;

    private CreditFacilityTemplateDao creditFacilityTemplateDao;

    private CreditFacilityTypeDao creditFacilityTypeDao;

    @Override
    public void transitStatus() throws AppsException {
        this.init();
        if (this.isValidApproval()) {
            this.updateApproveStatus();
            this.updateLowerLevelData();
            LOG.info("Support Document ApproveStatus change : {} : {}", approveRejectRQ, credentialsDTO);
        } else {
            LOG.error("ERROR : Support doc approve status change detect : Invalid status transition : {} : {}",
                    approveRejectRQ, credentialsDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_INVALID_APPROVE_STATUS_CHANGE);
        }
    }

    @Override
    protected boolean isValidApproval() {
        return true;
    }

    @Override
    protected void updateApproveStatus() throws AppsException {
        SupportingDoc supportingDoc = null;

        supportingDoc = supportingDocDao.getOne(approveRejectRQ.getApproveRejectDataID());
        hasUserAuthorizedForApproval(supportingDoc);
        supportingDoc.setModifiedBy(credentialsDTO.getUserName());
        supportingDoc.setModifiedDate(date);
        supportingDoc.setApproveStatus(approveRejectRQ.getApproveStatus());
        supportingDoc.setApprovedBy(credentialsDTO.getUserName());
        supportingDoc.setApprovedDate(date);
        if (approveRejectRQ.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED) {
            if (supportingDoc.getParentRecordID() != null) {
                SupportingDoc parentSupportingDoc = supportingDocDao.getOne(supportingDoc.getParentRecordID());
                parentSupportingDoc.setStatus(AppsConstants.Status.INA);
                parentSupportingDoc.setModifiedDate(date);
                parentSupportingDoc.setModifiedBy(credentialsDTO.getUserName());
                this.supportingDocDao.saveAndFlush(parentSupportingDoc);

                updateLowerLevelRQ = new UpdateLowerLevelRQ(approveRejectRQ.getApproveRejectDataID(), supportingDoc.getParentRecordID(),
                        credentialsDTO, date);
                needToUpdateLowerLevel = true;
            }
        } else {
            supportingDoc.setStatus(AppsConstants.Status.INA);
        }
        response = supportingDocDao.saveAndFlush(supportingDoc);
    }

    @Override
    protected void updateLowerLevelData() {
        if (needToUpdateLowerLevel) {

            List<CftSupportingDoc> pendingCftSupportingDocs = this.cftSupportingDocDao.findAllBySupportingDoc_SupportingDocIDAndApproveStatusAndStatus(updateLowerLevelRQ.getPreviousEntityID(),
                    DomainConstants.MasterDataApproveStatus.PENDING, AppsConstants.Status.ACT);
            if (pendingCftSupportingDocs != null) {
                for (CftSupportingDoc cftSupportingDoc : pendingCftSupportingDocs) {
                    cftSupportingDoc.setSupportingDoc(this.supportingDocDao.getOne(updateLowerLevelRQ.getNewEntityID()));
                }
            }
            List<CftSupportingDoc> approvedCftSupportingDocs = this.cftSupportingDocDao.findAllBySupportingDoc_SupportingDocIDAndApproveStatusAndStatus(updateLowerLevelRQ.getPreviousEntityID(),
                    DomainConstants.MasterDataApproveStatus.APPROVED, AppsConstants.Status.ACT);
            List<Integer> cfTemplateIDs = new ArrayList<>();

            if (approvedCftSupportingDocs != null) {
                for (CftSupportingDoc cftSupportingDoc : approvedCftSupportingDocs) {
                    if (!cfTemplateIDs.contains(cftSupportingDoc.getCreditFacilityTemplate().getCreditFacilityTemplateID())) {
                        cfTemplateIDs.add(cftSupportingDoc.getCreditFacilityTemplate().getCreditFacilityTemplateID());
                    }
                }
            }
            List<CreditFacilityTemplateDTO> templateList = new ArrayList<>();

            for (Integer templateID : cfTemplateIDs) {
                CreditFacilityTemplate previousData = this.creditFacilityTemplateDao.getOne(templateID);
                if (previousData.getStatus() == AppsConstants.Status.ACT) {
                    CreditFacilityTemplateDTO creditFacilityTemplateDTO = new CreditFacilityTemplateDTO(previousData);
                    templateList.add(creditFacilityTemplateDTO);
                    previousData.setStatus(AppsConstants.Status.INA);
                }
            }


            for (CreditFacilityTemplateDTO updateDTO : templateList) {

                CreditFacilityTemplate creditFacilityTemplate = new CreditFacilityTemplate();
                creditFacilityTemplate.setCreatedBy(updateLowerLevelRQ.getCredentialsDTO().getUserName());
                creditFacilityTemplate.setCreatedDate(updateLowerLevelRQ.getDate());
                creditFacilityTemplate.setCreditFacilityName(updateDTO.getCreditFacilityName());
                creditFacilityTemplate.setDescription(updateDTO.getDescription());
                creditFacilityTemplate.setMaxFacilityAmount(updateDTO.getMaxFacilityAmount());
                creditFacilityTemplate.setMinFacilityAmount(updateDTO.getMinFacilityAmount());
                creditFacilityTemplate.setShowCondition(updateDTO.getShowCondition());
                creditFacilityTemplate.setShowPurpose(updateDTO.getShowPurpose());
                creditFacilityTemplate.setShowRemark(updateDTO.getShowRemark());
                creditFacilityTemplate.setShowRentalData(updateDTO.getShowRentalData());
                creditFacilityTemplate.setShowRepayment(updateDTO.getShowRepayment());
                creditFacilityTemplate.setStatus(updateDTO.getStatus());

                CreditFacilityType creditFacilityType = creditFacilityTypeDao.getOne(updateDTO.getCreditFacilityTypeID());
                creditFacilityTemplate.setCreditFacilityType(creditFacilityType);

                creditFacilityTemplate.setApproveStatus(approveRejectRQ.getApproveStatus());
                creditFacilityTemplate.setApprovedBy(updateLowerLevelRQ.getCredentialsDTO().getUserName());
                creditFacilityTemplate.setApprovedDate(updateLowerLevelRQ.getDate());

                for (CftInterestRateDTO cftInterestRateDTO : updateDTO.getCftInterestRateDTOList()) {
                    CftInterestRate interestRate = null;
                    interestRate = new CftInterestRate();
                    interestRate.setCreatedBy(updateLowerLevelRQ.getCredentialsDTO().getUserName());
                    interestRate.setCreatedDate(updateLowerLevelRQ.getDate());
                    interestRate.setCreditFacilityTemplate(creditFacilityTemplate);
                    interestRate.setApproveStatus(approveRejectRQ.getApproveStatus());
                    interestRate.setApprovedBy(updateLowerLevelRQ.getCredentialsDTO().getUserName());
                    interestRate.setApprovedDate(updateLowerLevelRQ.getDate());
                    interestRate.setRateName(cftInterestRateDTO.getRateName());
                    interestRate.setRateCode(cftInterestRateDTO.getRateCode());
                    interestRate.setIsDefault(cftInterestRateDTO.getIsDefault());
                    interestRate.setValue(cftInterestRateDTO.getValue());
                    interestRate.setStatus(cftInterestRateDTO.getStatus());

                    creditFacilityTemplate.getCftInterestRates().add(interestRate);
                }

                for (CftSupportingDocDTO cftSupportingDocDTO : updateDTO.getCftSupportingDocDTOList()) {
                    CftSupportingDoc cftSupportingDoc = null;
                    cftSupportingDoc = new CftSupportingDoc();
                    cftSupportingDoc.setCreatedBy(updateLowerLevelRQ.getCredentialsDTO().getUserName());
                    cftSupportingDoc.setCreatedDate(updateLowerLevelRQ.getDate());
                    cftSupportingDoc.setCreditFacilityTemplate(creditFacilityTemplate);
                    cftSupportingDoc.setApproveStatus(approveRejectRQ.getApproveStatus());
                    cftSupportingDoc.setApprovedBy(updateLowerLevelRQ.getCredentialsDTO().getUserName());
                    cftSupportingDoc.setApprovedDate(updateLowerLevelRQ.getDate());
                    cftSupportingDoc.setMandatory(cftSupportingDocDTO.getMandatory());
                    if (updateLowerLevelRQ.getPreviousEntityID().equals(cftSupportingDocDTO.getSupportingDocID())) {
                        cftSupportingDoc.setSupportingDoc(this.supportingDocDao.getOne(updateLowerLevelRQ.getNewEntityID()));
                    } else {
                        cftSupportingDoc.setSupportingDoc(this.supportingDocDao.getOne(cftSupportingDocDTO.getSupportingDocID()));
                    }
                    cftSupportingDoc.setStatus(cftSupportingDocDTO.getStatus());

                    creditFacilityTemplate.getCftSupportingDocs().add(cftSupportingDoc);
                }

                this.creditFacilityTemplateDao.saveAndFlush(creditFacilityTemplate);
            }
        }
    }

    public void setSupportingDocDao(SupportingDocDao supportingDocDao) {
        this.supportingDocDao = supportingDocDao;
    }

    public void setCftSupportingDocDao(CftSupportingDocDao cftSupportingDocDao) {
        this.cftSupportingDocDao = cftSupportingDocDao;
    }

    public void setCreditFacilityTemplateDao(CreditFacilityTemplateDao creditFacilityTemplateDao) {
        this.creditFacilityTemplateDao = creditFacilityTemplateDao;
    }

    public void setCreditFacilityTypeDao(CreditFacilityTypeDao creditFacilityTypeDao) {
        this.creditFacilityTypeDao = creditFacilityTypeDao;
    }
}

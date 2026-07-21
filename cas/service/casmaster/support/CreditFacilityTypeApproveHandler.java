package com.itechro.cas.service.casmaster.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.casmaster.CreditFacilityTemplateDao;
import com.itechro.cas.dao.casmaster.CreditFacilityTypeDao;
import com.itechro.cas.dao.casmaster.SupportingDocDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.casmaster.*;
import com.itechro.cas.model.dto.casmaster.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CreditFacilityTypeApproveHandler extends MasterDataApprovalHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CreditFacilityTypeApproveHandler.class);

    private CreditFacilityTypeDao creditFacilityTypeDao;

    private SupportingDocDao supportingDocDao;

    private CreditFacilityTemplateDao creditFacilityTemplateDao;

    @Override
    public void transitStatus() throws AppsException {
        this.init();

        if (this.isValidApproval()) {
            this.updateApproveStatus();
            this.updateLowerLevelData();
            LOG.info("Credit Facility Type ApproveStatus change : {} : {}", approveRejectRQ, credentialsDTO);
        } else {
            LOG.error("ERROR : Credit Facility Type ApproveStatus change detect : Invalid status transition : {} : {}",
                    approveRejectRQ, credentialsDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_INVALID_APPROVE_STATUS_CHANGE);
        }
    }

    @Override
    protected void updateApproveStatus() throws AppsException {
        CreditFacilityType creditFacilityType = null;
        creditFacilityType = creditFacilityTypeDao.getOne(approveRejectRQ.getApproveRejectDataID());
        hasUserAuthorizedForApproval(creditFacilityType);
        creditFacilityType.setModifiedBy(credentialsDTO.getUserName());
        creditFacilityType.setModifiedDate(date);
        creditFacilityType.setApproveStatus(approveRejectRQ.getApproveStatus());
        creditFacilityType.setApprovedBy(credentialsDTO.getUserName());
        creditFacilityType.setApprovedDate(date);
        if (approveRejectRQ.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED) {
            if (creditFacilityType.getParentRecordID() != null) {
                CreditFacilityType parentCreditFacilityType = creditFacilityTypeDao.getOne(creditFacilityType.getParentRecordID());
                parentCreditFacilityType.setStatus(AppsConstants.Status.INA);
                parentCreditFacilityType.setModifiedDate(date);
                parentCreditFacilityType.setModifiedBy(credentialsDTO.getUserName());
                this.creditFacilityTypeDao.saveAndFlush(parentCreditFacilityType);

                updateLowerLevelRQ = new UpdateLowerLevelRQ(approveRejectRQ.getApproveRejectDataID(), creditFacilityType.getParentRecordID(),
                        credentialsDTO, date);
                this.needToUpdateLowerLevel = true;
            }
        } else {
            creditFacilityType.setStatus(AppsConstants.Status.INA);
        }

        LOG.info("Credit Facility Type ApproveStatus changed : {} : {}", approveRejectRQ, credentialsDTO);
        response = creditFacilityTypeDao.saveAndFlush(creditFacilityType);
    }

    @Override
    protected boolean isValidApproval() {
        return true;
    }

    @Override
    protected void updateLowerLevelData() {
        if (needToUpdateLowerLevel) {

            List<CreditFacilityTemplate> pendingUpdateEntities = this.creditFacilityTemplateDao.findAllByCreditFacilityType_CreditFacilityTypeIDAndApproveStatusAndStatus(updateLowerLevelRQ.getPreviousEntityID(),
                    DomainConstants.MasterDataApproveStatus.PENDING, AppsConstants.Status.ACT);
            if (pendingUpdateEntities != null) {
                for (CreditFacilityTemplate creditFacilityTemplate : pendingUpdateEntities) {
                    creditFacilityTemplate.setCreditFacilityType(this.creditFacilityTypeDao.getOne(updateLowerLevelRQ.getNewEntityID()));
                }
            }

            List<CreditFacilityTemplate> previousDataList = this.creditFacilityTemplateDao.findAllByCreditFacilityType_CreditFacilityTypeIDAndApproveStatusAndStatus(updateLowerLevelRQ.getPreviousEntityID(),
                    DomainConstants.MasterDataApproveStatus.APPROVED, AppsConstants.Status.ACT);
            List<CreditFacilityTemplateDTO> templateList = new ArrayList<>();

            if (previousDataList != null) {
                for (CreditFacilityTemplate previousData : previousDataList) {
                    CreditFacilityTemplateDTO creditFacilityTemplateDTO = new CreditFacilityTemplateDTO(previousData);
                    creditFacilityTemplateDTO.setCreditFacilityTypeID(updateLowerLevelRQ.getNewEntityID());
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

                CreditFacilityType creditFacilityType = creditFacilityTypeDao.getOne(approveRejectRQ.getApproveRejectDataID());
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

                for (CftVitalInfoDTO cftVitalInfoDTO : updateDTO.getCftVitalInfoDTOList()) {
                    CftVitalInfo cftVitalInfo = null;
                    cftVitalInfo = new CftVitalInfo();
                    cftVitalInfo.setCreatedBy(updateLowerLevelRQ.getCredentialsDTO().getUserName());
                    cftVitalInfo.setCreatedDate(updateLowerLevelRQ.getDate());
                    cftVitalInfo.setCreditFacilityTemplate(creditFacilityTemplate);
                    cftVitalInfo.setMandatory(cftVitalInfoDTO.getMandatory());
                    cftVitalInfo.setVitalInfoName(cftVitalInfoDTO.getVitalInfoName());
                    cftVitalInfo.setDisplayOrder(cftVitalInfoDTO.getDisplayOrder());
                    cftVitalInfo.setStatus(cftVitalInfoDTO.getStatus());

                    creditFacilityTemplate.getCftVitalInfos().add(cftVitalInfo);
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
                    cftSupportingDoc.setSupportingDoc(this.supportingDocDao.getOne(cftSupportingDocDTO.getSupportingDocID()));
                    cftSupportingDoc.setStatus(cftSupportingDocDTO.getStatus());

                    creditFacilityTemplate.getCftSupportingDocs().add(cftSupportingDoc);
                }

                this.creditFacilityTemplateDao.saveAndFlush(creditFacilityTemplate);
            }

            LOG.info("Credit Facility Type lower level changed : {} : {}", approveRejectRQ, credentialsDTO);
        }
    }

    public void setCreditFacilityTypeDao(CreditFacilityTypeDao creditFacilityTypeDao) {
        this.creditFacilityTypeDao = creditFacilityTypeDao;
    }

    public void setSupportingDocDao(SupportingDocDao supportingDocDao) {
        this.supportingDocDao = supportingDocDao;
    }

    public void setCreditFacilityTemplateDao(CreditFacilityTemplateDao creditFacilityTemplateDao) {
        this.creditFacilityTemplateDao = creditFacilityTemplateDao;
    }
}

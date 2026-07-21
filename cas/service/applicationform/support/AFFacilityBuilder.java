package com.itechro.cas.service.applicationform.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.applicationform.AFFacilityDao;
import com.itechro.cas.dao.applicationform.ApplicationFormDao;
import com.itechro.cas.dao.casmaster.CreditFacilityTemplateDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.applicationform.AFFacility;
import com.itechro.cas.model.domain.applicationform.AFFacilityInterestRate;
import com.itechro.cas.model.domain.applicationform.AFFacilityVitalInfoData;
import com.itechro.cas.model.domain.applicationform.ApplicationForm;
import com.itechro.cas.model.domain.casmaster.CftInterestRate;
import com.itechro.cas.model.domain.casmaster.CftVitalInfo;
import com.itechro.cas.model.domain.casmaster.CreditFacilityTemplate;
import com.itechro.cas.model.dto.applicationform.AFFacilityDTO;
import com.itechro.cas.model.dto.applicationform.AFFacilityInterestRateDTO;
import com.itechro.cas.model.dto.applicationform.AFFacilityVitalInfoDataDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.master.SystemParameterService;
import com.itechro.cas.util.DecimalCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class AFFacilityBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(AFFacilityBuilder.class);

    private AFFacility afFacility;

    private Date date;

    private CredentialsDTO credentialsDTO;

    private AFFacilityDTO afFacilityDTO;

    private ApplicationFormDao applicationFormDao;

    private CreditFacilityTemplateDao creditFacilityTemplateDao;

    private AFFacilityDao afFacilityDao;

    private SystemParameterService systemParameterService;

    public AFFacilityBuilder(AFFacilityDTO afFacilityDTO, CredentialsDTO credentialsDTO) {
        this.afFacilityDTO = afFacilityDTO;
        this.credentialsDTO = credentialsDTO;
        this.date = new Date();
    }

    public AFFacilityBuilder loadFacility() throws AppsException {

        boolean isNewFacility = afFacilityDTO.getFacilityID() == null;

        if (isNewFacility) {
            afFacility = new AFFacility();
            afFacility.setCreatedBy(credentialsDTO.getUserName());
            afFacility.setCreatedDate(date);

            if (afFacilityDTO.getApplicationFormID() != null) {
                ApplicationForm applicationForm = applicationFormDao.getOne(afFacilityDTO.getApplicationFormID());
                afFacility.setApplicationForm(applicationForm);
            }

            if (afFacilityDTO.getCreditFacilityTemplateID() == null) {
                LOG.error("ERROR: Facility template is mandatory for facility: {}", afFacilityDTO);
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FACILITY_CANNOT_INITIAL_FACILITY_WITHOUT_TEMPLATE);
            } else {
                CreditFacilityTemplate creditFacilityTemplate = creditFacilityTemplateDao.getOne(afFacilityDTO.getCreditFacilityTemplateID());
                afFacility.setCreditFacilityTemplate(creditFacilityTemplate);
                afFacility.setCreditFacilityType(creditFacilityTemplate.getCreditFacilityType());
                afFacility.setFacilityType(creditFacilityTemplate.getCreditFacilityType().getFacilityTypeName());
            }

        } else {
            afFacility = afFacilityDao.getOne(afFacilityDTO.getFacilityID());
            afFacility.setModifiedBy(credentialsDTO.getUserName());
            afFacility.setModifiedDate(date);
        }

        LOG.info("Load basic facility details completed: {}", afFacilityDTO);
        return this;
    }

    public AFFacilityBuilder buildBaseFacility() throws AppsException {
        if (!this.isValidFacilityAmount()) {
            LOG.error("ERROR: Facility amount not in required range {},", afFacilityDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FP_FACILITY_REQUESTED_AMOUNT_NOT_ALLOWED_FOR_THIS_FACILITY);
        }
        if (!this.isApplicableAmount(afFacilityDTO.getParentFacilityID())) {
            LOG.error("ERROR: Parent facility remaining amount less than requested amount {},", afFacilityDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FP_FACILITY_PARENT_FACILITY_AMOUNT_LESS_THAN_REQUESTED_AMOUNT);
        }
        afFacility.setFacilityRefCode(afFacilityDTO.getFacilityRefCode());
        afFacility.setFacilityCurrency(afFacilityDTO.getFacilityCurrency());
        afFacility.setDisbursementAccNumber(afFacilityDTO.getDisbursementAccNumber());
        afFacility.setFacilityAmount(afFacilityDTO.getFacilityAmount());
        afFacility.setExistingAmount(afFacilityDTO.getExistingAmount());
        afFacility.setOriginalAmount(afFacilityDTO.getOriginalAmount());
        afFacility.setIsCooperate(afFacilityDTO.getIsCooperate());
        afFacility.setOutstandingAmount(afFacilityDTO.getOutstandingAmount());
        afFacility.setParentFacilityID(afFacilityDTO.getParentFacilityID());
        afFacility.setSectorID(afFacilityDTO.getSectorID());
        afFacility.setSubSectorID(afFacilityDTO.getSubSectorID());
        afFacility.setCashFlowGenerationSectorID(afFacilityDTO.getCashFlowGenerationSectorID());
        afFacility.setPurposeOfAdvance(afFacilityDTO.getPurposeOfAdvance());
        afFacility.setPurpose(afFacilityDTO.getPurpose());
        afFacility.setIsOneOff(afFacilityDTO.getIsOneOff());
        afFacility.setDirectFacility(afFacilityDTO.getDirectFacility());
        afFacility.setSeriesOfLoans(afFacilityDTO.getSeriesOfLoans());
        afFacility.setRevolving(afFacilityDTO.getRevolving());
        afFacility.setEnhancement(afFacilityDTO.getEnhancement());
        afFacility.setReduction(afFacilityDTO.getReduction());
        afFacility.setRepayment(afFacilityDTO.getRepayment());
        afFacility.setCondition(afFacilityDTO.getCondition());
        afFacility.setIsNew(afFacilityDTO.getIsNew());
        afFacility.setDisplayOrder(afFacilityDTO.getDisplayOrder());
        afFacility.setRemark(afFacilityDTO.getRemark());
        afFacility.setStatus(afFacilityDTO.getStatus());
        LOG.info("UPDATE Basic facility details completed: {}", afFacilityDTO);
        return this;
    }

    public AFFacilityBuilder buildInterestRate() {
        for (AFFacilityInterestRateDTO afFacilityInterestRateDTO : afFacilityDTO.getAfFacilityInterestRateDTOList()) {
            boolean isNewInterestRate = afFacilityInterestRateDTO.getFacilityInterestRateID() == null;
            AFFacilityInterestRate afFacilityInterestRate = null;
            if (isNewInterestRate) {
                afFacilityInterestRate = new AFFacilityInterestRate();
                afFacilityInterestRate.setCreatedBy(credentialsDTO.getUserName());
                afFacilityInterestRate.setCreatedDate(date);
                afFacilityInterestRate.setAfFacility(afFacility);
                afFacility.getAfFacilityInterestRates().add(afFacilityInterestRate);
            } else {
                afFacilityInterestRate = afFacility.getAFFacilityInterestRateByID(afFacilityInterestRateDTO.getFacilityInterestRateID());
                afFacilityInterestRate.setCreatedBy(credentialsDTO.getUserName());
                afFacilityInterestRate.setCreatedDate(date);
            }

            afFacilityInterestRate.setCftInterestRateID(afFacilityInterestRateDTO.getCftInterestRateID());
            afFacilityInterestRate.setRateCode(afFacilityInterestRateDTO.getRateCode());
            afFacilityInterestRate.setUserComment(afFacilityInterestRateDTO.getUserComment());
            afFacilityInterestRate.setValue(afFacilityInterestRateDTO.getValue());
            afFacilityInterestRate.setIsDefault(afFacilityInterestRateDTO.getIsDefault());
            afFacilityInterestRate.setStatus(afFacilityInterestRateDTO.getStatus());
        }

        for (CftInterestRate interestRate : afFacility.getCreditFacilityTemplate().getCftInterestRates()) {
            if ((interestRate.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED)
                    && afFacility.getAFFacilityInterestRateByCFTInterestRateID(interestRate.getCftInterestRateID()) == null) {
                AFFacilityInterestRate afFacilityInterestRate = null;

                afFacilityInterestRate = new AFFacilityInterestRate();
                afFacilityInterestRate.setCreatedBy(credentialsDTO.getUserName());
                afFacilityInterestRate.setCreatedDate(date);
                afFacilityInterestRate.setAfFacility(afFacility);
                afFacility.getAfFacilityInterestRates().add(afFacilityInterestRate);

                afFacilityInterestRate.setCftInterestRateID(interestRate.getCftInterestRateID());
                afFacilityInterestRate.setRateCode(interestRate.getRateCode());
                afFacilityInterestRate.setValue(interestRate.getValue());
                afFacilityInterestRate.setIsDefault(interestRate.getIsDefault());
                afFacilityInterestRate.setStatus(interestRate.getStatus());
            }
        }

        LOG.info("UPDATE facility interest rate completed: {}", afFacilityDTO);
        return this;
    }

    public AFFacilityBuilder buildVitalInfo() {
        for (AFFacilityVitalInfoDataDTO afFacilityVitalInfoDataDTO : afFacilityDTO.getAfFacilityVitalInfoDataDTOList()) {
            boolean isNewRecord = afFacilityVitalInfoDataDTO.getFacilityVitalInfoDataID() == null;
            AFFacilityVitalInfoData afFacilityVitalInfoData = null;
            if (isNewRecord) {
                afFacilityVitalInfoData = new AFFacilityVitalInfoData();
                afFacilityVitalInfoData.setCreatedBy(credentialsDTO.getUserName());
                afFacilityVitalInfoData.setCreatedDate(date);
                afFacilityVitalInfoData.setAfFacility(afFacility);
                afFacility.getAfFacilityVitalInfoData().add(afFacilityVitalInfoData);
            } else {
                afFacilityVitalInfoData = afFacility.getAFFacilityVitalInfoDataByID(afFacilityVitalInfoDataDTO.getFacilityVitalInfoDataID());
                afFacilityVitalInfoData.setCreatedBy(credentialsDTO.getUserName());
                afFacilityVitalInfoData.setCreatedDate(date);
            }
            afFacilityVitalInfoData.setCftVitalInfoID(afFacilityVitalInfoDataDTO.getCftVitalInfoID());
            afFacilityVitalInfoData.setMandatory(afFacilityVitalInfoDataDTO.getMandatory());
            afFacilityVitalInfoData.setDisplayOrder(afFacilityVitalInfoDataDTO.getDisplayOrder());
            afFacilityVitalInfoData.setVitalInfoName(afFacilityVitalInfoDataDTO.getVitalInfoName());
            afFacilityVitalInfoData.setVitalInfoData(afFacilityVitalInfoDataDTO.getVitalInfoData());
            afFacilityVitalInfoData.setStatus(afFacilityVitalInfoDataDTO.getStatus());
        }

        for (CftVitalInfo cftVitalInfo : afFacility.getCreditFacilityTemplate().getCftVitalInfos()) {
            if (afFacility.getAFFacilityVitalInfoDataByCftVitalInfoID(cftVitalInfo.getCftVitalInfoID()) == null && cftVitalInfo.getStatus() == AppsConstants.Status.ACT) {
                AFFacilityVitalInfoData facilityVitalInfoData = null;

                facilityVitalInfoData = new AFFacilityVitalInfoData();
                facilityVitalInfoData.setCreatedBy(credentialsDTO.getUserName());
                facilityVitalInfoData.setCreatedDate(date);
                facilityVitalInfoData.setAfFacility(afFacility);
                afFacility.getAfFacilityVitalInfoData().add(facilityVitalInfoData);

                facilityVitalInfoData.setCftVitalInfoID(cftVitalInfo.getCftVitalInfoID());
                facilityVitalInfoData.setVitalInfoName(cftVitalInfo.getVitalInfoName());
                facilityVitalInfoData.setDisplayOrder(cftVitalInfo.getDisplayOrder());
                facilityVitalInfoData.setMandatory(cftVitalInfo.getMandatory());
                facilityVitalInfoData.setStatus(cftVitalInfo.getStatus());
            }
        }

        LOG.info("UPDATE facility vital info completed: {}", afFacilityDTO);
        return this;
    }

    public boolean isValidFacilityAmount() {
        if (afFacilityDTO.getIsNew() == AppsConstants.YesNo.Y) {
            return DecimalCalculator.isGreaterThanOrEq(this.afFacility.getCreditFacilityTemplate().getMaxFacilityAmount(), this.afFacilityDTO.getFacilityAmount())
                    && DecimalCalculator.isGreaterThanOrEq(this.afFacilityDTO.getFacilityAmount(), this.afFacility.getCreditFacilityTemplate().getMinFacilityAmount());
        } else {
            return true;
        }
    }

    public boolean isApplicableAmount(Integer parentFacilityID) {
        if (parentFacilityID != null) {
            AFFacility parentFacility = this.afFacilityDao.getOne(parentFacilityID);
            BigDecimal parentFacilityAmount = parentFacility.getFacilityAmount();
            Boolean isTotalChildFacilitiesAmountValidationIsEnabled = systemParameterService.isTotalChildFacilitiesAmountValidationIsEnabled();

            if (!afFacilityDTO.getFacilityCurrency().toUpperCase().equals(parentFacility.getFacilityCurrency().toUpperCase())) {
                LOG.warn("Different Facility Currencies : Child Facility Currency : {} Parent Facility Currency {}", afFacilityDTO.getFacilityCurrency(), parentFacility.getFacilityCurrency());
                return true;
            }

            if (isTotalChildFacilitiesAmountValidationIsEnabled) {
                BigDecimal totalChildFacilityAmount = DecimalCalculator.getDefaultZero();
                BigDecimal remainFacilityAmount;
                List<AFFacility> facilityList = this.afFacilityDao.findAllByParentFacilityIDAndStatusAndParentFacilityIDIsNotNull(parentFacility.getFacilityID(), AppsConstants.Status.ACT);

                for (AFFacility childFacility : facilityList) {
                    if (childFacility.getFacilityAmount() != null && !childFacility.getFacilityID().equals(this.afFacilityDTO.getFacilityID())) {
                        totalChildFacilityAmount = DecimalCalculator.add(totalChildFacilityAmount, childFacility.getFacilityActualAmount());
                    }
                }
                remainFacilityAmount = DecimalCalculator.subtract(parentFacilityAmount, totalChildFacilityAmount);

                return DecimalCalculator.isLessThanOrEq(this.afFacilityDTO.getFacilityActualAmount(), remainFacilityAmount);
            } else {
                return DecimalCalculator.isLessThanOrEq(this.afFacilityDTO.getFacilityActualAmount(), parentFacilityAmount);
            }

        } else {
            return true;
        }
    }

    public void setAfFacility(AFFacility afFacility) {
        this.afFacility = afFacility;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCredentialsDTO(CredentialsDTO credentialsDTO) {
        this.credentialsDTO = credentialsDTO;
    }

    public void setAfFacilityDTO(AFFacilityDTO afFacilityDTO) {
        this.afFacilityDTO = afFacilityDTO;
    }

    public void setApplicationFormDao(ApplicationFormDao applicationFormDao) {
        this.applicationFormDao = applicationFormDao;
    }

    public void setAfFacilityDao(AFFacilityDao afFacilityDao) {
        this.afFacilityDao = afFacilityDao;
    }

    public void setCreditFacilityTemplateDao(CreditFacilityTemplateDao creditFacilityTemplateDao) {
        this.creditFacilityTemplateDao = creditFacilityTemplateDao;
    }

    public AFFacility getAfFacility() {
        return afFacility;
    }

    public void setSystemParameterService(SystemParameterService systemParameterService) {
        this.systemParameterService = systemParameterService;
    }
}

package com.itechro.cas.service.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.casmaster.CreditFacilityTemplateDao;
import com.itechro.cas.dao.facility.FacilityRepaymentDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.casmaster.*;
import com.itechro.cas.model.domain.facilitypaper.facility.*;
import com.itechro.cas.model.dto.casmaster.FacilityCustomInfoDataDTO;
import com.itechro.cas.model.dto.facility.*;
import com.itechro.cas.model.dto.facilitypaper.creditcalculator.CalculationDTO;
import com.itechro.cas.model.dto.facilitypaper.creditcalculator.CalculatorInputDTO;
import com.itechro.cas.model.dto.facilitypaper.creditcalculator.CalculatorResponse;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.faclititypaper.creditcalculator.CalculatorService;
import com.itechro.cas.service.master.SystemParameterService;
import com.itechro.cas.util.CreditCalculatorUtil;
import com.itechro.cas.util.DecimalCalculator;
import com.itechro.cas.util.NumberUtil;
import com.itechro.cas.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class FacilityBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(FacilityBuilder.class);

    private FacilityPaper facilityPaper;

    private Facility facility;

    private Date date;

    private CredentialsDTO credentialsDTO;

    private FacilityDTO facilityDTO;

    private CreditFacilityTemplateDao creditFacilityTemplateDao;

    private FacilityRepaymentDao facilityRepaymentDao;

    private SystemParameterService systemParameterService;

    private CalculatorService calculatorService;

    private CalculatorResponse calculatorResponse;

    private AppsConstants.YesNo isCreditCalculationsEnabled;

    public FacilityBuilder(FacilityDTO facilityDTO, CredentialsDTO credentialsDTO) {
        this.facilityDTO = facilityDTO;
        this.credentialsDTO = credentialsDTO;
        this.date = new Date();
    }

    public FacilityBuilder loadFacility() throws AppsException {

        boolean isNewFacility = facilityDTO.getFacilityID() == null;

        if (isNewFacility) {
            facility = new Facility();
            facility.setCreatedBy(credentialsDTO.getUserName());
            facility.setCreatedDate(date);

            if (facilityDTO.getFacilityPaperID() != null) {
                facilityPaper.addFacility(facility);
            }

            if (facilityDTO.getCreditFacilityTemplateID() == null) {
                LOG.error("ERROR: Facility template is mandatory for facility: {}", facilityDTO);
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FACILITY_CANNOT_INITIAL_FACILITY_WITHOUT_TEMPLATE);
            } else {
                CreditFacilityTemplate creditFacilityTemplate = creditFacilityTemplateDao.getOne(facilityDTO.getCreditFacilityTemplateID());
                facility.setCreditFacilityTemplate(creditFacilityTemplate);
                facility.setCreditFacilityType(creditFacilityTemplate.getCreditFacilityType());
                facility.setFacilityType(creditFacilityTemplate.getCreditFacilityType().getFacilityTypeName());
            }
            if (facilityDTO.getFacilityRepaymentID() != null) {
                FacilityRepayment facilityRepayment = facilityRepaymentDao.getOne(facilityDTO.getFacilityRepaymentID());
                facility.setFacilityRepayment(facilityRepayment);
            }
        } else {
            facility = facilityPaper.getFacilityByID(facilityDTO.getFacilityID());
            facility.setModifiedBy(credentialsDTO.getUserName());
            facility.setModifiedDate(date);
        }

        LOG.info("Load basic facility details completed: {}", facilityDTO);
        return this;
    }

    public FacilityBuilder buildBaseFacility() throws AppsException {
        if (!this.isValidFacilityAmount()) {
            LOG.error("ERROR: Facility amount not in required range {},", facilityDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FP_FACILITY_REQUESTED_AMOUNT_NOT_ALLOWED_FOR_THIS_FACILITY);
        }
        if (!this.isApplicableAmount(facilityDTO.getParentFacilityID())) {
            LOG.error("ERROR: Parent facility remaining amount less than requested amount {},", facilityDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FP_FACILITY_PARENT_FACILITY_AMOUNT_LESS_THAN_REQUESTED_AMOUNT);
        }
        facility.setFacilityRefCode(facilityDTO.getFacilityRefCode());
        facility.setFacilityCurrency(facilityDTO.getFacilityCurrency());
        facility.setDisbursementAccNumber(facilityDTO.getDisbursementAccNumber());
        facility.setFacilityAmount(facilityDTO.getFacilityAmount());
        facility.setExistingAmount(facilityDTO.getExistingAmount());
        facility.setOriginalAmount(facilityDTO.getOriginalAmount());
        facility.setIsCooperate(facilityDTO.getIsCooperate());
        facility.setOutstandingAmount(facilityDTO.getOutstandingAmount());
        facility.setParentFacilityID(facilityDTO.getParentFacilityID());
        facility.setSectorID(facilityDTO.getSectorID());
        facility.setSubSectorID(facilityDTO.getSubSectorID());
        facility.setCashFlowGenerationSectorID(facilityDTO.getCashFlowGenerationSectorID());
        facility.setPurposeOfAdvance(facilityDTO.getPurposeOfAdvance());
        facility.setPurpose(facilityDTO.getPurpose());
        facility.setIsOneOff(facilityDTO.getIsOneOff());
        facility.setDirectFacility(facilityDTO.getDirectFacility());
        facility.setSeriesOfLoans(facilityDTO.getSeriesOfLoans());
        facility.setRevolving(facilityDTO.getRevolving());
        facility.setEnhancement(facilityDTO.getEnhancement());
        facility.setReduction(facilityDTO.getReduction());
        facility.setRepayment(facilityDTO.getRepayment());
        facility.setCondition(facilityDTO.getCondition());
        facility.setIsNew(facilityDTO.getIsNew());
        facility.setIsAnnual(facilityDTO.getIsAnnual());
        facility.setIsAdditional(facilityDTO.getIsAdditional());
        facility.setIsTermsAmended(facilityDTO.getIsTermsAmended());
        facility.setIsReStructure(facilityDTO.getIsReStructure());
        facility.setIsReSchedule(facilityDTO.getIsReSchedule());
        facility.setDisplayOrder(facilityDTO.getDisplayOrder());
        facility.setRemark(facilityDTO.getRemark());
        facility.setStatus(facilityDTO.getStatus());
        facility.setExchangeRate(facilityDTO.getExchangeRate());
        facility.setLoanLimitId(facilityDTO.getLoanLimitId());
        LOG.info("UPDATE Basic facility details completed: {}", facilityDTO);
        return this;
    }

    public boolean isValidFacilityAmount() {
        if (facilityDTO.getFacilityOtherFacilityInformationDTOList().size() > 0) {

            BigDecimal sellingPriceWithVAT = null;
            for (FacilityOtherFacilityInformationDTO dto : facilityDTO.getFacilityOtherFacilityInformationDTOList()) {
                if (dto.getOutputCode() != null && dto.getOutputCode().equals("OUT001")) {
                    sellingPriceWithVAT = new BigDecimal(dto.getOtherInfoData());
                }
            }

            if (sellingPriceWithVAT != null && facilityDTO.getIsNew() == AppsConstants.YesNo.Y) {
//            if (facilityDTO.getIsNew() == AppsConstants.YesNo.Y) {
                return DecimalCalculator.isGreaterThanOrEq(this.facility.getCreditFacilityTemplate().getMaxFacilityAmount(), sellingPriceWithVAT)
                        && DecimalCalculator.isGreaterThanOrEq(sellingPriceWithVAT, this.facility.getCreditFacilityTemplate().getMinFacilityAmount());
            } else {
                return true;
            }
        } else {
            if (facilityDTO.getIsNew() == AppsConstants.YesNo.Y) {
                return DecimalCalculator.isGreaterThanOrEq(this.facility.getCreditFacilityTemplate().getMaxFacilityAmount(), this.facilityDTO.getFacilityAmount())
                        && DecimalCalculator.isGreaterThanOrEq(this.facilityDTO.getFacilityAmount(), this.facility.getCreditFacilityTemplate().getMinFacilityAmount());
            } else {
                return true;
            }
        }
    }

    public boolean isApplicableAmount(Integer parentFacilityID) {
        if (parentFacilityID != null) {
            Facility parentFacility = this.facilityPaper.getFacilityByID(parentFacilityID);
            BigDecimal parentFacilityAmount = parentFacility.getFacilityAmount();

            if (!facilityDTO.getFacilityCurrency().toUpperCase().equals(parentFacility.getFacilityCurrency().toUpperCase())) {
                LOG.warn("Different Facility Currencies : Child Facility Currency : {} Parent Facility Currency {}", facilityDTO.getFacilityCurrency(), parentFacility.getFacilityCurrency());
                return true;
            }

            Boolean isTotalChildFacilitiesAmountValidationIsEnabled = systemParameterService.isTotalChildFacilitiesAmountValidationIsEnabled();
            if (isTotalChildFacilitiesAmountValidationIsEnabled) {
                BigDecimal totalChildFacilityAmount = DecimalCalculator.getDefaultZero();
                BigDecimal remainFacilityAmount;
                List<Facility> facilityList = this.facilityPaper.getActiveFacilitiesByParentFacilityID(parentFacility.getFacilityID());

                for (Facility childFacility : facilityList) {
                    if (childFacility.getFacilityAmount() != null && !childFacility.getFacilityID().equals(this.facilityDTO.getFacilityID())) {
                        totalChildFacilityAmount = DecimalCalculator.add(totalChildFacilityAmount, childFacility.getFacilityActualAmount());
                    }
                }
                remainFacilityAmount = DecimalCalculator.subtract(parentFacilityAmount, totalChildFacilityAmount);

                return DecimalCalculator.isLessThanOrEq(this.facilityDTO.getFacilityActualAmount(), remainFacilityAmount);
            } else {
                return DecimalCalculator.isLessThanOrEq(this.facilityDTO.getFacilityActualAmount(), parentFacilityAmount);
            }

        } else {
            return true;
        }
    }

    public FacilityBuilder buildFacilityDocument() {

        for (FacilityDocumentDTO facilityDocumentDTO : facilityDTO.getFacilityDocumentList()) {
            boolean isNewFacilityDoc = facilityDocumentDTO.getFacilityDocumentID() == null;
            FacilityDocument facilityDocument = null;
            if (isNewFacilityDoc) {
                facilityDocument = new FacilityDocument();
                facilityDocument.setCreatedBy(credentialsDTO.getUserName());
                facilityDocument.setCreatedDate(date);
                facilityDocument.setFacility(facility);
                facility.getFacilityDocuments().add(facilityDocument);
            } else {
                facilityDocument = facility.getFacilityDocumentsByID(facilityDocumentDTO.getFacilityDocumentID());
                facilityDocument.setCreatedBy(credentialsDTO.getUserName());
                facilityDocument.setCreatedDate(date);
            }
            facilityDocument.setCftSupportDocID(facilityDocumentDTO.getCftSupportDocID());
            facilityDocument.setDisplayOrder(facilityDocumentDTO.getDisplayOrder());
            facilityDocument.setMandatory(facilityDocumentDTO.getMandatory());
            facilityDocument.setRemark(facilityDocumentDTO.getRemark());
            facilityDocument.setStatus(facilityDocumentDTO.getStatus());

        }
        LOG.info("UPDATE facility details document completed: {}", facilityDTO);
        return this;
    }

    public FacilityBuilder buildAdvanceOfPurpose() {
        for (FacilityPurposeOfAdvanceDTO facilityPurposeOfAdvanceDTO : facilityDTO.getFacilityPurposeOfAdvanceList()) {
            boolean isNewPurposeOfAdvance = facilityPurposeOfAdvanceDTO.getFacilityPurposeOfAdvanceID() == null;
            FacilityPurposeOfAdvance facilityPurposeOfAdvance = null;
            facility.getFacilityPurposeOfAdvances().clear();
            /*if (isNewPurposeOfAdvance) {*/
            facilityPurposeOfAdvance = new FacilityPurposeOfAdvance();
            facilityPurposeOfAdvance.setCreatedBy(credentialsDTO.getUserName());
            facilityPurposeOfAdvance.setCreatedDate(date);
            facilityPurposeOfAdvance.setFacility(facility);
            facility.getFacilityPurposeOfAdvances().add(facilityPurposeOfAdvance);
            /*} else {
                facilityPurposeOfAdvance = facility.getFacilityPurposeOfAdvanceByID(facilityPurposeOfAdvanceDTO.getFacilityPurposeOfAdvanceID());
                facilityPurposeOfAdvance.setCreatedBy(credentialsDTO.getUserName());
                facilityPurposeOfAdvance.setCreatedDate(date);
            }*/
            facilityPurposeOfAdvance.setPurposeOfAdvance(facilityPurposeOfAdvanceDTO.getPurposeOfAdvance());
            facilityPurposeOfAdvance.setStatus(facilityPurposeOfAdvanceDTO.getStatus());
        }
        LOG.info("UPDATE facility purpose of advanced completed: {}", facilityDTO);
        return this;
    }

    public FacilityBuilder buildCreditCalculatorInfo() throws SAXException, IOException {

        this.isCreditCalculationsEnabled = facility.getCreditFacilityTemplate().getShowCalculator();
        if (isCreditCalculationsEnabled.equals(AppsConstants.YesNo.Y)) {
            CalculatorInputDTO calculatorInputDTO = new CalculatorInputDTO();
            calculatorInputDTO.setFacilityType(facility.getCreditFacilityTemplate().getCreditFacilityType().getFacilityTypeName());
            List<CalculationDTO> data = new ArrayList<>();

            calculatorInputDTO.setCalculatorType(AppsConstants.CalculatorType.NORMAL.getType());//FIXME
            for (FacilityOtherFacilityInformationDTO facilityOtherFacilityInformationDTO : facilityDTO.getFacilityOtherFacilityInformationDTOList()) {
                if (facilityOtherFacilityInformationDTO.getStatus().equals(AppsConstants.Status.ACT)) {
                    if (StringUtils.isNotEmpty(facilityOtherFacilityInformationDTO.getOtherInfoData())) {
                        CalculationDTO calculationDTO = new CalculationDTO();
                        calculationDTO.setValue(facilityOtherFacilityInformationDTO.getOtherInfoData());
                        calculationDTO.setCode(facilityOtherFacilityInformationDTO.getOtherFacilityInfoCode());
                        data.add(calculationDTO);
                        calculatorInputDTO.setData(data);
                    }
                }
            }

//            TODO Structured Calculator
            this.calculatorResponse = calculatorService.getCreditCalculatorData(calculatorInputDTO, credentialsDTO);

            if (calculatorResponse.getSystemOutputResponseData() != null) {
                StringBuilder repayment = new StringBuilder();
                Formatter formatter = new Formatter(repayment);

                String repaymentPeriod = "";
                String rentalAmount = "";
                String noOfUpFronts = "";
                String rentalAmountToNoOfUpfronts = "";

                for (CalculationDTO calculationDTO : calculatorResponse.getSystemOutputResponseData()) {
                    if (CreditCalculatorUtil.getPeriodOutputCode().equals(calculationDTO.getOutputCode())) {
                        repaymentPeriod = calculationDTO.getValue();
                    }

                    if (CreditCalculatorUtil.getNoOfUpFrontsOutputCode().equals(calculationDTO.getOutputCode())) {
                        noOfUpFronts = calculationDTO.getValue();
                    }

                    if (CreditCalculatorUtil.getRentalWithVatOutputCode().equals(calculationDTO.getOutputCode())) {
                        rentalAmount = calculationDTO.getValue();
                    }

                    if (!rentalAmount.equals("") && !noOfUpFronts.equals("")) {
                        rentalAmountToNoOfUpfronts = String.valueOf(Double.valueOf(rentalAmount) * Double.valueOf(noOfUpFronts));
                    }
                }

//                TODO
                formatter.format("In %s monthly rentals of Rs.%s with %s upfront rental/s totaling to Rs.%s", repaymentPeriod, NumberUtil.getFormattedAmount(BigDecimal.valueOf(Double.parseDouble(rentalAmount))), noOfUpFronts, NumberUtil.getFormattedAmount(BigDecimal.valueOf(Double.parseDouble(rentalAmountToNoOfUpfronts))));
                facility.setRepayment(repayment.toString());
            }
            LOG.info("Update Credit Calculator Details : {}", facilityDTO);
        }

        LOG.info("Update Credit Calculator Details : {}", facilityDTO);
        return this;
    }

    public FacilityBuilder buildInterestRate() {
        for (FacilityInterestRateDTO facilityInterestRateDTO : facilityDTO.getFacilityInterestRateList()) {
            boolean isNewInterestRate = facilityInterestRateDTO.getFacilityInterestRateID() == null;
            FacilityInterestRate facilityInterestRate = null;
            if (isNewInterestRate) {
                facilityInterestRate = new FacilityInterestRate();
                facilityInterestRate.setCreatedBy(credentialsDTO.getUserName());
                facilityInterestRate.setCreatedDate(date);
                facilityInterestRate.setFacility(facility);
                facility.getFacilityInterestRates().add(facilityInterestRate);
            } else {
                facilityInterestRate = facility.getFacilityInterestRateByID(facilityInterestRateDTO.getFacilityInterestRateID());
                facilityInterestRate.setCreatedBy(credentialsDTO.getUserName());
                facilityInterestRate.setCreatedDate(date);
            }

            facilityInterestRate.setCftInterestRateID(facilityInterestRateDTO.getCftInterestRateID());
            facilityInterestRate.setInterestRatingSubCategory(facilityInterestRateDTO.getInterestRatingSubCategory());
            facilityInterestRate.setValue(facilityInterestRateDTO.getValue());
            facilityInterestRate.setRateName(facilityInterestRateDTO.getRateName());
            facilityInterestRate.setIsEditable(facilityInterestRateDTO.getIsEditable());
            facilityInterestRate.setRateCode(facilityInterestRateDTO.getRateCode());
            facilityInterestRate.setUserComment(facilityInterestRateDTO.getUserComment());
            facilityInterestRate.setIsDefault(facilityInterestRateDTO.getIsDefault());
            facilityInterestRate.setStatus(facilityInterestRateDTO.getStatus());
            if (isCreditCalculationsEnabled.equals(AppsConstants.YesNo.Y)) {

                switch (facilityInterestRateDTO.getInterestRatingSubCategory()) {
                    case EFFECTIVE: {
                        facilityInterestRate.setValue(new Double(StringUtil.formatRates(new BigDecimal(calculatorResponse.getCalculationDTOByOutputCode(CreditCalculatorUtil.getEffectiveRateOutputCode()).getValue()))));
                        break;
                    }

                    case REDUCING: {
                        facilityInterestRate.setValue(new Double(StringUtil.formatRates(new BigDecimal(calculatorResponse.getCalculationDTOByOutputCode(CreditCalculatorUtil.getReducingRateOutputCode()).getValue()))));
                        break;
                    }

                    case OTHER: {
                        break;
                    }

                    case FLAT: {
                        facilityInterestRate.setValue(new Double(StringUtil.formatRates(new BigDecimal(calculatorResponse.getCalculationDTOByOutputCode(CreditCalculatorUtil.getFlatRateOutputCode()).getValue()))));
                        break;
                    }
                    default:
                }
            }
        }

        for (CftInterestRate interestRate : facility.getCreditFacilityTemplate().getCftInterestRates()) {
            if ((interestRate.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED)
                    && facility.getFacilityInterestRateByCFTInterestRateID(interestRate.getCftInterestRateID()) == null) {
                FacilityInterestRate facilityInterestRate = null;

                facilityInterestRate = new FacilityInterestRate();
                facilityInterestRate.setCreatedBy(credentialsDTO.getUserName());
                facilityInterestRate.setCreatedDate(date);
                facilityInterestRate.setFacility(facility);
                facility.getFacilityInterestRates().add(facilityInterestRate);

                facilityInterestRate.setCftInterestRateID(interestRate.getCftInterestRateID());
                facilityInterestRate.setRateName(interestRate.getRateName());
                facilityInterestRate.setIsEditable(interestRate.getIsEditable());
                facilityInterestRate.setInterestRatingSubCategory(interestRate.getInterestRatingSubCategory());
                facilityInterestRate.setRateCode(interestRate.getRateCode());
                facilityInterestRate.setValue(interestRate.getValue());
                facilityInterestRate.setIsDefault(interestRate.getIsDefault());
                facilityInterestRate.setStatus(interestRate.getStatus());
            }
        }

        LOG.info("UPDATE facility interest rate completed: {}", facilityDTO);
        return this;
    }

    public FacilityBuilder buildVitalInfo() {
        for (FacilityVitalInfoDataDTO vitalInfoDataDTO : facilityDTO.getFacilityVitalInfoDataDTOList()) {
            boolean isNewRecord = vitalInfoDataDTO.getFacilityVitalInfoDataID() == null;
            FacilityVitalInfoData facilityVitalInfoData = null;
            if (isNewRecord) {
                facilityVitalInfoData = new FacilityVitalInfoData();
                facilityVitalInfoData.setCreatedBy(credentialsDTO.getUserName());
                facilityVitalInfoData.setCreatedDate(date);
                facilityVitalInfoData.setFacility(facility);
                facility.getFacilityVitalInfoData().add(facilityVitalInfoData);
            } else {
                facilityVitalInfoData = facility.getFacilityVitalInfoDataByID(vitalInfoDataDTO.getFacilityVitalInfoDataID());
                facilityVitalInfoData.setCreatedBy(credentialsDTO.getUserName());
                facilityVitalInfoData.setCreatedDate(date);
            }
            facilityVitalInfoData.setCftVitalInfoID(vitalInfoDataDTO.getCftVitalInfoID());
            facilityVitalInfoData.setMandatory(vitalInfoDataDTO.getMandatory());
            facilityVitalInfoData.setDisplayOrder(vitalInfoDataDTO.getDisplayOrder());
            facilityVitalInfoData.setVitalInfoName(vitalInfoDataDTO.getVitalInfoName());
            facilityVitalInfoData.setVitalInfoData(vitalInfoDataDTO.getVitalInfoData());
            facilityVitalInfoData.setStatus(vitalInfoDataDTO.getStatus());
        }

        for (CftVitalInfo cftVitalInfo : facility.getCreditFacilityTemplate().getCftVitalInfos()) {
            if (facility.getFacilityVitalInfoDataByCftVitalInfoID(cftVitalInfo.getCftVitalInfoID()) == null && cftVitalInfo.getStatus() == AppsConstants.Status.ACT) {
                FacilityVitalInfoData facilityVitalInfoData = null;

                facilityVitalInfoData = new FacilityVitalInfoData();
                facilityVitalInfoData.setCreatedBy(credentialsDTO.getUserName());
                facilityVitalInfoData.setCreatedDate(date);
                facilityVitalInfoData.setFacility(facility);
                facility.getFacilityVitalInfoData().add(facilityVitalInfoData);

                facilityVitalInfoData.setCftVitalInfoID(cftVitalInfo.getCftVitalInfoID());
                facilityVitalInfoData.setVitalInfoName(cftVitalInfo.getVitalInfoName());
                facilityVitalInfoData.setDisplayOrder(cftVitalInfo.getDisplayOrder());
                facilityVitalInfoData.setMandatory(cftVitalInfo.getMandatory());
                facilityVitalInfoData.setStatus(cftVitalInfo.getStatus());
            }
        }

        LOG.info("UPDATE facility vital info completed: {}", facilityDTO);
        return this;
    }

    public FacilityBuilder buildCustomInfo(){
        for(FacilityCustomInfoDataDTO customInfoDataDTO : facilityDTO.getFacilityCustomInfoDataDTOList()){
            boolean isNewRecord = customInfoDataDTO.getFacilityCustomInfoDataID() == null;
            FacilityCustomInfoData facilityCustomInfoData = null;
            if(isNewRecord){
                facilityCustomInfoData = new FacilityCustomInfoData();
                facilityCustomInfoData.setCreatedBy(credentialsDTO.getUserName());
                facilityCustomInfoData.setCreatedDate(date);
                facilityCustomInfoData.setFacility(facility);
                facility.getFacilityCustomInfoData().add(facilityCustomInfoData);
            } else {
                facilityCustomInfoData = facility.getFacilityCustomInfoDataByID(customInfoDataDTO.getFacilityCustomInfoDataID());
                facilityCustomInfoData.setCreatedBy(credentialsDTO.getUserName());
                facilityCustomInfoData.setCreatedDate(date);
            }
            facilityCustomInfoData.setCftCustomFacilityInfoID(customInfoDataDTO.getCftCustomFacilityInfoID());
            facilityCustomInfoData.setMandatory(customInfoDataDTO.getMandatory());
            facilityCustomInfoData.setDisplayOrder(customInfoDataDTO.getDisplayOrder());
            facilityCustomInfoData.setCustomFacilityInfoName(customInfoDataDTO.getCustomFacilityInfoName());
            facilityCustomInfoData.setCustomFacilityInfoCode(customInfoDataDTO.getCustomFacilityInfoCode());
            facilityCustomInfoData.setCustomInfoData(customInfoDataDTO.getCustomInfoData());
            facilityCustomInfoData.setStatus(customInfoDataDTO.getStatus());

            LOG.info("UPDATE facility custom info completed 11111: {}", facilityCustomInfoData);
        }

        for(CftCustomFacilityInfo cftCustomFacilityInfo : facility.getCreditFacilityTemplate().getCftCustomFacilityInfos()){
            if(facility.getFacilityCustomInfoDataByCftCustomFacilityInfoID(cftCustomFacilityInfo.getCftCustomFacilityInfoID()) == null && cftCustomFacilityInfo.getStatus() == AppsConstants.Status.ACT) {
                FacilityCustomInfoData facilityCustomInfoData = null;

                facilityCustomInfoData = new FacilityCustomInfoData();
                facilityCustomInfoData.setCreatedBy(credentialsDTO.getDisplayName());
                facilityCustomInfoData.setCreatedDate(date);
                facility.getFacilityCustomInfoData().add(facilityCustomInfoData);

                facilityCustomInfoData.setCftCustomFacilityInfoID(cftCustomFacilityInfo.getCftCustomFacilityInfoID());
                facilityCustomInfoData.setCustomFacilityInfoName(cftCustomFacilityInfo.getCustomFacilityInfoName());
                facilityCustomInfoData.setMandatory(cftCustomFacilityInfo.getMandatory());
                facilityCustomInfoData.setDisplayOrder(cftCustomFacilityInfo.getDisplayOrder());
                facilityCustomInfoData.setStatus(cftCustomFacilityInfo.getStatus());

                LOG.info("UPDATE facility custom info completed 22222: {}", facilityCustomInfoData);
            }
        }
        LOG.info("UPDATE facility custom info completed: {}", facilityDTO);
        return this;
    }

    public FacilityBuilder buildOtherInfo() {
        for (FacilityOtherFacilityInformationDTO otherInfoDataDTO : facilityDTO.getFacilityOtherFacilityInformationDTOList()) {
            boolean isNewRecord = otherInfoDataDTO.getFacilityOtherFacilityInformationID() == null;
            FacilityOtherFacilityInformation facilityOtherInfoData = null;
            if (isNewRecord) {
                facilityOtherInfoData = new FacilityOtherFacilityInformation();
                facilityOtherInfoData.setCreatedBy(credentialsDTO.getUserName());
                facilityOtherInfoData.setCreatedDate(date);
                facility.addOtherFacilityInformation(facilityOtherInfoData);
            } else {
                facilityOtherInfoData = facility.getFacilityOtherInfoDataByID(otherInfoDataDTO.getFacilityOtherFacilityInformationID());
                facilityOtherInfoData.setCreatedBy(credentialsDTO.getUserName());
                facilityOtherInfoData.setCreatedDate(date);
            }
            facilityOtherInfoData.setCftOtherFacilityInfoID(otherInfoDataDTO.getCftOtherFacilityInfoID());
            facilityOtherInfoData.setOtherFacilityInfoName(otherInfoDataDTO.getOtherFacilityInfoName());
            facilityOtherInfoData.setOtherInfoData(otherInfoDataDTO.getOtherInfoData());
            facilityOtherInfoData.setOtherFacilityInfoCode(otherInfoDataDTO.getOtherFacilityInfoCode());
            facilityOtherInfoData.setOtherFacilityInfoFieldType(otherInfoDataDTO.getOtherFacilityInfoFieldType());
            facilityOtherInfoData.setDisplayOrder(otherInfoDataDTO.getDisplayOrder());
            facilityOtherInfoData.setDefaultValue(otherInfoDataDTO.getDefaultValue());
            facilityOtherInfoData.setMandatory(otherInfoDataDTO.getMandatory());
            facilityOtherInfoData.setStatus(otherInfoDataDTO.getStatus());
        }

        for (CftOtherFacilityInformation cftOtherInfo : facility.getCreditFacilityTemplate().getCftOtherFacilityInformations()) {
            if (facility.getFacilityOtherInfoByCftFacilityInformationId(cftOtherInfo.getCftOtherFacilityInfoID()) == null && cftOtherInfo.getStatus() == AppsConstants.Status.ACT) {
                FacilityOtherFacilityInformation facilityOtherInfoData = null;

                facilityOtherInfoData = new FacilityOtherFacilityInformation();
                facilityOtherInfoData.setCreatedBy(credentialsDTO.getUserName());
                facilityOtherInfoData.setCreatedDate(date);
                facility.addOtherFacilityInformation(facilityOtherInfoData);

                facilityOtherInfoData.setCftOtherFacilityInfoID(cftOtherInfo.getCftOtherFacilityInfoID());
                facilityOtherInfoData.setOtherFacilityInfoName(cftOtherInfo.getOtherFacilityInfoName());
                facilityOtherInfoData.setOtherFacilityInfoCode(cftOtherInfo.getOtherFacilityInfoCode());
                facilityOtherInfoData.setOtherFacilityInfoFieldType(cftOtherInfo.getOtherFacilityInfoFieldType());
                facilityOtherInfoData.setDisplayOrder(cftOtherInfo.getDisplayOrder());
                facilityOtherInfoData.setDefaultValue(cftOtherInfo.getDefaultValue());
                facilityOtherInfoData.setMandatory(cftOtherInfo.getMandatory());
                facilityOtherInfoData.setStatus(cftOtherInfo.getStatus());
            }
        }

        LOG.info("UPDATE facility other info completed: {}", facilityDTO);
        return this;
    }

    public FacilityBuilder buildRentalInfo() {
        List<FacilityRentalInformationDTO> facilityRentalInformationDTOS = facilityDTO.getFacilityRentalInformationDTOList().stream().sorted(Comparator.comparing(FacilityRentalInformationDTO::getDisplayOrder)).collect(Collectors.toList());
        for (FacilityRentalInformationDTO rentalInfoDTO : facilityRentalInformationDTOS) {
            boolean isNewRecord = rentalInfoDTO.getFacilityRentalInformationID() == null;
            FacilityRentalInformation facilityRentalInformation;
            if (isNewRecord) {
                facilityRentalInformation = new FacilityRentalInformation();
                facilityRentalInformation.setCreatedBy(credentialsDTO.getUserName());
                facilityRentalInformation.setCreatedDate(date);
                facility.addFacilityRentalInformation(facilityRentalInformation);
            } else {
                facilityRentalInformation = facility.getFacilityRentalInformationByID(rentalInfoDTO.getFacilityRentalInformationID());
                facilityRentalInformation.setCreatedBy(credentialsDTO.getUserName());
                facilityRentalInformation.setCreatedDate(date);
            }
            facilityRentalInformation.setNoOfRentals(rentalInfoDTO.getNoOfRentals());
            facilityRentalInformation.setRentalAmount(rentalInfoDTO.getRentalAmount());
            facilityRentalInformation.setStatus(rentalInfoDTO.getStatus());
            facilityRentalInformation.setDisplayOrder(rentalInfoDTO.getDisplayOrder());
        }

        LOG.info("UPDATE facility rental info completed: {}", facilityDTO);
        return this;
    }

    public void setCreditFacilityTemplateDao(CreditFacilityTemplateDao creditFacilityTemplateDao) {
        this.creditFacilityTemplateDao = creditFacilityTemplateDao;
    }

    public Facility getFacility() {
        return facility;
    }

    public FacilityRepaymentDao getFacilityRepaymentDao() {
        return facilityRepaymentDao;
    }

    public void setFacilityRepaymentDao(FacilityRepaymentDao facilityRepaymentDao) {
        this.facilityRepaymentDao = facilityRepaymentDao;
    }

    public void setSystemParameterService(SystemParameterService systemParameterService) {
        this.systemParameterService = systemParameterService;
    }

    public void setFacilityPaper(FacilityPaper facilityPaper) {
        this.facilityPaper = facilityPaper;
    }

    public void setCalculatorService(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }
}

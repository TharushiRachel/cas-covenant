package com.itechro.cas.model.dto.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants.MasterDataApproveStatus;
import com.itechro.cas.model.domain.casmaster.CftInterestRate;
import com.itechro.cas.model.domain.casmaster.CftOtherFacilityInformation;
import com.itechro.cas.model.domain.casmaster.CftVitalInfo;
import com.itechro.cas.model.domain.casmaster.FacilityCustomInfoData;
import com.itechro.cas.model.domain.facilitypaper.facility.*;
import com.itechro.cas.model.dto.casmaster.ApprovedCreditFacilityTemplateDTO;
import com.itechro.cas.model.dto.casmaster.CreditFacilityTemplateDTO;
import com.itechro.cas.model.dto.casmaster.CreditFacilityTypeDTO;
import com.itechro.cas.model.dto.casmaster.FacilityCustomInfoDataDTO;
import com.itechro.cas.model.dto.facilitypaper.creditcalculator.Formula;
import com.itechro.cas.util.NumberUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ApprovedFacilityDTO implements Serializable {

    private Integer facilityID;

    private String facilityRefCode;

    private Integer creditFacilityTemplateID;

    private ApprovedCreditFacilityTemplateDTO creditFacilityTemplateDTO;

    private Integer facilityPaperID;

    private Integer parentFacilityID;

    private String facilityCurrency;

    private String disbursementAccNumber;

    private BigDecimal facilityAmount;

    private AppsConstants.YesNo isCooperate;

    private BigDecimal outstandingAmount;

    private BigDecimal existingAmount;

    private BigDecimal originalAmount;

    private Integer sectorID;

    private Integer subSectorID;

    private Integer cashFlowGenerationSectorID;

    private String purposeOfAdvance;

    private String purpose;

    private Integer creditFacilityTypeID;

    private CreditFacilityTypeDTO facilityTypeDTO;

    private String facilityType;

    private AppsConstants.YesNo isOneOff;

    private AppsConstants.YesNo directFacility;

    private AppsConstants.YesNo seriesOfLoans;

    private AppsConstants.YesNo revolving;

    private AppsConstants.YesNo reduction;

    private AppsConstants.YesNo enhancement;

    private String repayment;

    private String condition;

    private AppsConstants.YesNo isNew;

    private Integer displayOrder;

    private String remark;

    private AppsConstants.Status status;

    private List<FacilityVitalInfoDataDTO> facilityVitalInfoDataDTOList;

    private List<FacilityOtherFacilityInformationDTO> facilityOtherFacilityInformationDTOList;

    private List<FacilityDocumentDTO> facilityDocumentList;

    private List<FacilityInterestRateDTO> facilityInterestRateList;

    private List<FacilityPurposeOfAdvanceDTO> facilityPurposeOfAdvanceList;

    private List<FacilitySecurityDTO> facilitySecurityDTOList;

    private Integer facilityRepaymentID;

    private FacilityRepaymentDTO facilityRepaymentDTO;

    private BigDecimal facilitySecurityAmount;

    private List<FacilityRentalInformationDTO> facilityRentalInformationDTOList;

    private List<FacilityCustomInfoDataDTO> facilityCustomFacilityInformationDTOList;

    public ApprovedFacilityDTO() {
    }

    public ApprovedFacilityDTO(Facility facility) {
        this(facility, null);
    }

    public ApprovedFacilityDTO(Facility facility, FacilityLoadOptionDTO loadOptionDTO) {
        this(facility, null, null);
    }

    public ApprovedFacilityDTO(Facility facility, FacilityLoadOptionDTO loadOptionDTO, List<Formula> formulaList) {
        this.facilityID = facility.getFacilityID();
        this.facilityRefCode = facility.getFacilityRefCode();
        if (facility.getCreditFacilityTemplate() != null) {
            this.creditFacilityTemplateID = facility.getCreditFacilityTemplate().getCreditFacilityTemplateID();
            this.creditFacilityTemplateDTO = new ApprovedCreditFacilityTemplateDTO(facility.getCreditFacilityTemplate());
            this.facilityType = facility.getCreditFacilityTemplate().getCreditFacilityType().getFacilityTypeName();
        }
        if (facility.getFacilityPaper() != null) {
            this.facilityPaperID = facility.getFacilityPaper().getFacilityPaperID();
        }
        this.parentFacilityID = facility.getParentFacilityID();
        this.facilityCurrency = facility.getFacilityCurrency();
        this.disbursementAccNumber = facility.getDisbursementAccNumber();
        this.facilityAmount = facility.getFacilityAmount();
        this.existingAmount = facility.getExistingAmount();
        this.originalAmount = facility.getOriginalAmount();
        this.isCooperate = facility.getIsCooperate();
        this.outstandingAmount = facility.getOutstandingAmount();
        this.sectorID = facility.getSectorID();
        this.subSectorID = facility.getSubSectorID();
        this.cashFlowGenerationSectorID = facility.getCashFlowGenerationSectorID();
        this.purposeOfAdvance = facility.getPurposeOfAdvance();
        if (facility.getCreditFacilityType() != null) {
            this.creditFacilityTypeID = facility.getCreditFacilityType().getCreditFacilityTypeID();
            this.facilityTypeDTO = new CreditFacilityTypeDTO(facility.getCreditFacilityType());
        }
        if (facility.getFacilityRepayment() != null) {
            this.facilityRepaymentID = facility.getFacilityRepayment().getFacilityRepaymentID();
            this.facilityRepaymentDTO = new FacilityRepaymentDTO(facility.getFacilityRepayment());
        }
        this.isOneOff = facility.getIsOneOff();
        this.directFacility = facility.getDirectFacility();
        this.repayment = facility.getRepayment();
        this.condition = facility.getCondition();
        this.purpose = facility.getPurpose();
        this.isNew = facility.getIsNew();
        this.displayOrder = facility.getDisplayOrder();
        this.seriesOfLoans = facility.getSeriesOfLoans();
        this.revolving = facility.getRevolving();
        this.reduction = facility.getReduction();
        this.enhancement = facility.getEnhancement();
        this.remark = facility.getRemark();
        this.status = facility.getStatus();

        for (FacilityCustomInfoData facilityCustomFacilityInformation : facility.getFacilityCustomFacilityInformations()) {
            this.getFacilityCustomFacilityInformationDTOList().add(new FacilityCustomInfoDataDTO(facilityCustomFacilityInformation));
        }

        if (loadOptionDTO != null) {
            if (loadOptionDTO.isLoadFacilityDocument()) {
                for (FacilityDocument facilityDocument : facility.getFacilityDocuments()) {
                    if (facilityDocument.getStatus() == AppsConstants.Status.ACT) {
                        this.getFacilityDocumentList().add(new FacilityDocumentDTO(facilityDocument, false));
                    }
                }
            }

            if (loadOptionDTO.isLoadFacilityInterestRate()) {
                List<Integer> cftInterestRateIDs = new ArrayList<>();
                for (FacilityInterestRate facilityInterestRate : facility.getOrderedFacilityInterestRates()) {
                    if (facilityInterestRate.getCftInterestRateID() != null) {
                        cftInterestRateIDs.add(facilityInterestRate.getCftInterestRateID());
                    }
                    if (facilityInterestRate.getStatus() == AppsConstants.Status.ACT) {
                        this.getFacilityInterestRateList().add(new FacilityInterestRateDTO(facilityInterestRate));
                    }
                }

                for (CftInterestRate interestRate : facility.getCreditFacilityTemplate().getCftInterestRates()) {
                    if ((interestRate.getApproveStatus() == MasterDataApproveStatus.APPROVED)
                            && !cftInterestRateIDs.contains(interestRate.getCftInterestRateID())) {
                        this.getFacilityInterestRateList().add(new FacilityInterestRateDTO(interestRate, facilityID));
                    }
                }
            }

            if (loadOptionDTO.isLoadFacilityVitalInfo()) {
                List<Integer> cftVitalInfoIDs = new ArrayList<>();
                for (FacilityVitalInfoData facilityVitalInfoData : facility.getFacilityVitalInfoData()) {
                    if (facilityVitalInfoData.getCftVitalInfoID() != null) {
                        cftVitalInfoIDs.add(facilityVitalInfoData.getCftVitalInfoID());
                    }
                    if (facilityVitalInfoData.getStatus() == AppsConstants.Status.ACT) {
                        this.getFacilityVitalInfoDataDTOList().add(new FacilityVitalInfoDataDTO(facilityVitalInfoData));
                    }
                }

                for (CftVitalInfo cftVitalInfo : facility.getCreditFacilityTemplate().getCftVitalInfos()) {
                    if (cftVitalInfo.getStatus() == AppsConstants.Status.ACT
                            && !cftVitalInfoIDs.contains(cftVitalInfo.getCftVitalInfoID())) {
                        this.getFacilityVitalInfoDataDTOList().add(new FacilityVitalInfoDataDTO(cftVitalInfo, facilityID));
                    }
                }
                this.getFacilityVitalInfoDataDTOList().sort(Comparator.comparingInt(FacilityVitalInfoDataDTO::getDisplayOrder));
            }

            if (loadOptionDTO.isLoadFacilityPurposeOfAdvance()) {
                for (FacilityPurposeOfAdvance facilityPurposeOfAdvance : facility.getFacilityPurposeOfAdvances()) {
                    this.getFacilityPurposeOfAdvanceList().add(new FacilityPurposeOfAdvanceDTO(facilityPurposeOfAdvance));
                }
            }

            if (loadOptionDTO.isLoadSecurities()) {
                for (FacilityFacilitySecurity facilityFacilitySecurity : facility.getFacilityFacilitySecurities()) {
                    if (facilityFacilitySecurity.getStatus() == AppsConstants.Status.ACT) {
                        FacilitySecurityDTO facilitySecurityDTO = new FacilitySecurityDTO(facilityFacilitySecurity.getFacilitySecurity());
                        facilitySecurityDTO.setFacilitySecurityAmount(facilityFacilitySecurity.getFacilitySecurityAmount());
                        this.getFacilitySecurityDTOList().add(facilitySecurityDTO);
                    }
                }

                this.getFacilitySecurityDTOList().sort(Comparator.comparing(FacilitySecurityDTO::getFacilitySecurityID));
            }

            if (loadOptionDTO.isLoadOtherFacilityInfo()) {
                if (formulaList == null) {
                    List<Integer> cftOtherFacilityInfoIDs = new ArrayList<>();
                    for (FacilityOtherFacilityInformation facilityOtherFacilityInformation : facility.getOrderedFacilityOtherFacilityInformations()) {
                        if (facilityOtherFacilityInformation.getCftOtherFacilityInfoID() != null) {
                            cftOtherFacilityInfoIDs.add(facilityOtherFacilityInformation.getCftOtherFacilityInfoID());
                        }
                        if (facilityOtherFacilityInformation.getStatus() == AppsConstants.Status.ACT) {
                            this.getFacilityOtherFacilityInformationDTOList().add(new FacilityOtherFacilityInformationDTO(facilityOtherFacilityInformation));
                        }
                    }

                    for (CftOtherFacilityInformation cftOtherFacilityInformation : facility.getCreditFacilityTemplate().getOrderedOtherFacilityInfo()) {
                        if (cftOtherFacilityInformation.getStatus() == AppsConstants.Status.ACT
                                && !cftOtherFacilityInfoIDs.contains(cftOtherFacilityInformation.getCftOtherFacilityInfoID())) {
                            this.getFacilityOtherFacilityInformationDTOList().add(new FacilityOtherFacilityInformationDTO(cftOtherFacilityInformation, facilityID));
                        }
                    }
                } else {
                    for (FacilityOtherFacilityInformation otherFacilityInfo : facility.getOrderedFacilityOtherFacilityInformations()) {
                        List<Formula> filteredFormula = formulaList.stream().filter(f -> f.getCode().equals(otherFacilityInfo.getOtherFacilityInfoCode())).collect(Collectors.toList());
                        String outputCode = filteredFormula.size() > 0 ? filteredFormula.get(0).getOutputCode() : null;
                        boolean currency = filteredFormula.size() > 0 ? filteredFormula.get(0).isCurrency() : false;
                        boolean percentage = filteredFormula.size() > 0 ? filteredFormula.get(0).isPercentage() : false;
                        if (otherFacilityInfo.getStatus() == AppsConstants.Status.ACT) {
                            this.getFacilityOtherFacilityInformationDTOList().add(new FacilityOtherFacilityInformationDTO(otherFacilityInfo, outputCode, currency, percentage));
                        }
                    }
                }
            }

            if (loadOptionDTO.isLoadFacilityRentalInfo()) {
                for (FacilityRentalInformation facilityRentalInformation : facility.getOrderedFacilityRentalInformation()) {
                    if (facilityRentalInformation.getStatus() == AppsConstants.Status.ACT) {
                        this.getFacilityRentalInformationDTOList().add(new FacilityRentalInformationDTO(facilityRentalInformation));
                    }
                }
            }
        }
    }

    public Integer getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(Integer facilityID) {
        this.facilityID = facilityID;
    }

    public String getFacilityRefCode() {
        return facilityRefCode;
    }

    public void setFacilityRefCode(String facilityRefCode) {
        this.facilityRefCode = facilityRefCode;
    }

    public Integer getCreditFacilityTemplateID() {
        return creditFacilityTemplateID;
    }

    public void setCreditFacilityTemplateID(Integer creditFacilityTemplateID) {
        this.creditFacilityTemplateID = creditFacilityTemplateID;
    }

    public ApprovedCreditFacilityTemplateDTO getCreditFacilityTemplateDTO() {
        return creditFacilityTemplateDTO;
    }

    public void setCreditFacilityTemplateDTO(ApprovedCreditFacilityTemplateDTO creditFacilityTemplateDTO) {
        this.creditFacilityTemplateDTO = creditFacilityTemplateDTO;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public Integer getParentFacilityID() {
        return parentFacilityID;
    }

    public void setParentFacilityID(Integer parentFacilityID) {
        this.parentFacilityID = parentFacilityID;
    }

    public String getFacilityCurrency() {
        return facilityCurrency;
    }

    public void setFacilityCurrency(String facilityCurrency) {
        this.facilityCurrency = facilityCurrency;
    }

    public String getDisbursementAccNumber() {
        return disbursementAccNumber;
    }

    public void setDisbursementAccNumber(String disbursementAccNumber) {
        this.disbursementAccNumber = disbursementAccNumber;
    }


    public AppsConstants.YesNo getIsCooperate() {
        return isCooperate;
    }

    public void setIsCooperate(AppsConstants.YesNo isCooperate) {
        this.isCooperate = isCooperate;
    }

    public BigDecimal getFacilityAmount() {
        return facilityAmount;
    }

    public void setFacilityAmount(BigDecimal facilityAmount) {
        this.facilityAmount = facilityAmount;
    }

    public BigDecimal getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(BigDecimal outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public Integer getSectorID() {
        return sectorID;
    }

    public void setSectorID(Integer sectorID) {
        this.sectorID = sectorID;
    }

    public Integer getSubSectorID() {
        return subSectorID;
    }

    public void setSubSectorID(Integer subSectorID) {
        this.subSectorID = subSectorID;
    }

    public Integer getCashFlowGenerationSectorID() {
        return cashFlowGenerationSectorID;
    }

    public void setCashFlowGenerationSectorID(Integer cashFlowGenerationSectorID) {
        this.cashFlowGenerationSectorID = cashFlowGenerationSectorID;
    }

    public String getPurposeOfAdvance() {
        return purposeOfAdvance;
    }

    public void setPurposeOfAdvance(String purposeOfAdvance) {
        this.purposeOfAdvance = purposeOfAdvance;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public CreditFacilityTypeDTO getFacilityTypeDTO() {
        return facilityTypeDTO;
    }

    public void setFacilityTypeDTO(CreditFacilityTypeDTO facilityTypeDTO) {
        this.facilityTypeDTO = facilityTypeDTO;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public AppsConstants.YesNo getIsOneOff() {
        return isOneOff;
    }

    public void setIsOneOff(AppsConstants.YesNo isOneOff) {
        this.isOneOff = isOneOff;
    }


    public AppsConstants.YesNo getDirectFacility() {
        return directFacility;
    }

    public void setDirectFacility(AppsConstants.YesNo directFacility) {
        this.directFacility = directFacility;
    }

    public AppsConstants.YesNo getSeriesOfLoans() {
        return seriesOfLoans;
    }

    public void setSeriesOfLoans(AppsConstants.YesNo seriesOfLoans) {
        this.seriesOfLoans = seriesOfLoans;
    }

    public AppsConstants.YesNo getRevolving() {
        return revolving;
    }

    public void setRevolving(AppsConstants.YesNo revolving) {
        this.revolving = revolving;
    }

    public String getRepayment() {
        return repayment;
    }

    public void setRepayment(String repayment) {
        this.repayment = repayment;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public AppsConstants.YesNo getIsNew() {
        return isNew;
    }

    public void setIsNew(AppsConstants.YesNo isNew) {
        this.isNew = isNew;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public AppsConstants.YesNo getReduction() {
        return reduction;
    }

    public void setReduction(AppsConstants.YesNo reduction) {
        this.reduction = reduction;
    }

    public AppsConstants.YesNo getEnhancement() {
        return enhancement;
    }

    public void setEnhancement(AppsConstants.YesNo enhancement) {
        this.enhancement = enhancement;
    }

    public List<FacilityVitalInfoDataDTO> getFacilityVitalInfoDataDTOList() {
        if (facilityVitalInfoDataDTOList == null) {
            facilityVitalInfoDataDTOList = new ArrayList<>();
        }
        return facilityVitalInfoDataDTOList;
    }

    public void setFacilityVitalInfoDataDTOList(List<FacilityVitalInfoDataDTO> facilityVitalInfoDataDTOList) {
        this.facilityVitalInfoDataDTOList = facilityVitalInfoDataDTOList;
    }

    public List<FacilityDocumentDTO> getFacilityDocumentList() {
        if (facilityDocumentList == null) {
            facilityDocumentList = new ArrayList<>();
        }
        return facilityDocumentList;
    }

    public void setFacilityDocumentList(List<FacilityDocumentDTO> facilityDocumentList) {
        this.facilityDocumentList = facilityDocumentList;
    }

    public List<FacilityInterestRateDTO> getFacilityInterestRateList() {
        if (facilityInterestRateList == null) {
            facilityInterestRateList = new ArrayList<>();
        }
        return facilityInterestRateList;
    }

    public void setFacilityInterestRateList(List<FacilityInterestRateDTO> facilityInterestRateList) {
        this.facilityInterestRateList = facilityInterestRateList;
    }

    public List<FacilityPurposeOfAdvanceDTO> getFacilityPurposeOfAdvanceList() {
        if (facilityPurposeOfAdvanceList == null) {
            facilityPurposeOfAdvanceList = new ArrayList<>();
        }
        return facilityPurposeOfAdvanceList;
    }

    public void setFacilityPurposeOfAdvanceList(List<FacilityPurposeOfAdvanceDTO> facilityPurposeOfAdvanceList) {
        this.facilityPurposeOfAdvanceList = facilityPurposeOfAdvanceList;
    }

    public List<FacilitySecurityDTO> getFacilitySecurityDTOList() {
        if (facilitySecurityDTOList == null) {
            facilitySecurityDTOList = new ArrayList<>();
        }
        return facilitySecurityDTOList;
    }

    public void setFacilitySecurityDTOList(List<FacilitySecurityDTO> facilitySecurityDTOList) {
        this.facilitySecurityDTOList = facilitySecurityDTOList;
    }

    public Integer getCreditFacilityTypeID() {
        return creditFacilityTypeID;
    }

    public void setCreditFacilityTypeID(Integer creditFacilityTypeID) {
        this.creditFacilityTypeID = creditFacilityTypeID;
    }

    public FacilityRepaymentDTO getFacilityRepaymentDTO() {
        return facilityRepaymentDTO;
    }

    public void setFacilityRepaymentDTO(FacilityRepaymentDTO facilityRepaymentDTO) {
        this.facilityRepaymentDTO = facilityRepaymentDTO;
    }

    public Integer getFacilityRepaymentID() {
        return facilityRepaymentID;
    }

    public void setFacilityRepaymentID(Integer facilityRepaymentID) {
        this.facilityRepaymentID = facilityRepaymentID;
    }

    public BigDecimal getFacilityActualAmount() {
        if (getIsNew() == AppsConstants.YesNo.N) {
            return getOutstandingAmount();
        } else {
            return getFacilityAmount();
        }
    }

    public String getFormattedAmount(BigDecimal amount) {
        return NumberUtil.getFormattedAmount(amount);
    }

    public BigDecimal getExistingAmount() {
        return existingAmount;
    }

    public void setExistingAmount(BigDecimal existingAmount) {
        this.existingAmount = existingAmount;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getFacilitySecurityAmount() {
        return facilitySecurityAmount;
    }

    public void setFacilitySecurityAmount(BigDecimal facilitySecurityAmount) {
        this.facilitySecurityAmount = facilitySecurityAmount;
    }

    public List<FacilityOtherFacilityInformationDTO> getFacilityOtherFacilityInformationDTOList() {
        if (facilityOtherFacilityInformationDTOList == null) {
            this.facilityOtherFacilityInformationDTOList = new ArrayList<>();
        }
        return facilityOtherFacilityInformationDTOList;
    }

    public void setFacilityOtherFacilityInformationDTOList(List<FacilityOtherFacilityInformationDTO> facilityOtherFacilityInformationDTOList) {
        this.facilityOtherFacilityInformationDTOList = facilityOtherFacilityInformationDTOList;
    }

    public List<FacilityRentalInformationDTO> getFacilityRentalInformationDTOList() {
        if (facilityRentalInformationDTOList == null) {
            this.facilityRentalInformationDTOList = new ArrayList<>();
        }
        return facilityRentalInformationDTOList;
    }

    public void setFacilityRentalInformationDTOList(List<FacilityRentalInformationDTO> facilityRentalInformationDTOList) {
        this.facilityRentalInformationDTOList = facilityRentalInformationDTOList;
    }

    public List<FacilityCustomInfoDataDTO> getFacilityCustomFacilityInformationDTOList(){
        if(facilityCustomFacilityInformationDTOList == null){
            this.facilityCustomFacilityInformationDTOList = new ArrayList<>();
        }
        return facilityCustomFacilityInformationDTOList;
    }

    public void setFacilityCustomFacilityInformationDTOList(List<FacilityCustomInfoDataDTO> facilityCustomFacilityInformationDTOList){
        this.facilityCustomFacilityInformationDTOList = facilityCustomFacilityInformationDTOList;
    }

    @Override
    public String toString() {
        return "ApprovedFacilityDTO{" +
                "facilityID=" + facilityID +
                ", facilityRefCode='" + facilityRefCode + '\'' +
                ", creditFacilityTemplateID=" + creditFacilityTemplateID +
                ", creditFacilityTemplateDTO=" + creditFacilityTemplateDTO +
                ", facilityPaperID=" + facilityPaperID +
                ", parentFacilityID=" + parentFacilityID +
                ", facilityCurrency='" + facilityCurrency + '\'' +
                ", disbursementAccNumber='" + disbursementAccNumber + '\'' +
                ", facilityAmount=" + facilityAmount +
                ", isCooperate=" + isCooperate +
                ", outstandingAmount=" + outstandingAmount +
                ", existingAmount=" + existingAmount +
                ", originalAmount=" + originalAmount +
                ", sectorID=" + sectorID +
                ", subSectorID=" + subSectorID +
                ", cashFlowGenerationSectorID=" + cashFlowGenerationSectorID +
                ", purposeOfAdvance='" + purposeOfAdvance + '\'' +
                ", purpose='" + purpose + '\'' +
                ", creditFacilityTypeID=" + creditFacilityTypeID +
                ", facilityTypeDTO=" + facilityTypeDTO +
                ", facilityType='" + facilityType + '\'' +
                ", isOneOff=" + isOneOff +
                ", directFacility=" + directFacility +
                ", seriesOfLoans=" + seriesOfLoans +
                ", revolving=" + revolving +
                ", reduction=" + reduction +
                ", enhancement=" + enhancement +
                ", repayment='" + repayment + '\'' +
                ", condition='" + condition + '\'' +
                ", isNew=" + isNew +
                ", displayOrder=" + displayOrder +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", facilityVitalInfoDataDTOList=" + facilityVitalInfoDataDTOList +
                ", facilityOtherFacilityInformationDTOList=" + facilityOtherFacilityInformationDTOList +
                ", facilityDocumentList=" + facilityDocumentList +
                ", facilityInterestRateList=" + facilityInterestRateList +
                ", facilityPurposeOfAdvanceList=" + facilityPurposeOfAdvanceList +
                ", facilitySecurityDTOList=" + facilitySecurityDTOList +
                ", facilityRepaymentID=" + facilityRepaymentID +
                ", facilityRepaymentDTO=" + facilityRepaymentDTO +
                ", facilitySecurityAmount=" + facilitySecurityAmount +
                ", facilityRentalInformationDTOList=" + facilityRentalInformationDTOList +
                ", facilityCustomFacilityInformationDTOList=" + facilityCustomFacilityInformationDTOList +
                '}';
    }
}

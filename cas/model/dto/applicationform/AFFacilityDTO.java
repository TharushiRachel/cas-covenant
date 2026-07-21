package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.applicationform.*;
import com.itechro.cas.model.dto.casmaster.CreditFacilityTemplateDTO;
import com.itechro.cas.model.dto.casmaster.CreditFacilityTypeDTO;
import com.itechro.cas.model.dto.facility.FacilityRepaymentDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AFFacilityDTO implements Serializable {

    private Integer facilityID;

    private String facilityRefCode;

    private Integer creditFacilityTemplateID;

    private CreditFacilityTemplateDTO creditFacilityTemplateDTO;

    private Integer applicationFormID;

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

    private List<AFSecurityDTO> afSecurityDTOList;

    private List<AFFacilitySecurityDTO> afFacilitySecurityDTOList;

    private List<AFFacilityInterestRateDTO> afFacilityInterestRateDTOList;

    private List<AFFacilityVitalInfoDataDTO> afFacilityVitalInfoDataDTOList;

    private List<AFFacilityDocumentDTO> afFacilityDocumentDTOList;

    private Integer facilityRepaymentID;

    private FacilityRepaymentDTO facilityRepaymentDTO;

    private BigDecimal facilitySecurityAmount;

    public AFFacilityDTO() {
    }

    public AFFacilityDTO(AFFacility afFacility) {
        this(afFacility, null);
    }

    public AFFacilityDTO(AFFacility afFacility, AFFacilityLoadOptionDTO loadOptionDTO) {
        this.facilityID = afFacility.getFacilityID();
        this.facilityRefCode = afFacility.getFacilityRefCode();
        if (afFacility.getCreditFacilityTemplate() != null) {
            this.creditFacilityTemplateID = afFacility.getCreditFacilityTemplate().getCreditFacilityTemplateID();
            this.creditFacilityTemplateDTO = new CreditFacilityTemplateDTO(afFacility.getCreditFacilityTemplate());
            this.facilityType = afFacility.getCreditFacilityTemplate().getCreditFacilityType().getFacilityTypeName();
        }
        if (afFacility.getApplicationForm() != null) {
            this.applicationFormID = afFacility.getApplicationForm().getApplicationFormID();
        }
        this.parentFacilityID = afFacility.getParentFacilityID();
        this.facilityCurrency = afFacility.getFacilityCurrency();
        this.disbursementAccNumber = afFacility.getDisbursementAccNumber();
        this.facilityAmount = afFacility.getFacilityAmount();
        this.existingAmount = afFacility.getExistingAmount();
        this.originalAmount = afFacility.getOriginalAmount();
        this.isCooperate = afFacility.getIsCooperate();
        this.outstandingAmount = afFacility.getOutstandingAmount();
        this.sectorID = afFacility.getSectorID();
        this.subSectorID = afFacility.getSubSectorID();
        this.cashFlowGenerationSectorID = afFacility.getCashFlowGenerationSectorID();
        this.purposeOfAdvance = afFacility.getPurposeOfAdvance();
        if (afFacility.getCreditFacilityType() != null) {
            this.creditFacilityTypeID = afFacility.getCreditFacilityType().getCreditFacilityTypeID();
            this.facilityTypeDTO = new CreditFacilityTypeDTO(afFacility.getCreditFacilityType());
        }

        this.isOneOff = afFacility.getIsOneOff();
        this.directFacility = afFacility.getDirectFacility();
        this.repayment = afFacility.getRepayment();
        this.condition = afFacility.getCondition();
        this.purpose = afFacility.getPurpose();
        this.isNew = afFacility.getIsNew();
        this.displayOrder = afFacility.getDisplayOrder();
        this.seriesOfLoans = afFacility.getSeriesOfLoans();
        this.revolving = afFacility.getRevolving();
        this.reduction = afFacility.getReduction();
        this.enhancement = afFacility.getEnhancement();
        this.remark = afFacility.getRemark();
        this.status = afFacility.getStatus();

        if (loadOptionDTO != null) {
            if (loadOptionDTO.isLoadSecurities()) {
                for (AFFacilitySecurity afFacilitySecurity : afFacility.getOrderedSecurities()) {
                    if (afFacilitySecurity.getStatus() == AppsConstants.Status.ACT) {

                        AFFacilitySecurityDTO afFacilitySecurityDTO = new AFFacilitySecurityDTO(afFacilitySecurity);
                        this.getAfFacilitySecurityDTOList().add(afFacilitySecurityDTO);

                        AFSecurityDTO afSecurityDTO = new AFSecurityDTO(afFacilitySecurity.getAfSecurity());
                        this.getAfSecurityDTOList().add(afSecurityDTO);

                    }
                }
            }

            if (loadOptionDTO.isLoadFacilityVitalInfo()) {
                for (AFFacilityVitalInfoData afFacilityVitalInfoData : afFacility.getOrderedAfFacilityVitalInfoData()) {
                    if (afFacilityVitalInfoData.getStatus() == AppsConstants.Status.ACT) {
                        this.getAfFacilityVitalInfoDataDTOList().add(new AFFacilityVitalInfoDataDTO(afFacilityVitalInfoData));
                    }
                }
            }

            if (loadOptionDTO.isLoadFacilityInterestRate()) {
                for (AFFacilityInterestRate afFacilityInterestRate : afFacility.getOrderedAfFacilityInterestRates()) {
                    if (afFacilityInterestRate.getStatus() == AppsConstants.Status.ACT) {
                        this.getAfFacilityInterestRateDTOList().add(new AFFacilityInterestRateDTO(afFacilityInterestRate));
                    }
                }
            }

            if (loadOptionDTO.isLoadAFFacilityDocuments()) {
                for (AFFacilityDocument afFacilityDocument : afFacility.getOrderedAfFacilityDocuments()) {
                    if (afFacilityDocument.getStatus() == AppsConstants.Status.ACT) {
                        this.getAfFacilityDocumentDTOList().add(new AFFacilityDocumentDTO(afFacilityDocument));
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

    public CreditFacilityTemplateDTO getCreditFacilityTemplateDTO() {
        return creditFacilityTemplateDTO;
    }

    public void setCreditFacilityTemplateDTO(CreditFacilityTemplateDTO creditFacilityTemplateDTO) {
        this.creditFacilityTemplateDTO = creditFacilityTemplateDTO;
    }

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
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

    public BigDecimal getFacilityAmount() {
        return facilityAmount;
    }

    public void setFacilityAmount(BigDecimal facilityAmount) {
        this.facilityAmount = facilityAmount;
    }

    public AppsConstants.YesNo getIsCooperate() {
        return isCooperate;
    }

    public void setIsCooperate(AppsConstants.YesNo isCooperate) {
        this.isCooperate = isCooperate;
    }

    public BigDecimal getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(BigDecimal outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
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

    public Integer getCreditFacilityTypeID() {
        return creditFacilityTypeID;
    }

    public void setCreditFacilityTypeID(Integer creditFacilityTypeID) {
        this.creditFacilityTypeID = creditFacilityTypeID;
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

    public List<AFSecurityDTO> getAfSecurityDTOList() {
        if (afSecurityDTOList == null) {
            this.afSecurityDTOList = new ArrayList<>();
        }
        return afSecurityDTOList;
    }

    public void setAfSecurityDTOList(List<AFSecurityDTO> afSecurityDTOList) {
        this.afSecurityDTOList = afSecurityDTOList;
    }

    public List<AFFacilitySecurityDTO> getAfFacilitySecurityDTOList() {
        if (afFacilitySecurityDTOList == null) {
            this.afFacilitySecurityDTOList = new ArrayList<>();
        }
        return afFacilitySecurityDTOList;
    }

    public void setAfFacilitySecurityDTOList(List<AFFacilitySecurityDTO> afFacilitySecurityDTOList) {
        this.afFacilitySecurityDTOList = afFacilitySecurityDTOList;
    }

    public Integer getFacilityRepaymentID() {
        return facilityRepaymentID;
    }

    public void setFacilityRepaymentID(Integer facilityRepaymentID) {
        this.facilityRepaymentID = facilityRepaymentID;
    }

    public FacilityRepaymentDTO getFacilityRepaymentDTO() {
        return facilityRepaymentDTO;
    }

    public void setFacilityRepaymentDTO(FacilityRepaymentDTO facilityRepaymentDTO) {
        this.facilityRepaymentDTO = facilityRepaymentDTO;
    }

    public BigDecimal getFacilitySecurityAmount() {
        return facilitySecurityAmount;
    }

    public void setFacilitySecurityAmount(BigDecimal facilitySecurityAmount) {
        this.facilitySecurityAmount = facilitySecurityAmount;
    }

    public List<AFFacilityInterestRateDTO> getAfFacilityInterestRateDTOList() {
        if (afFacilityInterestRateDTOList == null) {
            this.afFacilityInterestRateDTOList = new ArrayList<>();
        }
        return afFacilityInterestRateDTOList;
    }

    public void setAfFacilityInterestRateDTOList(List<AFFacilityInterestRateDTO> afFacilityInterestRateDTOList) {
        this.afFacilityInterestRateDTOList = afFacilityInterestRateDTOList;
    }

    public List<AFFacilityVitalInfoDataDTO> getAfFacilityVitalInfoDataDTOList() {
        if (afFacilityVitalInfoDataDTOList == null) {
            this.afFacilityVitalInfoDataDTOList = new ArrayList<>();
        }
        return afFacilityVitalInfoDataDTOList;
    }

    public void setAfFacilityVitalInfoDataDTOList(List<AFFacilityVitalInfoDataDTO> afFacilityVitalInfoDataDTOList) {
        this.afFacilityVitalInfoDataDTOList = afFacilityVitalInfoDataDTOList;
    }

    public BigDecimal getFacilityActualAmount() {
        if (getIsNew() == AppsConstants.YesNo.N) {
            return getOutstandingAmount();
        } else {
            return getFacilityAmount();
        }
    }

    public List<AFFacilityDocumentDTO> getAfFacilityDocumentDTOList() {
        if (afFacilityDocumentDTOList == null) {
            this.afFacilityDocumentDTOList = new ArrayList<>();
        }
        return afFacilityDocumentDTOList;
    }

    public void setAfFacilityDocumentDTOList(List<AFFacilityDocumentDTO> afFacilityDocumentDTOList) {
        this.afFacilityDocumentDTOList = afFacilityDocumentDTOList;
    }
}

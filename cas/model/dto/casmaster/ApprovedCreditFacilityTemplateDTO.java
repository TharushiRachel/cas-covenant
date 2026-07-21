package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.*;
import com.itechro.cas.model.domain.facilitypaper.FPDocumentElement;
import com.itechro.cas.model.dto.facilitypaper.FPDocumentElementDTO;
import com.itechro.cas.model.dto.facilitypaper.creditcalculator.Formula;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ApprovedCreditFacilityTemplateDTO implements Serializable {

    private Integer creditFacilityTemplateID;

    private Integer creditFacilityTypeID;

    private String creditFacilityName;

    private String facilityTypeName;

    private String description;

    private BigDecimal maxFacilityAmount;

    private BigDecimal minFacilityAmount;

    private AppsConstants.YesNo showPurpose;

    private AppsConstants.YesNo showRepayment;

    private AppsConstants.YesNo showCondition;

    private AppsConstants.YesNo showRemark;

    private AppsConstants.YesNo showCalculator;

    private AppsConstants.YesNo showRentalData;

    private AppsConstants.Status status;

    private DomainConstants.MasterDataApproveStatus approveStatus;

    private String approvedDateStr;

    private String approvedBy;

    private String createdDateStr;

    private String createdBy;

    private String modifiedDateStr;

    private String modifiedBy;

    private List<CftVitalInfoDTO> cftVitalInfoDTOList;

    private List<CftInterestRateDTO> cftInterestRateDTOList;

    private List<CftSupportingDocDTO> cftSupportingDocDTOList;

    private List<CftOtherFacilityInfoDTO> cftOtherFacilityInfoDTOList;

    private List<FPDocumentElementDTO> fpDocumentElementDTOList;

    private Integer prevCreditFacilityTemplateIDs;

    public ApprovedCreditFacilityTemplateDTO() {
    }

    public ApprovedCreditFacilityTemplateDTO(CreditFacilityTemplate creditFacilityTemplate) {
        this(creditFacilityTemplate, null);
    }

    public ApprovedCreditFacilityTemplateDTO(CreditFacilityTemplate creditFacilityTemplate, List<Formula> formulaList) {
        this.creditFacilityTemplateID = creditFacilityTemplate.getCreditFacilityTemplateID();
        if (creditFacilityTemplate.getCreditFacilityType() != null) {
            this.creditFacilityTypeID = creditFacilityTemplate.getCreditFacilityType().getCreditFacilityTypeID();
            this.facilityTypeName = creditFacilityTemplate.getCreditFacilityType().getFacilityTypeName();
        }
        this.creditFacilityName = creditFacilityTemplate.getCreditFacilityName();
        this.description = creditFacilityTemplate.getDescription();
        this.maxFacilityAmount = creditFacilityTemplate.getMaxFacilityAmount();
        this.minFacilityAmount = creditFacilityTemplate.getMinFacilityAmount();
        this.showCondition = creditFacilityTemplate.getShowCondition();
        this.showPurpose = creditFacilityTemplate.getShowPurpose();
        this.showRemark = creditFacilityTemplate.getShowRemark();
        this.showRepayment = creditFacilityTemplate.getShowRepayment();
        this.showCalculator = creditFacilityTemplate.getShowCalculator();
        this.showRentalData = creditFacilityTemplate.getShowRentalData();
        this.status = creditFacilityTemplate.getStatus();

        if (creditFacilityTemplate.getApprovedDate() != null) {
            this.approvedDateStr = CalendarUtil.getDefaultFormattedDateTime(creditFacilityTemplate.getApprovedDate());
        }
        this.approvedBy = creditFacilityTemplate.getApprovedBy();
        this.approveStatus = creditFacilityTemplate.getApproveStatus();

        if (creditFacilityTemplate.getCreatedDate() != null) {
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(creditFacilityTemplate.getCreatedDate());
        }
        this.createdBy = creditFacilityTemplate.getCreatedBy();
        if (creditFacilityTemplate.getModifiedDate() != null) {
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(creditFacilityTemplate.getModifiedDate());
        }
        this.modifiedBy = creditFacilityTemplate.getModifiedBy();

        for (CftVitalInfo cftVitalInfo : creditFacilityTemplate.getOrderedCftVitalInfos()) {
            if (cftVitalInfo.getStatus() == AppsConstants.Status.ACT) {
                this.getCftVitalInfoDTOList().add(new CftVitalInfoDTO(cftVitalInfo));
            }
            this.getCftVitalInfoDTOList().sort(Comparator.comparingInt(CftVitalInfoDTO::getDisplayOrder));
        }

        for (FPDocumentElement fpDocumentElement : creditFacilityTemplate.getFpDocumentElements()) {
            if (fpDocumentElement.getStatus().contains("ACT")) {
                this.getFpDocumentElementDTOList().add(new FPDocumentElementDTO(fpDocumentElement));
            }
        }

        for (CftInterestRate cftInterestRate : creditFacilityTemplate.getCftInterestRates()) {
            if (cftInterestRate.getStatus() == AppsConstants.Status.ACT) {
                this.getCftInterestRateDTOList().add(new CftInterestRateDTO(cftInterestRate));
            }
        }

        for (CftSupportingDoc cftSupportingDoc : creditFacilityTemplate.getCftSupportingDocs()) {
            if (cftSupportingDoc.getStatus() == AppsConstants.Status.ACT) {
                this.getCftSupportingDocDTOList().add(new CftSupportingDocDTO(cftSupportingDoc));
            }
        }

        if (formulaList == null) {
            for (CftOtherFacilityInformation otherFacilityInfo : creditFacilityTemplate.getOrderedCftOtherFacilityInformations()) {
                if (otherFacilityInfo.getStatus() == AppsConstants.Status.ACT) {
                    this.getCftOtherFacilityInfoDTOList().add(new CftOtherFacilityInfoDTO(otherFacilityInfo));
                }
            }
        } else {
            for (CftOtherFacilityInformation otherFacilityInfo : creditFacilityTemplate.getOrderedCftOtherFacilityInformations()) {
                List<Formula> filteredFormula = formulaList.stream().filter(f -> f.getCode().equals(otherFacilityInfo.getOtherFacilityInfoCode())).collect(Collectors.toList());
                String outputCode = filteredFormula.size() > 0 ? filteredFormula.get(0).getOutputCode() : null;
                boolean currency = filteredFormula.size() > 0 ? filteredFormula.get(0).isCurrency() : false;
                boolean percentage = filteredFormula.size() > 0 ? filteredFormula.get(0).isPercentage() : false;
                if (otherFacilityInfo.getStatus() == AppsConstants.Status.ACT) {
                    this.getCftOtherFacilityInfoDTOList().add(new CftOtherFacilityInfoDTO(otherFacilityInfo, outputCode, currency, percentage));
                }
            }
        }
    }

    public Integer getCreditFacilityTemplateID() {
        return creditFacilityTemplateID;
    }

    public void setCreditFacilityTemplateID(Integer creditFacilityTemplateID) {
        this.creditFacilityTemplateID = creditFacilityTemplateID;
    }

    public Integer getCreditFacilityTypeID() {
        return creditFacilityTypeID;
    }

    public void setCreditFacilityTypeID(Integer creditFacilityTypeID) {
        this.creditFacilityTypeID = creditFacilityTypeID;
    }

    public String getCreditFacilityName() {
        return creditFacilityName;
    }

    public void setCreditFacilityName(String creditFacilityName) {
        this.creditFacilityName = creditFacilityName;
    }

    public String getFacilityTypeName() {
        return facilityTypeName;
    }

    public void setFacilityTypeName(String facilityTypeName) {
        this.facilityTypeName = facilityTypeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMaxFacilityAmount() {
        return maxFacilityAmount;
    }

    public void setMaxFacilityAmount(BigDecimal maxFacilityAmount) {
        this.maxFacilityAmount = maxFacilityAmount;
    }

    public BigDecimal getMinFacilityAmount() {
        return minFacilityAmount;
    }

    public void setMinFacilityAmount(BigDecimal minFacilityAmount) {
        this.minFacilityAmount = minFacilityAmount;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public DomainConstants.MasterDataApproveStatus getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(DomainConstants.MasterDataApproveStatus approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApprovedDateStr() {
        return approvedDateStr;
    }

    public void setApprovedDateStr(String approvedDateStr) {
        this.approvedDateStr = approvedDateStr;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedDateStr() {
        return modifiedDateStr;
    }

    public void setModifiedDateStr(String modifiedDateStr) {
        this.modifiedDateStr = modifiedDateStr;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }


    public List<CftVitalInfoDTO> getCftVitalInfoDTOList() {
        if (cftVitalInfoDTOList == null) {
            cftVitalInfoDTOList = new ArrayList<>();
        }
        return cftVitalInfoDTOList;
    }

    public void setCftVitalInfoDTOList(List<CftVitalInfoDTO> cftVitalInfoDTOList) {
        this.cftVitalInfoDTOList = cftVitalInfoDTOList;
    }


    public List<FPDocumentElementDTO> getFpDocumentElementDTOList() {
        if (fpDocumentElementDTOList == null) {
            fpDocumentElementDTOList = new ArrayList<>();
        }
        return fpDocumentElementDTOList;
    }

    public void setFpDocumentElementDTOList(List<FPDocumentElementDTO> fpDocumentElementDTOList) {
        this.fpDocumentElementDTOList = fpDocumentElementDTOList;
    }

    public List<CftInterestRateDTO> getCftInterestRateDTOList() {
        if (cftInterestRateDTOList == null) {
            cftInterestRateDTOList = new ArrayList<>();
        }
        return cftInterestRateDTOList;
    }

    public void setCftInterestRateDTOList(List<CftInterestRateDTO> cftInterestRateDTOList) {
        this.cftInterestRateDTOList = cftInterestRateDTOList;
    }

    public List<CftSupportingDocDTO> getCftSupportingDocDTOList() {
        if (cftSupportingDocDTOList == null) {
            cftSupportingDocDTOList = new ArrayList<>();
        }
        return cftSupportingDocDTOList;
    }

    public void setCftSupportingDocDTOList(List<CftSupportingDocDTO> cftSupportingDocDTOList) {
        this.cftSupportingDocDTOList = cftSupportingDocDTOList;
    }

    public List<CftOtherFacilityInfoDTO> getCftOtherFacilityInfoDTOList() {
        if (this.cftOtherFacilityInfoDTOList == null) {
            cftOtherFacilityInfoDTOList = new ArrayList<>();
        }
        return cftOtherFacilityInfoDTOList;
    }

    public void setCftOtherFacilityInfoDTOList(List<CftOtherFacilityInfoDTO> cftOtherFacilityInfoDTOList) {
        this.cftOtherFacilityInfoDTOList = cftOtherFacilityInfoDTOList;
    }

    public AppsConstants.YesNo getShowPurpose() {
        return showPurpose;
    }

    public void setShowPurpose(AppsConstants.YesNo showPurpose) {
        this.showPurpose = showPurpose;
    }

    public AppsConstants.YesNo getShowRepayment() {
        return showRepayment;
    }

    public void setShowRepayment(AppsConstants.YesNo showRepayment) {
        this.showRepayment = showRepayment;
    }

    public AppsConstants.YesNo getShowCondition() {
        return showCondition;
    }

    public void setShowCondition(AppsConstants.YesNo showCondition) {
        this.showCondition = showCondition;
    }

    public AppsConstants.YesNo getShowRemark() {
        return showRemark;
    }

    public void setShowRemark(AppsConstants.YesNo showRemark) {
        this.showRemark = showRemark;
    }

    public AppsConstants.YesNo getShowRentalData() {
        return showRentalData;
    }

    public void setShowRentalData(AppsConstants.YesNo showRentalData) {
        this.showRentalData = showRentalData;
    }

    public AppsConstants.YesNo getShowCalculator() {
        return showCalculator;
    }

    public void setShowCalculator(AppsConstants.YesNo showCalculator) {
        this.showCalculator = showCalculator;
    }

    @Override
    public String toString() {
        return "CreditFacilityTemplateDTO{" +
                "creditFacilityTemplateID=" + creditFacilityTemplateID +
                ", creditFacilityTypeID=" + creditFacilityTypeID +
                ", creditFacilityName='" + creditFacilityName + '\'' +
                ", facilityTypeName='" + facilityTypeName + '\'' +
                ", description='" + description + '\'' +
                ", maxFacilityAmount=" + maxFacilityAmount +
                ", minFacilityAmount=" + minFacilityAmount +
                ", showPurpose=" + showPurpose +
                ", showRepayment=" + showRepayment +
                ", showCondition=" + showCondition +
                ", showRemark=" + showRemark +
                ", showCalculator=" + showCalculator +
                ", showRentalData=" + showRentalData +
                ", status=" + status +
                ", approveStatus=" + approveStatus +
                ", approvedDateStr='" + approvedDateStr + '\'' +
                ", approvedBy='" + approvedBy + '\'' +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedDateStr='" + modifiedDateStr + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", cftVitalInfoDTOList=" + cftVitalInfoDTOList +
                ", fpDocumentElementDTOList=" + fpDocumentElementDTOList +
                ", cftInterestRateDTOList=" + cftInterestRateDTOList +
                ", cftSupportingDocDTOList=" + cftSupportingDocDTOList +
                ", cftOtherFacilityInfoDTOList=" + cftOtherFacilityInfoDTOList +
                '}';
    }
}

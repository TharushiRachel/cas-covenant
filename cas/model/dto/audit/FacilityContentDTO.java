package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class FacilityContentDTO extends BaseContentDTO {

    @SerializedName("FACILITY ID")
    private Integer facilityID;

    @SerializedName("FACILITY REFERENCE CODE")
    private String facilityRefCode;

    @SerializedName("CREDIT FACILITY TEMPLATE ID")
    private Integer creditFacilityTemplateID;

    @SerializedName("CREDIT FACILITY NAME")
    private String creditFacilityName;

    @SerializedName("FACILITY PAPER ID")
    private Integer facilityPaperID;

    @SerializedName("FACILITY PAPER NAME")
    private String facilityPaperRefNumber;

    @SerializedName("CREDIT FACILITY TYPE ID")
    private Integer creditFacilityTypeID;

    @SerializedName("FACILITY TYPE")
    private String facilityType;

    @SerializedName("FACILITY CURRENCY")
    private String facilityCurrency;

    @SerializedName("DISBURSEMENT ACC NUM")
    private String disbursementAccNumber;

    @SerializedName("PARENT REC ID")
    private Integer parentFacilityID;

    @SerializedName("FACILITY AMOUNT")
    private String facilityAmount;

    @SerializedName("EXISTING AMOUNT")
    private String existingAmount;

    @SerializedName("ORIGINAL AMOUNT")
    private String originalAmount;

    @SerializedName("IS COOPERATE")
    private String isCooperate;

    @SerializedName("OUTSTANDING AMOUNT")
    private String outstandingAmount;

    @SerializedName("SECTOR ID")
    private Integer sectorID;

    @SerializedName("SUB SECTOR ID")
    private Integer subSectorID;

    @SerializedName("PURPOSE OF ADVANCE")
    private String purposeOfAdvance;

    @SerializedName("PURPOSE")
    private String purpose;

    @SerializedName("IS ONE OFF")
    private String isOneOff;

    @SerializedName("IS DIRECT FACILITY")
    private String directFacility;

    @SerializedName("REPAYMENT")
    private String repayment;

    @SerializedName("CONDITION")
    private String condition;

    @SerializedName("IS NEW")
    private String isNew;

    @SerializedName("IS REDUCTION")
    private String isReduction;

    @SerializedName("IS ENHANCEMENT")
    private String isEnhancement;

    @SerializedName("DISPLAY ORDER")
    private Integer displayOrder;

    @SerializedName("REMARK")
    private String remark;

    @SerializedName("STATUS")
    private String status;

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

    public String getCreditFacilityName() {
        return creditFacilityName;
    }

    public void setCreditFacilityName(String creditFacilityName) {
        this.creditFacilityName = creditFacilityName;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public String getFacilityPaperRefNumber() {
        return facilityPaperRefNumber;
    }

    public void setFacilityPaperRefNumber(String facilityPaperRefNumber) {
        this.facilityPaperRefNumber = facilityPaperRefNumber;
    }

    public Integer getCreditFacilityTypeID() {
        return creditFacilityTypeID;
    }

    public void setCreditFacilityTypeID(Integer creditFacilityTypeID) {
        this.creditFacilityTypeID = creditFacilityTypeID;
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

    public Integer getParentFacilityID() {
        return parentFacilityID;
    }

    public void setParentFacilityID(Integer parentFacilityID) {
        this.parentFacilityID = parentFacilityID;
    }

    public String getFacilityAmount() {
        return facilityAmount;
    }

    public void setFacilityAmount(String facilityAmount) {
        this.facilityAmount = facilityAmount;
    }

    public String getIsCooperate() {
        return isCooperate;
    }

    public void setIsCooperate(String isCooperate) {
        this.isCooperate = isCooperate;
    }

    public String getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(String outstandingAmount) {
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

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getIsOneOff() {
        return isOneOff;
    }

    public void setIsOneOff(String isOneOff) {
        this.isOneOff = isOneOff;
    }

    public String getDirectFacility() {
        return directFacility;
    }

    public void setDirectFacility(String directFacility) {
        this.directFacility = directFacility;
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

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsReduction() {
        return isReduction;
    }

    public void setIsReduction(String isReduction) {
        this.isReduction = isReduction;
    }

    public String getIsEnhancement() {
        return isEnhancement;
    }

    public void setIsEnhancement(String isEnhancement) {
        this.isEnhancement = isEnhancement;
    }

    public String getExistingAmount() {
        return existingAmount;
    }

    public void setExistingAmount(String existingAmount) {
        this.existingAmount = existingAmount;
    }

    public String getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(String originalAmount) {
        this.originalAmount = originalAmount;
    }
}

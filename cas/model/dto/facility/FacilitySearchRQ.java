package com.itechro.cas.model.dto.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.common.PagedParamDTO;

import java.math.BigDecimal;

public class FacilitySearchRQ extends PagedParamDTO {

    private Integer facilityID;

    private String facilityRefCode;

    private Integer creditFacilityTemplateID;

    private Integer facilityPaperID;

    private String facilityCurrency;

    private BigDecimal facilityAmount;

    private AppsConstants.YesNo isCooperate;

    private BigDecimal outstandingAmount;

    private Integer sectorID;

    private Integer subSectorID;

    private String purposeOfAdvance;

    private String purpose;

    private String facilityType;

    private AppsConstants.YesNo isOneOff;

    private String repayment;

    private String condition;

    private AppsConstants.YesNo isNew;

    private Integer displayOrder;

    private String remark;

    private AppsConstants.Status status;

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

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public String getFacilityCurrency() {
        return facilityCurrency;
    }

    public void setFacilityCurrency(String facilityCurrency) {
        this.facilityCurrency = facilityCurrency;
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

    public AppsConstants.YesNo getIsOneOff() {
        return isOneOff;
    }

    public void setIsOneOff(AppsConstants.YesNo isOneOff) {
        this.isOneOff = isOneOff;
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

    @Override
    public String toString() {
        return "FacilityDTO{" +
                "facilityID=" + facilityID +
                ", creditFacilityTemplateID=" + creditFacilityTemplateID +
                ", facilityPaperID=" + facilityPaperID +
                ", facilityCurrency=" + facilityCurrency +
                ", facilityAmount=" + facilityAmount +
                ", isCooperate" + isCooperate +
                ", outstandingAmount" + outstandingAmount +
                ", sectorID" + sectorID +
                ", subSectorID" + subSectorID +
                ", purposeOfAdvance" + purposeOfAdvance +
                ", purpose" + purpose +
                ", facilityType" + facilityType +
                ", isOneOff" + isOneOff +
                ", repayment" + repayment +
                ", condition" + condition +
                ", isNew" + isNew +
                ", displayOrder" + displayOrder +
                ", remark" + remark +
                ", status" + status +
                '}';
    }
}

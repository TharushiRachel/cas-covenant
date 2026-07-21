package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class CreditFacilityTemplateContentDTO extends BaseContentDTO {

    @SerializedName("CREDIT FACILITY TEMPLATE ID")
    private Integer creditFacilityTemplateID;

    @SerializedName("CREDIT FACILITY NAME")
    private String creditFacilityName;

    @SerializedName("CREDIT FACILITY TYPE ID")
    private Integer creditFacilityTypeID;

    @SerializedName("FACILITY TYPE NAME")
    private String facilityTypeName;

    @SerializedName("DESCRIPTION")
    private String description;

    @SerializedName("MAX FACILITY AMOUNT")
    private String maxFacilityAmount;

    @SerializedName("MIN FACILITY AMOUNT")
    private String minFacilityAmount;

    @SerializedName("APPROVE STATUS")
    private String approveStatus;

    @SerializedName("APPROVE DATE")
    private String approvedDateStr;

    @SerializedName("STATUS")
    private String status;

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

    public Integer getCreditFacilityTypeID() {
        return creditFacilityTypeID;
    }

    public void setCreditFacilityTypeID(Integer creditFacilityTypeID) {
        this.creditFacilityTypeID = creditFacilityTypeID;
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

    public String getMaxFacilityAmount() {
        return maxFacilityAmount;
    }

    public void setMaxFacilityAmount(String maxFacilityAmount) {
        this.maxFacilityAmount = maxFacilityAmount;
    }

    public String getMinFacilityAmount() {
        return minFacilityAmount;
    }

    public void setMinFacilityAmount(String minFacilityAmount) {
        this.minFacilityAmount = minFacilityAmount;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApprovedDateStr() {
        return approvedDateStr;
    }

    public void setApprovedDateStr(String approvedDateStr) {
        this.approvedDateStr = approvedDateStr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

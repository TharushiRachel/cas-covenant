package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class CftSupportingDocContentDTO extends BaseContentDTO {

    @SerializedName("CFT SUPPORTING DOC ID")
    private Integer cftSupportingDocID;

    @SerializedName("CREDIT FACILITY TEMPLATE ID")
    private Integer creditFacilityTemplateID;

    @SerializedName("CREDIT FACILITY NAME")
    private String creditFacilityName;

    @SerializedName("SUPPORTING DOC ID")
    private Integer supportingDocID;

    @SerializedName("SUPPORTING DOC NAME")
    private String supportingDocName;

    @SerializedName("MANDATORY")
    private String mandatory;

    @SerializedName("APPROVE STATUS")
    private String approveStatus;

    @SerializedName("STATUS")
    private String status;

    public Integer getCftSupportingDocID() {
        return cftSupportingDocID;
    }

    public void setCftSupportingDocID(Integer cftSupportingDocID) {
        this.cftSupportingDocID = cftSupportingDocID;
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

    public Integer getSupportingDocID() {
        return supportingDocID;
    }

    public void setSupportingDocID(Integer supportingDocID) {
        this.supportingDocID = supportingDocID;
    }

    public String getSupportingDocName() {
        return supportingDocName;
    }

    public void setSupportingDocName(String supportingDocName) {
        this.supportingDocName = supportingDocName;
    }

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

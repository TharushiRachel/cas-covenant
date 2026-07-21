package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class LeadDocContentDTO extends BaseContentDTO{

    @SerializedName("LEAD DOCUMENT ID")
    private Integer leadDocumentID;

    @SerializedName("LEAD ID")
    private Integer leadID;

    @SerializedName("LEAD REFERENCE NUMBER")
    private String leadRefNumber;

    @SerializedName("SUPPORT DOC ID")
    private Integer supportingDocID;

    @SerializedName("SUPPORT DOC NAME")
    private String supportingDocName;

    @SerializedName("REMARK")
    private String remark;

    @SerializedName("STATUS")
    private String status;

    public Integer getLeadDocumentID() {
        return leadDocumentID;
    }

    public void setLeadDocumentID(Integer leadDocumentID) {
        this.leadDocumentID = leadDocumentID;
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

    public Integer getLeadID() {
        return leadID;
    }

    public void setLeadID(Integer leadID) {
        this.leadID = leadID;
    }

    public String getLeadRefNumber() {
        return leadRefNumber;
    }

    public void setLeadRefNumber(String leadRefNumber) {
        this.leadRefNumber = leadRefNumber;
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
}

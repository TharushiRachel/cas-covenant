package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class SupportingDocContentDTO extends BaseContentDTO {

    @SerializedName("SUPPORTING DOC ID")
    private Integer supportingDocID;

    @SerializedName("DOCUMENT NAME")
    private String documentName;

    @SerializedName("DESCRIPTION")
    private String description;

    @SerializedName("SUPPORT DOCUMENT TYPE")
    private String supportDocumentType;

    @SerializedName("APPROVE STATUS")
    private String approveStatus;

    @SerializedName("APPROVE DATE")
    private String approvedDateStr;

    @SerializedName("STATUS")
    private String status;

    public Integer getSupportingDocID() {
        return supportingDocID;
    }

    public void setSupportingDocID(Integer supportingDocID) {
        this.supportingDocID = supportingDocID;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupportDocumentType() {
        return supportDocumentType;
    }

    public void setSupportDocumentType(String supportDocumentType) {
        this.supportDocumentType = supportDocumentType;
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

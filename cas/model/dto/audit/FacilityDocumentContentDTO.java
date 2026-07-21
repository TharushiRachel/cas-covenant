package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class FacilityDocumentContentDTO extends BaseContentDTO{

    @SerializedName("FACILITY DOCUMENT ID")
    private Integer facilityDocumentID;

    @SerializedName("FACILITY ID")
    private Integer facilityID;

    @SerializedName("FACILITY REF CODE")
    private String facilityRefCode;

    @SerializedName("SUPPORT DOC ID")
    private Integer supportingDocID;

    @SerializedName("SUPPORT DOC NAME")
    private String supportingDocName;

    @SerializedName("DOCUMENT STORAGE ID")
    private Integer docStorageID;

    @SerializedName("DOCUMENT NAME")
    private String docStorageFileName;

    @SerializedName("MANDATORY")
    private String mandatory;

    @SerializedName("REMARK")
    private String remark;

    @SerializedName("STATUS")
    private String status;

    public Integer getFacilityDocumentID() {
        return facilityDocumentID;
    }

    public void setFacilityDocumentID(Integer facilityDocumentID) {
        this.facilityDocumentID = facilityDocumentID;
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

    public Integer getDocStorageID() {
        return docStorageID;
    }

    public void setDocStorageID(Integer docStorageID) {
        this.docStorageID = docStorageID;
    }

    public String getDocStorageName() {
        return docStorageFileName;
    }

    public void setDocStorageName(String docStorageName) {
        this.docStorageFileName = docStorageName;
    }

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
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
}

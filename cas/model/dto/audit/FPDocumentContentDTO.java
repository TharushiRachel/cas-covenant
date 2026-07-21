package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class FPDocumentContentDTO extends BaseContentDTO{

    @SerializedName("FP DOCUMENT ID")
    private Integer fpDocumentID;

    @SerializedName("FACILITY PAPER ID")
    private Integer facilityPaperID;

    @SerializedName("FACILITY PAPER REF NUMBER")
    private String fpRefNumber;

    @SerializedName("SUPPORT DOC ID")
    private Integer supportingDocID;

    @SerializedName("SUPPORT DOC NAME")
    private String supportingDocName;

    @SerializedName("DOCUMENT STORAGE ID")
    private Integer docStorageID;

    @SerializedName("DOCUMENT STORAGE NAME")
    private String docStorageName;

    @SerializedName("DESCRIPTION")
    private String description;
    
    @SerializedName("STATUS")
    private String status;

    public Integer getFpDocumentID() {
        return fpDocumentID;
    }

    public void setFpDocumentID(Integer fpDocumentID) {
        this.fpDocumentID = fpDocumentID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
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
        return docStorageName;
    }

    public void setDocStorageName(String docStorageName) {
        this.docStorageName = docStorageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFpRefNumber() {
        return fpRefNumber;
    }

    public void setFpRefNumber(String fpRefNumber) {
        this.fpRefNumber = fpRefNumber;
    }
}

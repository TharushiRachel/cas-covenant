package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class FPCustomerDocContentDTO extends BaseContentDTO {

    @SerializedName("FP CUSTOMER DOC ID")
    private Integer fpCustomerDocID;

    @SerializedName("FP CUSTOMER ID")
    private Integer fpCustomerID;

    @SerializedName("FP CUSTOMER NAME")
    private String fpCustomerName;

    @SerializedName("SUPPORTING DOC ID")
    private Integer supportingDocID;

    @SerializedName("SUPPORT DOC NAME")
    private String supportingDocName;

    @SerializedName("DOCUMENT STORAGE ID")
    private Integer docStorageID;

    @SerializedName("DOCUMENT STORAGE NAME")
    private String docStorageName;

    @SerializedName("DESCRIPTION")
    private String description;

    public Integer getFpCustomerDocID() {
        return fpCustomerDocID;
    }

    public void setFpCustomerDocID(Integer fpCustomerDocID) {
        this.fpCustomerDocID = fpCustomerDocID;
    }

    public Integer getFpCustomerID() {
        return fpCustomerID;
    }

    public void setFpCustomerID(Integer fpCustomerID) {
        this.fpCustomerID = fpCustomerID;
    }

    public String getFpCustomerName() {
        return fpCustomerName;
    }

    public void setFpCustomerName(String fpCustomerName) {
        this.fpCustomerName = fpCustomerName;
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


}

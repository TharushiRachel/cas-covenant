package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;


public class FPCustomerCribDetailContentDTO extends BaseContentDTO {

    @SerializedName("FP CUSTOMER CRIB DETAIL ID")
    private Integer fpCustomerCribDetailID;

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

    @SerializedName("CRIB STATUS")
    private String cribStatus;

    @SerializedName("REMARK")
    private String remark;

    @SerializedName("CRIB ISSUE DATE")
    private String cribIssueDate;

    @SerializedName("STATUS")
    private String status;

    public Integer getFpCustomerCribDetailID() {
        return fpCustomerCribDetailID;
    }

    public void setFpCustomerCribDetailID(Integer fpCustomerCribDetailID) {
        this.fpCustomerCribDetailID = fpCustomerCribDetailID;
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

    public String getCribStatus() {
        return cribStatus;
    }

    public void setCribStatus(String cribStatus) {
        this.cribStatus = cribStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCribIssueDate() {
        return cribIssueDate;
    }

    public void setCribIssueDate(String cribIssueDate) {
        this.cribIssueDate = cribIssueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

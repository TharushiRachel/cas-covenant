package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class FPCustomerContentDTO extends BaseContentDTO {

    @SerializedName("FP CUSTOMER ID")
    private Integer fpCustomerID;

    @SerializedName("FACILITY PAPER ID")
    private Integer facilityPaperID;

    @SerializedName("FACILITY PAPER REF NUMBER")
    private String fpRefNumber;

    @SerializedName("CUSTOMER ID")
    private Integer customerId;

    @SerializedName("CUSTOMER NAME")
    private String customerName;

    @SerializedName("IS PRIMARY")
    private boolean isPrimary;

    @SerializedName("DISPLAY ORDER")
    private Integer displayOrder;

    @SerializedName("STATUS")
    private String status;

    public Integer getFpCustomerID() {
        return fpCustomerID;
    }

    public void setFpCustomerID(Integer fpCustomerID) {
        this.fpCustomerID = fpCustomerID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public String getFpRefNumber() {
        return fpRefNumber;
    }

    public void setFpRefNumber(String fpRefNumber) {
        this.fpRefNumber = fpRefNumber;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

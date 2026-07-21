package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class CustomerIdentificationContentDTO extends BaseContentDTO {

    @SerializedName("IDENTIFICATION ID")
    private Integer identificationID;

    @SerializedName("CUSTOMER ID")
    private Integer customerID;

    @SerializedName("CUSTOMER NAME")
    private String customerName;

    @SerializedName("IDENTIFICATION TYPE")
    private String identificationType;

    @SerializedName("IDENTIFICATION NUMBER")
    private String identificationNumber;

    @SerializedName("STATUS")
    private String status;

    public Integer getIdentificationID() {
        return identificationID;
    }

    public void setIdentificationID(Integer identificationID) {
        this.identificationID = identificationID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

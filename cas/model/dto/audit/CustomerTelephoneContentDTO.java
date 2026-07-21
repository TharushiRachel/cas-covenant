package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class CustomerTelephoneContentDTO extends BaseContentDTO {

    @SerializedName("CUSTOMER TELEPHONE ID")
    private Integer customerTelephoneID;

    @SerializedName("CUSTOMER ID")
    private Integer customerID;

    @SerializedName("CUSTOMER NAME")
    private String customerName;

    @SerializedName("CONTACT NUMBER")
    private String contactNumber;

    @SerializedName("DESCRIPTION")
    private String description;

    @SerializedName("STATUS")
    private String status;

    public Integer getCustomerTelephoneID() {
        return customerTelephoneID;
    }

    public void setCustomerTelephoneID(Integer customerTelephoneID) {
        this.customerTelephoneID = customerTelephoneID;
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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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


}

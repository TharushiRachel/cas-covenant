package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class CustomerAddressContentDTO extends BaseContentDTO {

    @SerializedName("CUSTOMER ADDRESS ID")
    private Integer customerAddressID;

    @SerializedName("CUSTOMER ID")
    private Integer customerID;

    @SerializedName("CUSTOMER NAME")
    private String customerName;

    @SerializedName("ADDRESS TYPE")
    private String addressType;

    @SerializedName("ADDRESS1")
    private String address1;

    @SerializedName("ADDRESS2")
    private String address2;

    @SerializedName("CITY")
    private String city;

    @SerializedName("STATUS")
    private String status;

    public Integer getCustomerAddressID() {
        return customerAddressID;
    }

    public void setCustomerAddressID(Integer customerAddressID) {
        this.customerAddressID = customerAddressID;
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

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

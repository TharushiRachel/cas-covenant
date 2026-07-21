package com.itechro.cas.model.dto.applicationform.applicationFormCustomer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.applicationform.applicationfromcustomer.AFCustomerAddress;

import java.io.Serializable;

public class AFCustomerAddressDTO implements Serializable {

    private Integer afCustomerAddressID;

    private Integer customerAddressID;

    private Integer customerId;

    private Integer afCustomerId;

    private String addressType;

    private String address1;

    private String address2;

    private String city;

    private AppsConstants.Status status;

    public AFCustomerAddressDTO(AFCustomerAddress afCustomerAddress) {
        this.afCustomerAddressID = afCustomerAddress.getAfCustomerAddressID();
        this.customerAddressID = afCustomerAddress.getCustomerAddressID();
        this.customerId = afCustomerAddress.getCustomerID();
        this.afCustomerId = afCustomerAddress.getAfCustomer().getAfCustomerID();
        this.addressType = afCustomerAddress.getAddressType();
        this.address1 = afCustomerAddress.getAddress1();
        this.address2 = afCustomerAddress.getAddress2();
        this.city = afCustomerAddress.getCity();
        this.status = afCustomerAddress.getStatus();
    }

    public AFCustomerAddressDTO() {
    }

    public Integer getAfCustomerAddressID() {
        return afCustomerAddressID;
    }

    public void setAfCustomerAddressID(Integer afCustomerAddressID) {
        this.afCustomerAddressID = afCustomerAddressID;
    }

    public Integer getCustomerAddressID() {
        return customerAddressID;
    }

    public void setCustomerAddressID(Integer customerAddressID) {
        this.customerAddressID = customerAddressID;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getAfCustomerId() {
        return afCustomerId;
    }

    public void setAfCustomerId(Integer afCustomerId) {
        this.afCustomerId = afCustomerId;
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

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}

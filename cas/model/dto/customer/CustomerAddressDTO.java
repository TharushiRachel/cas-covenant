package com.itechro.cas.model.dto.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.customer.CustomerAddress;

import java.io.Serializable;

public class CustomerAddressDTO implements Serializable {

    private Integer customerAddressID;

    private Integer customerId;

    private String addressType;

    private String address1;

    private String address2;

    private String city;

    private AppsConstants.Status status;

    public CustomerAddressDTO(){}

    public CustomerAddressDTO(CustomerAddress customerAddress){

        this.customerAddressID = customerAddress.getCustomerAddressID();
        if(customerAddress.getCustomer() != null) {
            this.customerId = customerAddress.getCustomer().getCustomerID();
        }
        this.addressType = customerAddress.getAddressType();
        this.address1 = customerAddress.getAddress1();
        this.address2 = customerAddress.getAddress2();
        this.status = customerAddress.getStatus();
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

    @Override
    public String toString() {
        return "CustomerAddressDTO{" +
                "customerAddressID=" + customerAddressID +
                ", customerId=" + customerId +
                ", address1='" + address1 +
                ", address2=" + address2 +
                ", city=" + city +
                ", status='" + status +
                '}';
    }
}

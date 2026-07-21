package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.CASCustomerAddress;

import java.io.Serializable;

public class CASCustomerAddressDTO implements Serializable {

    private Integer casCustomerAddressID;

    private Integer casCustomerID;

    private String addressType;

    private String address1;

    private String address2;

    private String city;

    private AppsConstants.Status status;

    public CASCustomerAddressDTO() {
        this.casCustomerAddressID = 0;
        this.casCustomerID = 0;
        this.addressType = "";
        this.address1 = "";
        this.address2 = "";
        this.city = "";
    }

    public CASCustomerAddressDTO(CASCustomerAddress CASCustomerAddress) {
        this.casCustomerAddressID = CASCustomerAddress.getCasCustomerAddressID();
        this.casCustomerID = CASCustomerAddress.getCASCustomer().getCasCustomerID();
        this.addressType = CASCustomerAddress.getAddressType();
        this.address1 = CASCustomerAddress.getAddress1();
        this.address2 = CASCustomerAddress.getAddress2();
        this.city = CASCustomerAddress.getCity();
        this.status = CASCustomerAddress.getStatus();
    }

    public Integer getCasCustomerAddressID() {
        return casCustomerAddressID;
    }

    public void setCasCustomerAddressID(Integer casCustomerAddressID) {
        this.casCustomerAddressID = casCustomerAddressID;
    }

    public Integer getCasCustomerID() {
        return casCustomerID;
    }

    public void setCasCustomerID(Integer casCustomerID) {
        this.casCustomerID = casCustomerID;
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
        return "CASCustomerAddressDTO{" +
                "casCustomerAddressID=" + casCustomerAddressID +
                ", casCustomerID=" + casCustomerID +
                ", addressType='" + addressType + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", status=" + status +
                '}';
    }
}

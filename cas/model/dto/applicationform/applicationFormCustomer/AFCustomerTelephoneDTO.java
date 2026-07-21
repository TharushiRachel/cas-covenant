package com.itechro.cas.model.dto.applicationform.applicationFormCustomer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.applicationform.applicationfromcustomer.AFCustomerTelephone;

import java.io.Serializable;

public class AFCustomerTelephoneDTO implements Serializable {

    private Integer afCustomerTelephoneID;

    private Integer customerTelephoneID;

    private Integer customerID;

    private Integer afCustomerID;

    private String contactNumber;

    private String description;

    private AppsConstants.Status status;

    public AFCustomerTelephoneDTO() {
    }

    public AFCustomerTelephoneDTO(AFCustomerTelephone afCustomerTelephone) {
        this.afCustomerTelephoneID = afCustomerTelephone.getAfCustomerTelephoneID();
        this.afCustomerID = afCustomerTelephone.getAfCustomer().getAfCustomerID();
        this.customerTelephoneID = afCustomerTelephone.getCustomerTelephoneID();
        this.customerID = afCustomerTelephone.getCustomerID();
        this.contactNumber = afCustomerTelephone.getContactNumber();
        this.description = afCustomerTelephone.getDescription();
        this.status = afCustomerTelephone.getStatus();
    }


    public Integer getAfCustomerTelephoneID() {
        return afCustomerTelephoneID;
    }

    public void setAfCustomerTelephoneID(Integer afCustomerTelephoneID) {
        this.afCustomerTelephoneID = afCustomerTelephoneID;
    }

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

    public Integer getAfCustomerID() {
        return afCustomerID;
    }

    public void setAfCustomerID(Integer afCustomerID) {
        this.afCustomerID = afCustomerID;
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

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}

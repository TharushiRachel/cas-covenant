package com.itechro.cas.model.dto.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.customer.CustomerTelephone;

import java.io.Serializable;

public class CustomerTelephoneDTO implements Serializable {

    private Integer customerTelephoneID;

    private Integer customerID;

    private String contactNumber;

    private String description;

    private AppsConstants.Status status;


    public CustomerTelephoneDTO() {
    }

    public CustomerTelephoneDTO(CustomerTelephone customerTelephone) {

        this.customerTelephoneID = customerTelephone.getCustomerTelephoneID();
        if (customerTelephone.getCustomer() != null) {
            this.customerID = customerTelephone.getCustomer().getCustomerID();
        }
        this.contactNumber = customerTelephone.getContactNumber();
        this.description = customerTelephone.getDescription();
        this.status = customerTelephone.getStatus();

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

    @Override
    public String toString() {
        return "CustomerTelephoneDTO{" +
                "customerTelephoneID=" + customerTelephoneID +
                ", customerID=" + customerID +
                ", contactNumber='" + contactNumber + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}

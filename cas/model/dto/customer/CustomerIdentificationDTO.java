package com.itechro.cas.model.dto.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants.CustomerIdentificationType;
import com.itechro.cas.model.domain.customer.CustomerIdentification;

import java.io.Serializable;

public class CustomerIdentificationDTO implements Serializable {

    private Integer identificationID;

    private Integer customerID;

    private CustomerIdentificationType identificationType;

    private String identificationNumber;

    private AppsConstants.Status status;

    public CustomerIdentificationDTO() {
    }

    public CustomerIdentificationDTO(CustomerIdentification customerIdentification) {

        this.identificationID = customerIdentification.getIdentificationID();
        if (customerIdentification.getCustomer() != null) {
            this.customerID = customerIdentification.getCustomer().getCustomerID();
        }
        this.identificationType = customerIdentification.getIdentificationType();
        this.identificationNumber = customerIdentification.getIdentificationNumber();
        this.status = customerIdentification.getStatus();
    }

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

    public CustomerIdentificationType getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(CustomerIdentificationType identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CustomerIdentificationDTO{" +
                "identificationID=" + identificationID +
                ", customerID=" + customerID +
                ", identificationType=" + identificationType +
                ", identificationNumber='" + identificationNumber + '\'' +
                ", status=" + status +
                '}';
    }
}

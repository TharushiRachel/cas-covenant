package com.itechro.cas.model.dto.applicationform.applicationFormCustomer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.applicationform.applicationfromcustomer.AFCustomerIdentification;

import java.io.Serializable;

public class AFCustomerIdentificationDTO implements Serializable {

    private Integer afIdentificationID;

    private Integer afCustomerID;

    private Integer identificationID;

    private Integer customerID;

    private DomainConstants.CustomerIdentificationType identificationType;

    private String identificationNumber;

    private AppsConstants.Status status;

    public AFCustomerIdentificationDTO() {
    }

    public AFCustomerIdentificationDTO(AFCustomerIdentification afCustomerIdentification) {
        this.afIdentificationID = afCustomerIdentification.getAfIdentificationID();
        this.afCustomerID = afCustomerIdentification.getAfCustomer().getAfCustomerID();
        this.identificationID = afCustomerIdentification.getIdentificationID();
        this.customerID = afCustomerIdentification.getCustomerID();
        this.identificationType = afCustomerIdentification.getIdentificationType();
        this.identificationNumber = afCustomerIdentification.getIdentificationNumber();
        this.status = afCustomerIdentification.getStatus();
    }

    public Integer getAfIdentificationID() {
        return afIdentificationID;
    }

    public void setAfIdentificationID(Integer afIdentificationID) {
        this.afIdentificationID = afIdentificationID;
    }

    public Integer getAfCustomerID() {
        return afCustomerID;
    }

    public void setAfCustomerID(Integer afCustomerID) {
        this.afCustomerID = afCustomerID;
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

    public DomainConstants.CustomerIdentificationType getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(DomainConstants.CustomerIdentificationType identificationType) {
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
}

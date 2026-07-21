package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.facilitypaper.CASCustomerIdentification;

import java.io.Serializable;

public class CASCustomerIdentificationDTO implements Serializable {

    private Integer casCustomerIdentificationID;

    private Integer casCustomerID;

    private DomainConstants.CustomerIdentificationType identificationType;

    private String identificationNumber;

    private AppsConstants.Status status;

    public CASCustomerIdentificationDTO() {
        this.identificationNumber = "";
    }

    public CASCustomerIdentificationDTO(CASCustomerIdentification CASCustomerIdentification) {
        this.casCustomerIdentificationID = CASCustomerIdentification.getCasCustomerIdentificationID();
        this.casCustomerID = CASCustomerIdentification.getCASCustomer().getCasCustomerID();
        this.identificationType = CASCustomerIdentification.getIdentificationType();
        this.identificationNumber = CASCustomerIdentification.getIdentificationNumber();
        this.status = CASCustomerIdentification.getStatus();
    }

    public Integer getCasCustomerIdentificationID() {
        return casCustomerIdentificationID;
    }

    public void setCasCustomerIdentificationID(Integer casCustomerIdentificationID) {
        this.casCustomerIdentificationID = casCustomerIdentificationID;
    }

    public Integer getCasCustomerID() {
        return casCustomerID;
    }

    public void setCasCustomerID(Integer casCustomerID) {
        this.casCustomerID = casCustomerID;
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

    @Override
    public String toString() {
        return "CASCustomerIdentificationDTO{" +
                "casCustomerIdentificationID=" + casCustomerIdentificationID +
                ", casCustomerID=" + casCustomerID +
                ", identificationType=" + identificationType +
                ", identificationNumber='" + identificationNumber + '\'' +
                ", status=" + status +
                '}';
    }
}

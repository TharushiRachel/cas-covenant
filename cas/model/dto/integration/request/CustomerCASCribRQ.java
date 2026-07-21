package com.itechro.cas.model.dto.integration.request;

import com.itechro.cas.commons.constants.DomainConstants;

import java.io.Serializable;

public class CustomerCASCribRQ implements Serializable {

    private DomainConstants.CustomerIdentificationType identificationType;

    private String identificationNumber;

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

    @Override
    public String toString() {
        return "CustomerCASCribRQ{" +
                "identificationType=" + identificationType +
                ", identificationNumber='" + identificationNumber + '\'' +
                '}';
    }
}

package com.itechro.cas.model.dto.customer;

import com.itechro.cas.commons.constants.DomainConstants;

import java.io.Serializable;

public class CustomerCribLiabilitiesSearchRQ implements Serializable {

    private DomainConstants.CustomerIdentificationType identificationType;

    private String identificationNumber;

    public CustomerCribLiabilitiesSearchRQ() {
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
}

package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.DomainConstants;

import java.io.Serializable;

public class AfCustomerLiabilityDeactivateRQ implements Serializable {

    private Integer applicationFormID;

    private Integer basicInformationID;

    private Integer afCustomerCribLiabilityID;

    private Integer customerCribLiabilityID;

    private Integer customerCribResponseID;

    private String identificationNumber;

    private DomainConstants.CustomerIdentificationType identificationType;

    private String modifiedUserDisplayName;

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    public Integer getBasicInformationID() {
        return basicInformationID;
    }

    public void setBasicInformationID(Integer basicInformationID) {
        this.basicInformationID = basicInformationID;
    }

    public Integer getAfCustomerCribLiabilityID() {
        return afCustomerCribLiabilityID;
    }

    public void setAfCustomerCribLiabilityID(Integer afCustomerCribLiabilityID) {
        this.afCustomerCribLiabilityID = afCustomerCribLiabilityID;
    }

    public Integer getCustomerCribLiabilityID() {
        return customerCribLiabilityID;
    }

    public void setCustomerCribLiabilityID(Integer customerCribLiabilityID) {
        this.customerCribLiabilityID = customerCribLiabilityID;
    }

    public Integer getCustomerCribResponseID() {
        return customerCribResponseID;
    }

    public void setCustomerCribResponseID(Integer customerCribResponseID) {
        this.customerCribResponseID = customerCribResponseID;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public DomainConstants.CustomerIdentificationType getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(DomainConstants.CustomerIdentificationType identificationType) {
        this.identificationType = identificationType;
    }

    public String getModifiedUserDisplayName() {
        return modifiedUserDisplayName;
    }

    public void setModifiedUserDisplayName(String modifiedUserDisplayName) {
        this.modifiedUserDisplayName = modifiedUserDisplayName;
    }
}

package com.itechro.cas.model.dto.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.customer.CustomerCribLiability;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class CustomerCribLiabilityDTO implements Serializable {

    private Integer customerCribLiabilityID;

    private Integer customerCribResponseID;

    private String identificationNumber;

    private DomainConstants.CustomerIdentificationType identificationType;

    private String originalAmount;

    private String presentOutstanding;

    private String arrears;

    private String signedAs;

    private String securities;

    private String financialInstitution;

    private String modifiedUserDisplayName;

    private String modifiedDateStr;

    private AppsConstants.Status status;

    public CustomerCribLiabilityDTO() {
    }

    public CustomerCribLiabilityDTO(CustomerCribLiability customerCribLiability) {
        this.customerCribLiabilityID = customerCribLiability.getCustomerCribLiabilityID();
        this.customerCribResponseID = customerCribLiability.getCustomerCribResponse().getCustomerCribReportID();
        this.identificationNumber = customerCribLiability.getIdentificationNumber();
        this.identificationType = customerCribLiability.getIdentificationType();
        this.originalAmount = customerCribLiability.getOriginalAmount();
        this.presentOutstanding = customerCribLiability.getPresentOutstanding();
        this.arrears = customerCribLiability.getArrears();
        this.signedAs = customerCribLiability.getSignedAs();
        this.securities = customerCribLiability.getSecurities();
        this.financialInstitution = customerCribLiability.getFinancialInstitution();
        this.status = customerCribLiability.getStatus();
        if (customerCribLiability.getModifiedDate() != null && customerCribLiability.getModifiedUserDisplayName() != null) {
            this.modifiedUserDisplayName = customerCribLiability.getModifiedUserDisplayName();
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(customerCribLiability.getModifiedDate());
        }
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

    public String getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(String originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getPresentOutstanding() {
        return presentOutstanding;
    }

    public void setPresentOutstanding(String presentOutstanding) {
        this.presentOutstanding = presentOutstanding;
    }

    public String getArrears() {
        return arrears;
    }

    public void setArrears(String arrears) {
        this.arrears = arrears;
    }

    public String getSignedAs() {
        return signedAs;
    }

    public void setSignedAs(String signedAs) {
        this.signedAs = signedAs;
    }

    public String getSecurities() {
        return securities;
    }

    public void setSecurities(String securities) {
        this.securities = securities;
    }

    public String getFinancialInstitution() {
        return financialInstitution;
    }

    public void setFinancialInstitution(String financialInstitution) {
        this.financialInstitution = financialInstitution;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public String getModifiedUserDisplayName() {
        return modifiedUserDisplayName;
    }

    public void setModifiedUserDisplayName(String modifiedUserDisplayName) {
        this.modifiedUserDisplayName = modifiedUserDisplayName;
    }

    public String getModifiedDateStr() {
        return modifiedDateStr;
    }

    public void setModifiedDateStr(String modifiedDateStr) {
        this.modifiedDateStr = modifiedDateStr;
    }
}

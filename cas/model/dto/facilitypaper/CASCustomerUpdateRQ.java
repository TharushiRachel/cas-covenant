package com.itechro.cas.model.dto.facilitypaper;

import java.io.Serializable;

public class CASCustomerUpdateRQ implements Serializable {

    private Integer facilityPaperID;

    private Integer casCustomerID;

    private String customerFinancialID;

    private String identificationType;

    private String identificationNumber;

    private String bankAccountNumber;

    public CASCustomerUpdateRQ() {
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public Integer getCasCustomerID() {
        return casCustomerID;
    }

    public void setCasCustomerID(Integer casCustomerID) {
        this.casCustomerID = casCustomerID;
    }

    public String getCustomerFinancialID() {
        return customerFinancialID;
    }

    public void setCustomerFinancialID(String customerFinancialID) {
        this.customerFinancialID = customerFinancialID;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    @Override
    public String toString() {
        return "CASCustomerUpdateRQ{" +
                "facilityPaperID=" + facilityPaperID +
                ", casCustomerID=" + casCustomerID +
                ", customerFinancialID='" + customerFinancialID + '\'' +
                ", identificationType='" + identificationType + '\'' +
                ", identificationNumber='" + identificationNumber + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                '}';
    }
}

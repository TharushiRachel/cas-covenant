package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class Customer360SearchContentDTO extends BaseContentDTO {

    @SerializedName("CUSTOMER FINANCIAL ID")
    private String customerFinancialID;

    @SerializedName("IDENTIFICATION TYPE")
    private String identificationType;

    @SerializedName("IDENTIFICATION NUMBER")
    private String identificationNumber;

    @SerializedName("BANK ACCOUNT NUMBER")
    private String bankAccountNumber;

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
}

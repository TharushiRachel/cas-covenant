package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class CustomerBankDetailsContentDTO extends BaseContentDTO {

    @SerializedName("CUSTOMER BANK DETAILS ID")
    private Integer customerBankDetailsID;

    @SerializedName("CUSTOMER ID")
    private Integer customerID;

    @SerializedName("CUSTOMER NAME")
    private String customerName;

    @SerializedName("BANK ACCOUNT NUMBER")
    private String bankAccountNumber;

    @SerializedName("BANK ACCOUNT TYPE")
    private String bankAccountType;

    @SerializedName("BRANCH CODE")
    private String branchCode;

    @SerializedName("ACCOUNT CLS FLAG")
    private String accountCLSFlag;

    @SerializedName("ACCOUNT SINCE")
    private String accSince;

    @SerializedName("SCHM CODE")
    private String schmCode;

    @SerializedName("SCHM TYPE")
    private String schmType;

    @SerializedName("ACCOUNT CURRENCY CODE")
    private String accountCurrencyCode;

    @SerializedName("ACCOUNT STATUS")
    private String accountStatus;

    @SerializedName("STATUS")
    private String status;

    public Integer getCustomerBankDetailsID() {
        return customerBankDetailsID;
    }

    public void setCustomerBankDetailsID(Integer customerBankDetailsID) {
        this.customerBankDetailsID = customerBankDetailsID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankAccountType() {
        return bankAccountType;
    }

    public void setBankAccountType(String bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getAccountCLSFlag() {
        return accountCLSFlag;
    }

    public void setAccountCLSFlag(String accountCLSFlag) {
        this.accountCLSFlag = accountCLSFlag;
    }

    public String getAccSince() {
        return accSince;
    }

    public void setAccSince(String accSince) {
        this.accSince = accSince;
    }

    public String getSchmCode() {
        return schmCode;
    }

    public void setSchmCode(String schmCode) {
        this.schmCode = schmCode;
    }

    public String getSchmType() {
        return schmType;
    }

    public void setSchmType(String schmType) {
        this.schmType = schmType;
    }

    public String getAccountCurrencyCode() {
        return accountCurrencyCode;
    }

    public void setAccountCurrencyCode(String accountCurrencyCode) {
        this.accountCurrencyCode = accountCurrencyCode;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

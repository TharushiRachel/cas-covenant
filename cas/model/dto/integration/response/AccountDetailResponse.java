package com.itechro.cas.model.dto.integration.response;

import com.itechro.cas.model.dto.integration.ws.response.AccountDetailWSRS;

import java.io.Serializable;

public class AccountDetailResponse implements Serializable {

    private String customerID;

    private String customerName;

    private String nationalIDCardNumber;

    private String dateOfBirth;

    private String accountCLSFlag;

    private String schmCode;

    private String schmType;

    private String accountName;

    private String accountCurrencyCode;

    private String solID;

    private String emailAddress;

    private String customerAddress;

    private String accountStatus;

    private String accountMGRSolID;

    private boolean success;

    public AccountDetailResponse() {
    }

    public AccountDetailResponse(AccountDetailWSRS detailLoadRS) {
        if (detailLoadRS != null) {
            this.customerID = detailLoadRS.getCustomerID();
            this.customerName = detailLoadRS.getCustomerName();
            this.nationalIDCardNumber = detailLoadRS.getNationalIDCardNumber();
            this.dateOfBirth = detailLoadRS.getDateOfBirth();
            this.accountCLSFlag = detailLoadRS.getAccountCLSFlag();
            this.schmCode = detailLoadRS.getSchmCode();
            this.schmType = detailLoadRS.getSchmType();
            this.accountName = detailLoadRS.getAccountName();
            this.accountCurrencyCode = detailLoadRS.getAccountCurrencyCode();
            this.solID = detailLoadRS.getSolID();
            this.emailAddress = detailLoadRS.getEmailAddress();
            this.customerAddress = detailLoadRS.getCustomerAddress();
            this.accountStatus = detailLoadRS.getAccountStatus();
            this.accountMGRSolID = detailLoadRS.getAccountMGRSolID();
        }
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getNationalIDCardNumber() {
        return nationalIDCardNumber;
    }

    public void setNationalIDCardNumber(String nationalIDCardNumber) {
        this.nationalIDCardNumber = nationalIDCardNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAccountCLSFlag() {
        return accountCLSFlag;
    }

    public void setAccountCLSFlag(String accountCLSFlag) {
        this.accountCLSFlag = accountCLSFlag;
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

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountCurrencyCode() {
        return accountCurrencyCode;
    }

    public void setAccountCurrencyCode(String accountCurrencyCode) {
        this.accountCurrencyCode = accountCurrencyCode;
    }

    public String getSolID() {
        return solID;
    }

    public void setSolID(String solID) {
        this.solID = solID;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountMGRSolID() {
        return accountMGRSolID;
    }

    public void setAccountMGRSolID(String accountMGRSolID) {
        this.accountMGRSolID = accountMGRSolID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

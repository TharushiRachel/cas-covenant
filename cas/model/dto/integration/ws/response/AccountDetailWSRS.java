package com.itechro.cas.model.dto.integration.ws.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "CUST_ID",
        "CUST_NAME",
        "NAT_ID_CARD_NUM",
        "DATE_OF_BIRTH",
        "ACCT_CLS_FLG",
        "SCHM_CODE",
        "SCHM_TYPE",
        "ACCT_NAME",
        "ACCT_CRNCY_CODE",
        "SOL_ID",
        "EMAIL_ADDR",
        "CUST_ADDR",
        "ACC_STATUS",
        "ACC_MGR_SOL_ID"
})
public class AccountDetailWSRS implements Serializable {

    @JsonProperty("CUST_ID")
    private String customerID;

    @JsonProperty("APPLICATION_SECURITY_CLASS")
    private String customerName;

    @JsonProperty("NAT_ID_CARD_NUM")
    private String nationalIDCardNumber;

    @JsonProperty("DATE_OF_BIRTH")
    private String dateOfBirth;

    @JsonProperty("ACCT_CLS_FLG")
    private String accountCLSFlag;

    @JsonProperty("SCHM_CODE")
    private String schmCode;

    @JsonProperty("SCHM_TYPE")
    private String schmType;

    @JsonProperty("ACCT_NAME")
    private String accountName;

    @JsonProperty("ACCT_CRNCY_CODE")
    private String accountCurrencyCode;

    @JsonProperty("SOL_ID")
    private String solID;

    @JsonProperty("EMAIL_ADDR")
    private String emailAddress;

    @JsonProperty("CUST_ADDR")
    private String customerAddress;

    @JsonProperty("ACC_STATUS")
    private String accountStatus;

    @JsonProperty("ACC_MGR_SOL_ID")
    private String accountMGRSolID;

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

    @Override
    public String toString() {
        return "AccountDetailWSRS{" +
                "customerID='" + customerID + '\'' +
                ", customerName='" + customerName + '\'' +
                ", nationalIDCardNumber=" + nationalIDCardNumber +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", accountCLSFlag='" + accountCLSFlag + '\'' +
                ", schmCode='" + schmCode + '\'' +
                ", schmType='" + schmType + '\'' +
                ", accountName='" + accountName + '\'' +
                ", accountCurrencyCode='" + accountCurrencyCode + '\'' +
                ", solID='" + solID + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", accountStatus='" + accountStatus + '\'' +
                ", accountMGRSolID='" + accountMGRSolID + '\'' +
                '}';
    }
}

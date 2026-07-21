package com.itechro.cas.model.dto.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.CASCustomerBankDetail;

import java.io.Serializable;

public class CASCustomerBankDetailsDTO implements Serializable {

    private Integer casCustomerBankDetailsID;

    private Integer casCustomerID;

    private String bankAccountNumber;

    private String bankAccountType;

    private String branchCode;

    private AppsConstants.Status status;

    private String accountCLSFlag;

    private String accSince;

    private String schmCode;

    private String schmType;

    private String accountCurrencyCode;

    private String accountStatus;

    public CASCustomerBankDetailsDTO() {
    }

    public CASCustomerBankDetailsDTO(CASCustomerBankDetail CASCustomerBankDetail) {
        this.casCustomerBankDetailsID = CASCustomerBankDetail.getCasCustomerBankDetailsID();
        this.casCustomerID = CASCustomerBankDetail.getCASCustomer().getCasCustomerID();
        this.bankAccountNumber = CASCustomerBankDetail.getBankAccountNumber();
        this.bankAccountType = CASCustomerBankDetail.getBankAccountType();
        this.branchCode = CASCustomerBankDetail.getBranchCode();
        this.status = CASCustomerBankDetail.getStatus();
        this.accountCLSFlag = CASCustomerBankDetail.getAccountCLSFlag();
        this.accSince = CASCustomerBankDetail.getAccSince();
        this.schmCode = CASCustomerBankDetail.getSchmCode();
        this.schmType = CASCustomerBankDetail.getSchmType();
        this.accountCurrencyCode = CASCustomerBankDetail.getAccountCurrencyCode();
        this.accountStatus = CASCustomerBankDetail.getAccountStatus();
    }

    public Integer getCasCustomerBankDetailsID() {
        return casCustomerBankDetailsID;
    }

    public void setCasCustomerBankDetailsID(Integer casCustomerBankDetailsID) {
        this.casCustomerBankDetailsID = casCustomerBankDetailsID;
    }

    public Integer getCasCustomerID() {
        return casCustomerID;
    }

    public void setCasCustomerID(Integer casCustomerID) {
        this.casCustomerID = casCustomerID;
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

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "CASCustomerBankDetailsDTO{" +
                "casCustomerBankDetailsID=" + casCustomerBankDetailsID +
                ", casCustomerID=" + casCustomerID +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", bankAccountType='" + bankAccountType + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", status=" + status +
                ", accountCLSFlag='" + accountCLSFlag + '\'' +
                ", accSince='" + accSince + '\'' +
                ", schmCode='" + schmCode + '\'' +
                ", schmType='" + schmType + '\'' +
                ", accountCurrencyCode='" + accountCurrencyCode + '\'' +
                ", accountStatus='" + accountStatus + '\'' +
                '}';
    }
}

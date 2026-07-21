package com.itechro.cas.model.dto.applicationform.applicationFormCustomer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.applicationform.applicationfromcustomer.AFCustomerBankDetail;

import java.io.Serializable;

public class AFCustomerBankDetailsDTO implements Serializable {

    private Integer customerBankDetailsID;

    private Integer afCustomerBankDetailsID;

    private Integer customerID;

    private Integer afCustomerID;

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

    public AFCustomerBankDetailsDTO() {
    }

    public AFCustomerBankDetailsDTO(AFCustomerBankDetail afCustomerBankDetail) {
        this.afCustomerBankDetailsID = afCustomerBankDetail.getAfCustomerBankDetailsID();
        this.afCustomerID = afCustomerBankDetail.getAfCustomer().getAfCustomerID();
        this.customerID = afCustomerBankDetail.getCustomerID();
        this.customerBankDetailsID = afCustomerBankDetail.getCustomerBankDetailsID();
        this.bankAccountNumber = afCustomerBankDetail.getBankAccountNumber();
        this.bankAccountType = afCustomerBankDetail.getBankAccountType();
        this.branchCode = afCustomerBankDetail.getBranchCode();
        this.status = afCustomerBankDetail.getStatus();
        this.accountCLSFlag = afCustomerBankDetail.getAccountCLSFlag();
        this.accSince = afCustomerBankDetail.getAccSince();
        this.schmCode = afCustomerBankDetail.getSchmCode();
        this.schmType = afCustomerBankDetail.getSchmType();
        this.accountCurrencyCode = afCustomerBankDetail.getAccountCurrencyCode();
        this.accountStatus = afCustomerBankDetail.getAccountStatus();
    }

    public Integer getCustomerBankDetailsID() {
        return customerBankDetailsID;
    }

    public void setCustomerBankDetailsID(Integer customerBankDetailsID) {
        this.customerBankDetailsID = customerBankDetailsID;
    }

    public Integer getAfCustomerBankDetailsID() {
        return afCustomerBankDetailsID;
    }

    public void setAfCustomerBankDetailsID(Integer afCustomerBankDetailsID) {
        this.afCustomerBankDetailsID = afCustomerBankDetailsID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Integer getAfCustomerID() {
        return afCustomerID;
    }

    public void setAfCustomerID(Integer afCustomerID) {
        this.afCustomerID = afCustomerID;
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
}

package com.itechro.cas.model.dto.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.customer.CusBankAccJoiningPartner;
import com.itechro.cas.model.domain.customer.CustomerBankDetail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomerBankDetailsDTO implements Serializable {

    private Integer customerBankDetailsID;

    private Integer customerID;

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

    private List<CusBankAccJoiningPartnerDto> joiningPartnerList;

    public CustomerBankDetailsDTO() {
    }

    public CustomerBankDetailsDTO(CustomerBankDetail customerBankDetail) {
        this.customerBankDetailsID = customerBankDetail.getCustomerBankDetailsID();
        if (customerBankDetail.getCustomer() != null) {
            this.customerID = customerBankDetail.getCustomer().getCustomerID();
        }
        this.bankAccountNumber = customerBankDetail.getBankAccountNumber();
        this.bankAccountType = customerBankDetail.getBankAccountType();
        this.branchCode = customerBankDetail.getBranchCode();
        this.accountCLSFlag = customerBankDetail.getAccountCLSFlag();
        this.accSince = customerBankDetail.getAccSince();
        this.schmCode = customerBankDetail.getSchmCode();
        this.schmType = customerBankDetail.getSchmType();
        this.accountCurrencyCode = customerBankDetail.getAccountCurrencyCode();
        this.accountStatus = customerBankDetail.getAccountStatus();
        this.status = customerBankDetail.getStatus();

        for (CusBankAccJoiningPartner joiningPartner : customerBankDetail.getCusBankAccJoiningPartnerList()) {
            this.getJoiningPartnerList().add(new CusBankAccJoiningPartnerDto(joiningPartner));
        }

    }

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

    public List<CusBankAccJoiningPartnerDto> getJoiningPartnerList() {
        if (joiningPartnerList == null) {
            joiningPartnerList = new ArrayList<>();
        }
        return joiningPartnerList;
    }

    public void setJoiningPartnerList(List<CusBankAccJoiningPartnerDto> joiningPartnerList) {
        this.joiningPartnerList = joiningPartnerList;
    }

    @Override
    public String toString() {
        return "CustomerBankDetailsDTO{" +
                "customerBankDetailsID=" + customerBankDetailsID +
                ", customerID=" + customerID +
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
                ", joiningPartnerList=" + joiningPartnerList +
                '}';
    }
}

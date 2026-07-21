package com.itechro.cas.model.domain.applicationform.applicationfromcustomer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_AF_CUSTOMER_BANK_DETAILS")
public class AFCustomerBankDetail extends UserTrackableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_CUSTOMER_BANK_DETAILS")
    @SequenceGenerator(name = "SEQ_T_AF_CUSTOMER_BANK_DETAILS", sequenceName = "SEQ_T_AF_CUSTOMER_BANK_DETAILS", allocationSize = 1)
    @Column(name = "AF_CUSTOMER_BANK_DETAILS_ID")
    private Integer afCustomerBankDetailsID;

    @Column(name = "CUSTOMER_BANK_DETAILS_ID")
    private Integer customerBankDetailsID;

    @Column(name = "CUSTOMER_ID")
    private Integer customerID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AF_CUSTOMER_ID")
    private AFCustomer afCustomer;

    @Column(name = "BANK_ACCOUNT_NUMBER")
    private String bankAccountNumber;

    @Column(name = "BANK_ACCOUNT_TYPE")
    private String bankAccountType;

    @Column(name = "BRANCH_CODE")
    private String branchCode;

    @Column(name = "ACCOUNT_CLS_FLAG")
    private String accountCLSFlag;

    @Column(name = "ACCOUNT_SINCE")
    private String accSince;

    @Column(name = "SCHM_CODE")
    private String schmCode;

    @Column(name = "SCHM_TYPE")
    private String schmType;

    @Column(name = "ACCOUNT_CURRENCY_CODE")
    private String accountCurrencyCode;

    @Column(name = "ACCOUNT_STATUS")
    private String accountStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getAfCustomerBankDetailsID() {
        return afCustomerBankDetailsID;
    }

    public void setAfCustomerBankDetailsID(Integer afCustomerBankDetailsID) {
        this.afCustomerBankDetailsID = afCustomerBankDetailsID;
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

    public AFCustomer getAfCustomer() {
        return afCustomer;
    }

    public void setAfCustomer(AFCustomer afCustomer) {
        this.afCustomer = afCustomer;
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

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}

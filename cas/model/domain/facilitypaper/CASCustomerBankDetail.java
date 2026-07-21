package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_CAS_CUSTOMER_BANK_DETAILS")
public class CASCustomerBankDetail extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CAS_CUS_BANK_DETAILS")
    @SequenceGenerator(name = "SEQ_T_CAS_CUS_BANK_DETAILS", sequenceName = "SEQ_T_CAS_CUS_BANK_DETAILS", allocationSize = 1)
    @Column(name = "CAS_CUSTOMER_BANK_DETAILS_ID")
    private Integer casCustomerBankDetailsID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAS_CUSTOMER_ID")
    private CASCustomer CASCustomer;

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

    public Integer getCasCustomerBankDetailsID() {
        return casCustomerBankDetailsID;
    }

    public void setCasCustomerBankDetailsID(Integer casCustomerBankDetailsID) {
        this.casCustomerBankDetailsID = casCustomerBankDetailsID;
    }

    public CASCustomer getCASCustomer() {
        return CASCustomer;
    }

    public void setCASCustomer(CASCustomer CASCustomer) {
        this.CASCustomer = CASCustomer;
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

package com.itechro.cas.model.domain.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.dto.customer.CusBankAccJoiningPartnerDto;
import com.itechro.cas.model.dto.customer.CustomerBankDetailsDTO;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "T_CUSTOMER_BANK_DETAILS")
public class CustomerBankDetail extends UserTrackableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CUSTOMER_BANK_DETAILS")
    @SequenceGenerator(name = "SEQ_T_CUSTOMER_BANK_DETAILS", sequenceName = "SEQ_T_CUSTOMER_BANK_DETAILS", allocationSize = 1)
    @Column(name = "CUSTOMER_BANK_DETAILS_ID")
    private Integer customerBankDetailsID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "customerBankDetail")
    private List<CusBankAccJoiningPartner> cusBankAccJoiningPartnerList;

    public Integer getCustomerBankDetailsID() {
        return customerBankDetailsID;
    }

    public void setCustomerBankDetailsID(Integer customerBankDetailsID) {
        this.customerBankDetailsID = customerBankDetailsID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public List<CusBankAccJoiningPartner> getCusBankAccJoiningPartnerList() {
        if (cusBankAccJoiningPartnerList == null) {
            cusBankAccJoiningPartnerList = new ArrayList<>();
        }
        return cusBankAccJoiningPartnerList;
    }

    public void setCusBankAccJoiningPartnerList(List<CusBankAccJoiningPartner> cusBankAccJoiningPartnerList) {
        this.cusBankAccJoiningPartnerList = cusBankAccJoiningPartnerList;
    }

    public void addCusBankAccJoiningPartner(CusBankAccJoiningPartner joiningPartner) {
        joiningPartner.setCustomerBankDetail(this);
        this.getCusBankAccJoiningPartnerList().add(joiningPartner);
    }


    public CusBankAccJoiningPartner getCusBankAccJoiningPartnerByID(Integer joiningPartnerID) {
        return this.getCusBankAccJoiningPartnerList().stream().
                filter(partner -> {
                    return joiningPartnerID.equals(partner.getCusBankAccJoiningPartnerID());
                }).findFirst().orElse(null);
    }

    public void removeCusBankAccJoiningPartner(CusBankAccJoiningPartner cusBankAccJoiningPartner) {
        if (cusBankAccJoiningPartner != null) {
            this.getCusBankAccJoiningPartnerList().remove(cusBankAccJoiningPartner);
        }
    }

    public boolean equalsDTO(CustomerBankDetailsDTO bankDetailsDTO) {
        if (bankDetailsDTO == null) {
            return false;
        }

        if (StringUtils.isNotEmpty(bankDetailsDTO.getBankAccountNumber()) && StringUtils.isNotEmpty(bankDetailsDTO.getAccountStatus()) && StringUtils.isNotEmpty(bankDetailsDTO.getSchmType())) {
            return bankDetailsDTO.getBankAccountNumber().equals(this.getBankAccountNumber())
                    && bankDetailsDTO.getAccountStatus().equals(this.getAccountStatus())
                    && bankDetailsDTO.getSchmType().equals(this.getSchmType());
        }

        if (StringUtils.isNotEmpty(bankDetailsDTO.getBankAccountNumber()) && StringUtils.isNotEmpty(bankDetailsDTO.getAccountStatus())) {
            return bankDetailsDTO.getBankAccountNumber().equals(this.getBankAccountNumber()) &&
                    bankDetailsDTO.getAccountStatus().equals(this.getAccountStatus());
        }

        if (StringUtils.isNotEmpty(bankDetailsDTO.getBankAccountNumber()) && StringUtils.isNotEmpty(bankDetailsDTO.getSchmType())) {
            return bankDetailsDTO.getBankAccountNumber().equals(this.getBankAccountNumber()) &&
                    bankDetailsDTO.getSchmType().equals(this.getSchmType());
        }

        if (StringUtils.isNotEmpty(bankDetailsDTO.getBankAccountNumber())) {
            return bankDetailsDTO.getBankAccountNumber().equals(this.getBankAccountNumber());
        }

        return false;
    }

    public CusBankAccJoiningPartner getCusBankAccJoiningPartnerByDTO(CusBankAccJoiningPartnerDto cusBankAccJoiningPartnerDto) {
        return this.getCusBankAccJoiningPartnerList().stream().
                filter(cusBankAccJoiningPartner -> {
                    return cusBankAccJoiningPartner.equals(cusBankAccJoiningPartnerDto);
                }).findFirst().orElse(null);
    }

}

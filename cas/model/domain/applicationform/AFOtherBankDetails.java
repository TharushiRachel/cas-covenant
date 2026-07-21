package com.itechro.cas.model.domain.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_AF_OTHER_BANK_DETAILS")
public class AFOtherBankDetails extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_OTHER_BANK_DETAILS")
    @SequenceGenerator(name = "SEQ_T_AF_OTHER_BANK_DETAILS", sequenceName = "SEQ_T_AF_OTHER_BANK_DETAILS", allocationSize = 1)
    @Column(name = "OTHER_BANK_DETAIL_ID")
    private Integer otherBankDetailsID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASIC_INFORMATION_ID")
    private BasicInformation basicInformation;

    @Column(name = "APPLICATION_FORM_ID")
    private Integer applicationFormID;

    @Column(name = "NAME_OF_BANK")
    private String nameOfBank;

    @Column(name = "NAME_OF_BRANCH")
    private String nameOfBranch;

    @Column(name = "ACCOUNT_NO")
    private String accountNo;

    @Column(name = "TYPE_OF_ACCOUNT")
    private String typeOfAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getOtherBankDetailsID() {
        return otherBankDetailsID;
    }

    public void setOtherBankDetailsID(Integer otherBankDetailsID) {
        this.otherBankDetailsID = otherBankDetailsID;
    }

    public BasicInformation getBasicInformation() {
        return basicInformation;
    }

    public void setBasicInformation(BasicInformation basicInformation) {
        this.basicInformation = basicInformation;
    }

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    public String getNameOfBank() {
        return nameOfBank;
    }

    public void setNameOfBank(String nameOfBank) {
        this.nameOfBank = nameOfBank;
    }

    public String getNameOfBranch() {
        return nameOfBranch;
    }

    public void setNameOfBranch(String nameOfBranch) {
        this.nameOfBranch = nameOfBranch;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getTypeOfAccount() {
        return typeOfAccount;
    }

    public void setTypeOfAccount(String typeOfAccount) {
        this.typeOfAccount = typeOfAccount;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}

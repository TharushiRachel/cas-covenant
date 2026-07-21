package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.applicationform.AFOtherBankDetails;

import java.io.Serializable;

public class AFOtherBankDetailsDTO implements Serializable {

    private Integer otherBankDetailsID;

    private Integer basicInformationID;

    private Integer applicationFormID;

    private String nameOfBank;

    private String nameOfBranch;

    private String accountNo;

    private String typeOfAccount;

    private AppsConstants.Status status;

    public AFOtherBankDetailsDTO() {
    }

    public AFOtherBankDetailsDTO(AFOtherBankDetails afOtherBankDetails) {
        this.otherBankDetailsID = afOtherBankDetails.getOtherBankDetailsID();
        this.basicInformationID = afOtherBankDetails.getBasicInformation().getBasicInformationID();
        this.applicationFormID = afOtherBankDetails.getApplicationFormID();
        this.nameOfBank = afOtherBankDetails.getNameOfBank();
        this.nameOfBranch = afOtherBankDetails.getNameOfBranch();
        this.accountNo = afOtherBankDetails.getAccountNo();
        this.typeOfAccount = afOtherBankDetails.getTypeOfAccount();
        this.status = afOtherBankDetails.getStatus();
    }

    public Integer getOtherBankDetailsID() {
        return otherBankDetailsID;
    }

    public void setOtherBankDetailsID(Integer otherBankDetailsID) {
        this.otherBankDetailsID = otherBankDetailsID;
    }

    public Integer getBasicInformationID() {
        return basicInformationID;
    }

    public void setBasicInformationID(Integer basicInformationID) {
        this.basicInformationID = basicInformationID;
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

    @Override
    public String toString() {
        return "AFOtherBankDetailsDTO{" +
                "otherBankDetailsID=" + otherBankDetailsID +
                ", basicInformationID=" + basicInformationID +
                ", applicationFormID=" + applicationFormID +
                ", nameOfBank='" + nameOfBank + '\'' +
                ", nameOfBranch='" + nameOfBranch + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", typeOfAccount='" + typeOfAccount + '\'' +
                ", status=" + status +
                '}';
    }
}

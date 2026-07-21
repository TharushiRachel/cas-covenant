package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.applicationform.AFBorrowerGuarantor;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.math.BigDecimal;

public class AFBorrowerGuarantorDTO implements Serializable {

    private Integer borrowerGuarantorID;

    private Integer applicationFormID;

    private Integer basicInformationID;

    private String bankAndBranch;

    private String dateGrantedStr;

    private BigDecimal amount;

    private String borrowerName;

    private AppsConstants.YesNo isBorrowerOrGuarantor;

    private AppsConstants.Status status;

    public AFBorrowerGuarantorDTO() {
    }

    public AFBorrowerGuarantorDTO(AFBorrowerGuarantor afBorrowerGuarantor) {
        this.borrowerGuarantorID = afBorrowerGuarantor.getBorrowerGuarantorID();
        this.basicInformationID = afBorrowerGuarantor.getBasicInformation().getBasicInformationID();
        this.bankAndBranch = afBorrowerGuarantor.getBankAndBranch();
        if (afBorrowerGuarantor.getDateGranted() != null) {
            this.dateGrantedStr = CalendarUtil.getDefaultFormattedDateOnly(afBorrowerGuarantor.getDateGranted());
        }
        this.amount = afBorrowerGuarantor.getAmount();
        this.borrowerName = afBorrowerGuarantor.getBorrowerName();
        this.status = afBorrowerGuarantor.getStatus();
    }

    public Integer getBorrowerGuarantorID() {
        return borrowerGuarantorID;
    }

    public void setBorrowerGuarantorID(Integer borrowerGuarantorID) {
        this.borrowerGuarantorID = borrowerGuarantorID;
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

    public String getBankAndBranch() {
        return bankAndBranch;
    }

    public void setBankAndBranch(String bankAndBranch) {
        this.bankAndBranch = bankAndBranch;
    }

    public String getDateGrantedStr() {
        return dateGrantedStr;
    }

    public void setDateGrantedStr(String dateGrantedStr) {
        this.dateGrantedStr = dateGrantedStr;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public AppsConstants.YesNo getIsBorrowerOrGuarantor() {
        return isBorrowerOrGuarantor;
    }

    public void setIsBorrowerOrGuarantor(AppsConstants.YesNo isBorrowerOrGuarantor) {
        this.isBorrowerOrGuarantor = isBorrowerOrGuarantor;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AFBorrowerGuarantorDTO{" +
                "borrowerGuarantorID=" + borrowerGuarantorID +
                ", applicationFormID=" + applicationFormID +
                ", basicInformationID=" + basicInformationID +
                ", bankAndBranch='" + bankAndBranch + '\'' +
                ", dateGrantedStr='" + dateGrantedStr + '\'' +
                ", amount=" + amount +
                ", borrowerName=" + borrowerName +
                ", status=" + status +
                '}';
    }
}

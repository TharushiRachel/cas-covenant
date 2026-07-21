package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.DomainConstants;

import java.io.Serializable;
import java.math.BigDecimal;

public class FacilityPaperSummaryDTO implements Serializable {

    private Integer facilityPaperID;

    private String facilityPaperNumber;

    private String fpRefNumber;

    private String branchCode;

    private String bankAccountNumber;

    private String currentAssignUser;

    private String createdDateStr;

    private DomainConstants.FacilityPaperStatus facilityPaperStatus;

    private BigDecimal totalOutstandingAmount;

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public String getFacilityPaperNumber() {
        return facilityPaperNumber;
    }

    public void setFacilityPaperNumber(String facilityPaperNumber) {
        this.facilityPaperNumber = facilityPaperNumber;
    }

    public String getFpRefNumber() {
        return fpRefNumber;
    }

    public void setFpRefNumber(String fpRefNumber) {
        this.fpRefNumber = fpRefNumber;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getCurrentAssignUser() {
        return currentAssignUser;
    }

    public void setCurrentAssignUser(String currentAssignUser) {
        this.currentAssignUser = currentAssignUser;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public DomainConstants.FacilityPaperStatus getFacilityPaperStatus() {
        return facilityPaperStatus;
    }

    public void setFacilityPaperStatus(DomainConstants.FacilityPaperStatus facilityPaperStatus) {
        this.facilityPaperStatus = facilityPaperStatus;
    }

    public BigDecimal getTotalOutstandingAmount() {
        return totalOutstandingAmount;
    }

    public void setTotalOutstandingAmount(BigDecimal totalOutstandingAmount) {
        this.totalOutstandingAmount = totalOutstandingAmount;
    }

    @Override
    public String toString() {
        return "FacilityPaperSummaryDTO{" +
                "facilityPaperNumber='" + facilityPaperNumber + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", currentAssignUser='" + currentAssignUser + '\'' +
                ", facilityPaperStatus=" + facilityPaperStatus +
                '}';
    }
}

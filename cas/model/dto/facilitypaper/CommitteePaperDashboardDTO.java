package com.itechro.cas.model.dto.facilitypaper;

import java.io.Serializable;

public class CommitteePaperDashboardDTO implements Serializable {

    private String dashboardType;

    private String dashboardStatus;

    private String dashboardSubStatus;

    private Integer facilityPaperID;

    private String fpRefNumber;

    private String customerName;

    private String bankAccountID;

    private String branchCode;

    private String lastActionDateStr;

    private String assignUserDisplayName;

    private String assignDepartmentCode;

    private Integer assignUserLevel;

    private String facilityPaperStatus;

    private String committeePaperStatus;

    private Integer committeeID;

    private Integer committeePaperID;

    private String committeeName;

    private String committeeType;

    private Integer committeeTypeID;

    private String bccApproveStatus;
    private String bccDocsApproveStatus;



    public String getBccApproveStatus() {
        return bccApproveStatus;
    }

    public void setBccApproveStatus(String bccApproveStatus) {
        this.bccApproveStatus = bccApproveStatus;
    }

    public String getBccDocsApproveStatus() {
        return bccDocsApproveStatus;
    }

    public void setBccDocsApproveStatus(String bccDocsApproveStatus) {
        this.bccDocsApproveStatus = bccDocsApproveStatus;
    }

    public String getDashboardType() {
        return dashboardType;
    }

    public void setDashboardType(String dashboardType) {
        this.dashboardType = dashboardType;
    }

    public String getDashboardStatus() {
        return dashboardStatus;
    }

    public void setDashboardStatus(String dashboardStatus) {
        this.dashboardStatus = dashboardStatus;
    }

    public String getDashboardSubStatus() {
        return dashboardSubStatus;
    }

    public void setDashboardSubStatus(String dashboardSubStatus) {
        this.dashboardSubStatus = dashboardSubStatus;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBankAccountID() {
        return bankAccountID;
    }

    public void setBankAccountID(String bankAccountID) {
        this.bankAccountID = bankAccountID;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getLastActionDateStr() {
        return lastActionDateStr;
    }

    public void setLastActionDateStr(String lastActionDateStr) {
        this.lastActionDateStr = lastActionDateStr;
    }

    public String getAssignUserDisplayName() {
        return assignUserDisplayName;
    }

    public void setAssignUserDisplayName(String assignUserDisplayName) {
        this.assignUserDisplayName = assignUserDisplayName;
    }

    public String getAssignDepartmentCode() {
        return assignDepartmentCode;
    }

    public void setAssignDepartmentCode(String assignDepartmentCode) {
        this.assignDepartmentCode = assignDepartmentCode;
    }

    public String getFpRefNumber() {
        return fpRefNumber;
    }

    public void setFpRefNumber(String fpRefNumber) {
        this.fpRefNumber = fpRefNumber;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public Integer getAssignUserLevel() {
        return assignUserLevel;
    }

    public void setAssignUserLevel(Integer assignUserLevel) {
        this.assignUserLevel = assignUserLevel;
    }

    public String getFacilityPaperStatus() {
        return facilityPaperStatus;
    }

    public void setFacilityPaperStatus(String facilityPaperStatus) {
        this.facilityPaperStatus = facilityPaperStatus;
    }

    public String getCommitteePaperStatus() {
        return committeePaperStatus;
    }

    public void setCommitteePaperStatus(String committeePaperStatus) {
        this.committeePaperStatus = committeePaperStatus;
    }

    public Integer getCommitteeID() {
        return committeeID;
    }

    public void setCommitteeID(Integer committeeID) {
        this.committeeID = committeeID;
    }

    public Integer getCommitteePaperID() {
        return committeePaperID;
    }

    public void setCommitteePaperID(Integer committeePaperID) {
        this.committeePaperID = committeePaperID;
    }

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }

    public String getCommitteeType() {
        return committeeType;
    }

    public void setCommitteeType(String committeeType) {
        this.committeeType = committeeType;
    }

    public Integer getCommitteeTypeID() {
        return committeeTypeID;
    }

    public void setCommitteeTypeID(Integer committeeTypeID) {
        this.committeeTypeID = committeeTypeID;
    }

    @Override
    public String toString() {
        return "CommitteePaperDashboardDTO{" +
                "dashboardType='" + dashboardType + '\'' +
                ", dashboardStatus='" + dashboardStatus + '\'' +
                ", dashboardSubStatus='" + dashboardSubStatus + '\'' +
                ", facilityPaperID=" + facilityPaperID +
                ", fpRefNumber='" + fpRefNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", bankAccountID='" + bankAccountID + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", lastActionDateStr='" + lastActionDateStr + '\'' +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                ", assignDepartmentCode='" + assignDepartmentCode + '\'' +
                ", assignUserLevel=" + assignUserLevel +
                ", facilityPaperStatus='" + facilityPaperStatus + '\'' +
                ", committeePaperStatus='" + committeePaperStatus + '\'' +
                ", committeeID=" + committeeID +
                ", committeePaperID=" + committeePaperID +
                ", committeeName='" + committeeName + '\'' +
                ", committeeType='" + committeeType + '\'' +
                ", committeeTypeID=" + committeeTypeID +
                ", bccApproveStatus='" + bccApproveStatus + '\'' +
                ", bccDocsApproveStatus=" + bccDocsApproveStatus +
                '}';
    }
}

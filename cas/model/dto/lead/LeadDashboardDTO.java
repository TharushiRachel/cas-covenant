package com.itechro.cas.model.dto.lead;

import com.itechro.cas.commons.constants.AppsConstants;

import java.io.Serializable;

public class LeadDashboardDTO implements Serializable {

    private String dashboardType;

    private String dashboardStatus;

    private String dashboardSubStatus;

    private Integer id;

    private String referenceName;

    private String customerName;

    private String leadReferenceNumber;

    private String afReferenceNumber;

    private String fpReferenceNumber;

    private String branchCode;

    private String branchName;

    private String accountNumber;

    private String identificationNumber;

    private String createdDate;

    private String createdBy;

    private String assignedUser;

    private String status;

    private String fromDate;

    private String isCompLead;


    public LeadDashboardDTO() {
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

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getLeadReferenceNumber() {
        return leadReferenceNumber;
    }

    public void setLeadReferenceNumber(String leadReferenceNumber) {
        this.leadReferenceNumber = leadReferenceNumber;
    }

    public String getAfReferenceNumber() {
        return afReferenceNumber;
    }

    public void setAfReferenceNumber(String afReferenceNumber) {
        this.afReferenceNumber = afReferenceNumber;
    }

    public String getFpReferenceNumber() {
        return fpReferenceNumber;
    }

    public void setFpReferenceNumber(String fpReferenceNumber) {
        this.fpReferenceNumber = fpReferenceNumber;
    }

    public void setIsCompLead(String isCompLead) {
        this.isCompLead = isCompLead;
    }

    public String getIsCompLead() {
        if (this.isCompLead == null){
            return AppsConstants.YesNo.N.toString();
        }
        return this.isCompLead;
    }


    @Override
    public String toString() {
        return "LeadDashboardDTO{" +
                "dashboardType='" + dashboardType + '\'' +
                ", dashboardStatus='" + dashboardStatus + '\'' +
                ", dashboardSubStatus='" + dashboardSubStatus + '\'' +
                ", id=" + id +
                ", referenceName='" + referenceName + '\'' +
                ", customerName='" + customerName + '\'' +
                ", LeadReferenceNumber='" + leadReferenceNumber + '\'' +
                ", AfReferenceNumber='" + afReferenceNumber + '\'' +
                ", FpReferenceNumber='" + fpReferenceNumber + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", branchName='" + branchName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", identificationNumber='" + identificationNumber + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", assignedUser='" + assignedUser + '\'' +
                ", status='" + status + '\'' +
                ", fromDate='" + fromDate + '\'' +
                '}';
    }
}

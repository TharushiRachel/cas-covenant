package com.itechro.cas.model.dto.lead;

import com.itechro.cas.commons.constants.DomainConstants;

import java.io.Serializable;

public class LeadStatusUpdateRQ implements Serializable {

    private static final long serialVersionUID = -3862547728921844686L;

    private Integer leadID;

    private Integer applicationFormID;

    private DomainConstants.LeadStatus leadStatus;

    private String assignUserID;

    private String remark;

    private String actionedByDisplayName;

    private String action;

    private boolean allowFinacleData;

    private boolean allowCrib;

    private boolean allowKalypto;

    private String assignUserDisplayName;

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    private String customerBankAccountNumber;

    public Integer getLeadID() {
        return leadID;
    }

    public void setLeadID(Integer leadID) {
        this.leadID = leadID;
    }

    public DomainConstants.LeadStatus getLeadStatus() {
        return leadStatus;
    }

    public void setLeadStatus(DomainConstants.LeadStatus leadStatus) {
        this.leadStatus = leadStatus;
    }

    public String getAssignUserID() {
        return assignUserID;
    }

    public void setAssignUserID(String assignUserID) {
        this.assignUserID = assignUserID;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isAllowFinacleData() {
        return allowFinacleData;
    }

    public void setAllowFinacleData(boolean allowFinacleData) {
        this.allowFinacleData = allowFinacleData;
    }

    public boolean isAllowCrib() {
        return allowCrib;
    }

    public void setAllowCrib(boolean allowCrib) {
        this.allowCrib = allowCrib;
    }

    public boolean isAllowKalypto() {
        return allowKalypto;
    }

    public void setAllowKalypto(boolean allowKalypto) {
        this.allowKalypto = allowKalypto;
    }

    public String getCustomerBankAccountNumber() {
        return customerBankAccountNumber;
    }

    public void setCustomerBankAccountNumber(String customerBankAccountNumber) {
        this.customerBankAccountNumber = customerBankAccountNumber;
    }

    public String getActionedByDisplayName() {
        return actionedByDisplayName;
    }

    public void setActionedByDisplayName(String actionedByDisplayName) {
        this.actionedByDisplayName = actionedByDisplayName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAssignUserDisplayName() {
        return assignUserDisplayName;
    }

    public void setAssignUserDisplayName(String assignUserDisplayName) {
        this.assignUserDisplayName = assignUserDisplayName;
    }

    @Override
    public String toString() {
        return "LeadStatusUpdateRQ{" +
                "leadID=" + leadID +
                ", applicationFormID=" + applicationFormID +
                ", leadStatus=" + leadStatus +
                ", assignUserID='" + assignUserID + '\'' +
                ", remark='" + remark + '\'' +
                ", actionedByDisplayName='" + actionedByDisplayName + '\'' +
                ", action='" + action + '\'' +
                ", allowFinacleData=" + allowFinacleData +
                ", allowCrib=" + allowCrib +
                ", allowKalypto=" + allowKalypto +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                ", customerBankAccountNumber='" + customerBankAccountNumber + '\'' +
                '}';
    }
}

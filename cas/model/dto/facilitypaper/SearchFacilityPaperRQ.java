package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.common.PagedParamDTO;

import java.util.ArrayList;
import java.util.List;

public class SearchFacilityPaperRQ  extends PagedParamDTO {

    private static final long serialVersionUID = -1062192877923337640L;

    private String fpRefNumber;

    private String currentAssignUser;

    private String intiatedUserName;

    private String leadRefNumber;

    private String customerName;

    private String loginUpmAccessLevel;

    private String loggedInUserBranchCode; //divCode

    private List<String> otherUpmAccessLevels;

    private List<String> assignUsers;

    private String createdFromDateStr;

    private String createdToDateStr;

    private String branchCode;

    private DomainConstants.FacilityPaperStatus facilityPaperStatus;

    private AppsConstants.YesNo inboxRequest;

    private AppsConstants.YesNo inprogressRequest;

    private AppsConstants.YesNo returnRequest;

    private String assignUserDisplayName;

    private String bankAccountID;

    private AppsConstants.YesNo isCommittee;

    private String committeeType;

    public String getFpRefNumber() {
        return fpRefNumber;
    }

    public void setFpRefNumber(String fpRefNumber) {
        this.fpRefNumber = fpRefNumber;
    }

    public String getCurrentAssignUser() {
        return currentAssignUser;
    }

    public void setCurrentAssignUser(String currentAssignUser) {
        this.currentAssignUser = currentAssignUser;
    }

    public String getIntiatedUserName() {
        return intiatedUserName;
    }

    public void setIntiatedUserName(String intiatedUserName) {
        this.intiatedUserName = intiatedUserName;
    }

    public String getLeadRefNumber() {
        return leadRefNumber;
    }

    public void setLeadRefNumber(String leadRefNumber) {
        this.leadRefNumber = leadRefNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public DomainConstants.FacilityPaperStatus getFacilityPaperStatus() {
        return facilityPaperStatus;
    }

    public void setFacilityPaperStatus(DomainConstants.FacilityPaperStatus facilityPaperStatus) {
        this.facilityPaperStatus = facilityPaperStatus;
    }

    public AppsConstants.YesNo getInboxRequest() {
        return inboxRequest;
    }

    public void setInboxRequest(AppsConstants.YesNo inboxRequest) {
        this.inboxRequest = inboxRequest;
    }

    public boolean isInboxRequest() {
        return this.inboxRequest == AppsConstants.YesNo.Y;
    }

    public AppsConstants.YesNo getInprogressRequest() {
        return inprogressRequest;
    }

    public void setInprogressRequest(AppsConstants.YesNo inprogressRequest) {
        this.inprogressRequest = inprogressRequest;
    }

    public boolean isInProgressRequest() {
        return this.inprogressRequest == AppsConstants.YesNo.Y;
    }

    public AppsConstants.YesNo getReturnRequest() {
        return returnRequest;
    }

    public void setReturnRequest(AppsConstants.YesNo returnRequest) {
        this.returnRequest = returnRequest;
    }

    public boolean isReturnRequest() {
        return this.returnRequest == AppsConstants.YesNo.Y;
    }

    public String getLoginUpmAccessLevel() {
        return loginUpmAccessLevel;
    }

    public void setLoginUpmAccessLevel(String loginUpmAccessLevel) {
        this.loginUpmAccessLevel = loginUpmAccessLevel;
    }

    public List<String> getOtherUpmAccessLevels() {
        if (otherUpmAccessLevels == null) {
            this.otherUpmAccessLevels = new ArrayList<>();
        }
        return otherUpmAccessLevels;
    }

    public void setOtherUpmAccessLevels(List<String> otherUpmAccessLevels) {
        this.otherUpmAccessLevels = otherUpmAccessLevels;
    }

    public String getLoggedInUserBranchCode() {
        return loggedInUserBranchCode;
    }

    public void setLoggedInUserBranchCode(String loggedInUserBranchCode) {
        this.loggedInUserBranchCode = loggedInUserBranchCode;
    }

    public String getAssignUserDisplayName() {
        return assignUserDisplayName;
    }

    public void setAssignUserDisplayName(String assignUserDisplayName) {
        this.assignUserDisplayName = assignUserDisplayName;
    }

    public String getCreatedFromDateStr() {
        return createdFromDateStr;
    }

    public void setCreatedFromDateStr(String createdFromDateStr) {
        this.createdFromDateStr = createdFromDateStr;
    }

    public String getCreatedToDateStr() {
        return createdToDateStr;
    }

    public void setCreatedToDateStr(String createdToDateStr) {
        this.createdToDateStr = createdToDateStr;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public String getBankAccountID() {
        return bankAccountID;
    }

    public void setBankAccountID(String bankAccountID) {
        this.bankAccountID = bankAccountID;
    }

    public List<String> getAssignUsers() {
        if (assignUsers == null) {
            this.assignUsers = new ArrayList<>();
        }
        return assignUsers;
    }

    public void setAssignUsers(List<String> assignUsers) {
        this.assignUsers = assignUsers;
    }

    public AppsConstants.YesNo getIsCommittee() {
        return isCommittee;
    }

    public void setIsCommittee(AppsConstants.YesNo isCommittee) {
        this.isCommittee = isCommittee;
    }

    public String getCommitteeType() {
        return committeeType;
    }

    public void setCommitteeType(String committeeType) {
        this.committeeType = committeeType;
    }

    @Override
    public String toString() {
        return "FacilityPaperSearchRQ{" +
                "fpRefNumber='" + fpRefNumber + '\'' +
                ", currentAssignUser='" + currentAssignUser + '\'' +
                ", intiatedUserName='" + intiatedUserName + '\'' +
                ", leadRefNumber='" + leadRefNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", loginUpmAccessLevel='" + loginUpmAccessLevel + '\'' +
                ", loggedInUserBranchCode='" + loggedInUserBranchCode + '\'' +
                ", otherUpmAccessLevels=" + otherUpmAccessLevels +
                ", assignUsers=" + assignUsers +
                ", facilityPaperStatus=" + facilityPaperStatus +
                ", inboxRequest=" + inboxRequest +
                ", inprogressRequest=" + inprogressRequest +
                ", returnRequest=" + returnRequest +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                ", bankAccountID='" + bankAccountID + '\'' +
                ", createdFromDateStr='" + createdFromDateStr + '\'' +
                ", createdToDateStr='" + createdToDateStr + '\'' +
                ", isCommittee='" + isCommittee + '\'' +
                '}';
    }
}
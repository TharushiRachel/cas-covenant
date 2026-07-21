package com.itechro.cas.model.dto.facilitypaper.facilitypaperreview;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.common.PagedParamDTO;

import java.util.ArrayList;
import java.util.List;

public class FPReviewSummarySearchRQ extends PagedParamDTO {

    private Integer assignedUserID;

    private String userName;

    private String approvedUser;

    private String customerName;

    private String assignUserUpmGroupCode;

    private String fromDate;

    private String toDate;

    private String loginUpmAccessLevel;

    private List<String> otherUpmAccessLevels;

    private DomainConstants.DateRange dateRange;

    private List<DomainConstants.PaperReviewStatus> paperReviewStatusList;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public DomainConstants.DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DomainConstants.DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public Integer getAssignedUserID() {
        return assignedUserID;
    }

    public void setAssignedUserID(Integer assignedUserID) {
        this.assignedUserID = assignedUserID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAssignUserUpmGroupCode() {
        return assignUserUpmGroupCode;
    }

    public void setAssignUserUpmGroupCode(String assignUserUpmGroupCode) {
        this.assignUserUpmGroupCode = assignUserUpmGroupCode;
    }

    public String getLoginUpmAccessLevel() {
        return loginUpmAccessLevel;
    }

    public void setLoginUpmAccessLevel(String loginUpmAccessLevel) {
        this.loginUpmAccessLevel = loginUpmAccessLevel;
    }

    public String getApprovedUser() {
        return approvedUser;
    }

    public void setApprovedUser(String approvedUser) {
        this.approvedUser = approvedUser;
    }

    public List<String> getOtherUpmAccessLevels() {
        if (otherUpmAccessLevels == null) {
            otherUpmAccessLevels = new ArrayList<>();
        }
        return otherUpmAccessLevels;
    }

    public void setOtherUpmAccessLevels(List<String> otherUpmAccessLevels) {
        this.otherUpmAccessLevels = otherUpmAccessLevels;
    }

    public List<DomainConstants.PaperReviewStatus> getPaperReviewStatusList() {
        if (paperReviewStatusList == null) {
            this.paperReviewStatusList = new ArrayList<>();
        }
        return paperReviewStatusList;
    }

    public void setPaperReviewStatusList(List<DomainConstants.PaperReviewStatus> paperReviewStatusList) {
        this.paperReviewStatusList = paperReviewStatusList;
    }

    @Override
    public String toString() {
        return "FPReviewSummarySearchRQ{" +
                "assignedUserID=" + assignedUserID +
                ", userName='" + userName + '\'' +
                ", approvedUser='" + approvedUser + '\'' +
                ", customerName='" + customerName + '\'' +
                ", assignUserUpmGroupCode='" + assignUserUpmGroupCode + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", loginUpmAccessLevel='" + loginUpmAccessLevel + '\'' +
                ", otherUpmAccessLevels=" + otherUpmAccessLevels +
                ", dateRange=" + dateRange +
                ", paperReviewStatusList=" + paperReviewStatusList +
                '}';
    }
}

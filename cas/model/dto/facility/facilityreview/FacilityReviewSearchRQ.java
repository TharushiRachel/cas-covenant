package com.itechro.cas.model.dto.facility.facilityreview;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.common.PagedParamDTO;

import java.util.ArrayList;
import java.util.List;

public class FacilityReviewSearchRQ extends PagedParamDTO {

    private Integer facilityID;

    private Integer facilityPaperID;

    private String currentAssignedUserName;

    private Integer currentAssignedUserID;

    private String facilityRefCode;

    private String fpRefNumber;

    private String userName;

    private String customerName;

    private String assignUserUpmGroupCode;

    private String fromDate;

    private String toDate;

    private String upmAccessLevel;

    private DomainConstants.DateRange dateRange;

    private List<DomainConstants.PaperReviewStatus> paperReviewStatusList;

    private AppsConstants.Status status;

    public Integer getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(Integer facilityID) {
        this.facilityID = facilityID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public String getCurrentAssignedUserName() {
        return currentAssignedUserName;
    }

    public void setCurrentAssignedUserName(String currentAssignedUserName) {
        this.currentAssignedUserName = currentAssignedUserName;
    }

    public Integer getCurrentAssignedUserID() {
        return currentAssignedUserID;
    }

    public void setCurrentAssignedUserID(Integer currentAssignedUserID) {
        this.currentAssignedUserID = currentAssignedUserID;
    }

    public String getFacilityRefCode() {
        return facilityRefCode;
    }

    public void setFacilityRefCode(String facilityRefCode) {
        this.facilityRefCode = facilityRefCode;
    }

    public String getFpRefNumber() {
        return fpRefNumber;
    }

    public void setFpRefNumber(String fpRefNumber) {
        this.fpRefNumber = fpRefNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getUpmAccessLevel() {
        return upmAccessLevel;
    }

    public void setUpmAccessLevel(String upmAccessLevel) {
        this.upmAccessLevel = upmAccessLevel;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
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
        return "FacilityReviewSearchRQ{" +
                "facilityID=" + facilityID +
                ", facilityPaperID=" + facilityPaperID +
                ", currentAssignedUserName='" + currentAssignedUserName + '\'' +
                ", currentAssignedUserID=" + currentAssignedUserID +
                ", facilityRefCode='" + facilityRefCode + '\'' +
                ", fpRefNumber='" + fpRefNumber + '\'' +
                ", userName='" + userName + '\'' +
                ", customerName='" + customerName + '\'' +
                ", assignUserUpmGroupCode='" + assignUserUpmGroupCode + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", upmAccessLevel='" + upmAccessLevel + '\'' +
                ", dateRange=" + dateRange +
                ", status=" + status +
                '}';
    }
}

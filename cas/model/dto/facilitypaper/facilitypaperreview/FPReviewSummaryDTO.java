package com.itechro.cas.model.dto.facilitypaper.facilitypaperreview;

import java.io.Serializable;

public class FPReviewSummaryDTO implements Serializable {

    private String userName;

    private String displayName;

    private Integer assignedUserID;

    private Integer upmID;

    private Double totalFacilityAmount;

    private Double totalDirectExposureAmount;

    private Double totalIndirectExposureAmount;

    private Integer numberOfFacilities;

    private Double average;

    private Double noOfDays;

    public FPReviewSummaryDTO() {
    }

    public Double getTotalDirectExposureAmount() {
        return totalDirectExposureAmount;
    }

    public void setTotalDirectExposureAmount(Double totalDirectExposureAmount) {
        this.totalDirectExposureAmount = totalDirectExposureAmount;
    }

    public Double getTotalIndirectExposureAmount() {
        return totalIndirectExposureAmount;
    }

    public void setTotalIndirectExposureAmount(Double totalIndirectExposureAmount) {
        this.totalIndirectExposureAmount = totalIndirectExposureAmount;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getTotalFacilityAmount() {
        return totalFacilityAmount;
    }

    public Integer getAssignedUserID() {
        return assignedUserID;
    }

    public void setAssignedUserID(Integer assignedUserID) {
        this.assignedUserID = assignedUserID;
    }

    public Integer getUpmID() {
        return upmID;
    }

    public void setUpmID(Integer upmID) {
        this.upmID = upmID;
    }

    public void setTotalFacilityAmount(Double totalFacilityAmount) {
        this.totalFacilityAmount = totalFacilityAmount;
    }

    public Integer getNumberOfFacilities() {
        return numberOfFacilities;
    }

    public void setNumberOfFacilities(Integer numberOfFacilities) {
        this.numberOfFacilities = numberOfFacilities;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Double getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(Double noOfDays) {
        this.noOfDays = noOfDays;
    }
}

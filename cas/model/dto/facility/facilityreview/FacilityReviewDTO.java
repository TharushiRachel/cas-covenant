package com.itechro.cas.model.dto.facility.facilityreview;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;

import java.io.Serializable;
import java.math.BigDecimal;

public class FacilityReviewDTO implements Serializable {

    private Integer facilityPaperID;

    private String fpRefNumber;

    private Integer customerID;

    private String customerName;

    private String createdDateStr;// this value carry the approved date TODO refactor

    private BigDecimal totalExposurePrevious;

    private Double noOfDays;

    private BigDecimal totalExposureNew;

    private DomainConstants.PaperReviewStatus paperReviewStatus;

    private String reviewUserDisplayName;

    private AppsConstants.Status status;

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public String getFpRefNumber() {
        return fpRefNumber;
    }

    public void setFpRefNumber(String fpRefNumber) {
        this.fpRefNumber = fpRefNumber;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public Double getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(Double noOfDays) {
        this.noOfDays = noOfDays;
    }

    public BigDecimal getTotalExposurePrevious() {
        return totalExposurePrevious;
    }

    public void setTotalExposurePrevious(BigDecimal totalExposurePrevious) {
        this.totalExposurePrevious = totalExposurePrevious;
    }

    public BigDecimal getTotalExposureNew() {
        return totalExposureNew;
    }

    public void setTotalExposureNew(BigDecimal totalExposureNew) {
        this.totalExposureNew = totalExposureNew;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public DomainConstants.PaperReviewStatus getPaperReviewStatus() {
        return paperReviewStatus;
    }

    public void setPaperReviewStatus(DomainConstants.PaperReviewStatus paperReviewStatus) {
        this.paperReviewStatus = paperReviewStatus;
    }

    public String getReviewUserDisplayName() {
        return reviewUserDisplayName;
    }

    public void setReviewUserDisplayName(String reviewUserDisplayName) {
        this.reviewUserDisplayName = reviewUserDisplayName;
    }

    @Override
    public String toString() {
        return "FacilityReviewDTO{" +
                "facilityPaperID=" + facilityPaperID +
                ", fpRefNumber='" + fpRefNumber + '\'' +
                ", customerID=" + customerID +
                ", customerName='" + customerName + '\'' +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", totalExposurePrevious=" + totalExposurePrevious +
                ", noOfDays=" + noOfDays +
                ", totalExposureNew=" + totalExposureNew +
                ", paperReviewStatus=" + paperReviewStatus +
                ", reviewUserDisplayName='" + reviewUserDisplayName + '\'' +
                ", status=" + status +
                '}';
    }
}

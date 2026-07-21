package com.itechro.cas.model.dto.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.CftInterestRate;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityInterestRate;

import java.io.Serializable;

public class FacilityInterestRateDTO implements Serializable {

    private Integer facilityInterestRateID;

    private Integer facilityID;

    private Integer cftInterestRateID;

    private String rateCode;

    private String rateName;

    private String userComment;

    private Double value;

    private AppsConstants.YesNo isDefault;

    private DomainConstants.InterestRatingSubCategory interestRatingSubCategory;

    private AppsConstants.YesNo isEditable;

    private AppsConstants.Status status;

    public FacilityInterestRateDTO() {
    }

    public FacilityInterestRateDTO(FacilityInterestRate facilityInterestRate) {

        this.facilityInterestRateID = facilityInterestRate.getFacilityInterestRateID();
        if (facilityInterestRate.getFacility() != null) {
            this.facilityID = facilityInterestRate.getFacility().getFacilityID();
        }
        this.cftInterestRateID = facilityInterestRate.getCftInterestRateID();
        this.rateCode = facilityInterestRate.getRateCode();
        this.rateName = facilityInterestRate.getRateName();
        this.userComment = facilityInterestRate.getUserComment();
        this.value = facilityInterestRate.getValue();
        this.isDefault = facilityInterestRate.getIsDefault();
        this.isEditable = facilityInterestRate.getIsEditable();
        this.interestRatingSubCategory = facilityInterestRate.getInterestRatingSubCategory();
        this.status = facilityInterestRate.getStatus();
    }

    public FacilityInterestRateDTO(CftInterestRate cftInterestRate, Integer facilityID) {

        this.facilityID = facilityID;
        this.cftInterestRateID = cftInterestRate.getCftInterestRateID();
        this.rateCode = cftInterestRate.getRateCode();
        this.rateName = cftInterestRate.getRateName();
        this.value = cftInterestRate.getValue();
        this.isDefault = cftInterestRate.getIsDefault();
        this.isEditable = cftInterestRate.getIsEditable();
        this.interestRatingSubCategory = cftInterestRate.getInterestRatingSubCategory();
        this.status = AppsConstants.Status.ACT;
    }

    public Integer getFacilityInterestRateID() {
        return facilityInterestRateID;
    }

    public void setFacilityInterestRateID(Integer facilityInterestRateID) {
        this.facilityInterestRateID = facilityInterestRateID;
    }

    public Integer getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(Integer facilityID) {
        this.facilityID = facilityID;
    }

    public Integer getCftInterestRateID() {
        return cftInterestRateID;
    }

    public void setCftInterestRateID(Integer cftInterestRateID) {
        this.cftInterestRateID = cftInterestRateID;
    }

    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public AppsConstants.YesNo getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(AppsConstants.YesNo isDefault) {
        this.isDefault = isDefault;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getRateName() {
        return rateName;
    }

    public void setRateName(String rateName) {
        this.rateName = rateName;
    }

    public DomainConstants.InterestRatingSubCategory getInterestRatingSubCategory() {
        return interestRatingSubCategory;
    }

    public void setInterestRatingSubCategory(DomainConstants.InterestRatingSubCategory interestRatingSubCategory) {
        this.interestRatingSubCategory = interestRatingSubCategory;
    }

    public AppsConstants.YesNo getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(AppsConstants.YesNo isEditable) {
        this.isEditable = isEditable;
    }

    @Override
    public String toString() {
        return "FacilityInterestRateDTO{" +
                "facilityInterestRateID=" + facilityInterestRateID +
                ", facilityID=" + facilityID +
                ", cftInterestRateID=" + cftInterestRateID +
                ", rateCode='" + rateCode + '\'' +
                ", rateName='" + rateName + '\'' +
                ", userComment='" + userComment + '\'' +
                ", value=" + value +
                ", isDefault=" + isDefault +
                ", interestRatingSubCategory=" + interestRatingSubCategory +
                ", isEditable=" + isEditable +
                ", status=" + status +
                '}';
    }
}

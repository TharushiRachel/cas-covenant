package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.applicationform.AFFacilityInterestRate;

import java.io.Serializable;

public class AFFacilityInterestRateDTO implements Serializable {

    private Integer facilityInterestRateID;

    private Integer facilityID;

    private Integer cftInterestRateID;

    private String rateCode;

    private String userComment;

    private Double value;

    private AppsConstants.YesNo isDefault;

    private AppsConstants.Status status;

    public AFFacilityInterestRateDTO() {
    }

    public AFFacilityInterestRateDTO(AFFacilityInterestRate afFacilityInterestRate) {
        this.facilityInterestRateID = afFacilityInterestRate.getFacilityInterestRateID();
        this.facilityID = afFacilityInterestRate.getAfFacility().getFacilityID();
        this.cftInterestRateID = afFacilityInterestRate.getCftInterestRateID();
        this.rateCode = afFacilityInterestRate.getRateCode();
        this.userComment = afFacilityInterestRate.getUserComment();
        this.value = afFacilityInterestRate.getValue();
        this.isDefault = afFacilityInterestRate.getIsDefault();
        this.status = afFacilityInterestRate.getStatus();

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

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
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
}

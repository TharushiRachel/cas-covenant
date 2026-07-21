package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class FacilityInterestRateContentDTO extends BaseContentDTO {

    @SerializedName("FACILITY INTEREST RATE ID")
    private Integer facilityInterestRateID;

    @SerializedName("FACILITY ID")
    private Integer facilityID;

    @SerializedName("FACILITY REF CODE")
    private String facilityRefCode;

    @SerializedName("CFT INTEREST RATE ID")
    private Integer cftInterestRateID;

    @SerializedName("CFT INTEREST RATE NAME")
    private String cftInterestRateName;

    @SerializedName("RATE CODE")
    private String rateCode;

    @SerializedName("VALUE")
    private Double value;

    @SerializedName("IS DEFAULT")
    private String isDefault;

    @SerializedName("STATUS")
    private String status;

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

    public String getFacilityRefCode() {
        return facilityRefCode;
    }

    public void setFacilityRefCode(String facilityRefCode) {
        this.facilityRefCode = facilityRefCode;
    }

    public Integer getCftInterestRateID() {
        return cftInterestRateID;
    }

    public void setCftInterestRateID(Integer cftInterestRateID) {
        this.cftInterestRateID = cftInterestRateID;
    }

    public String getCftInterestRateName() {
        return cftInterestRateName;
    }

    public void setCftInterestRateName(String cftInterestRateName) {
        this.cftInterestRateName = cftInterestRateName;
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

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

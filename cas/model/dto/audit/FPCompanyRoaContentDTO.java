package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class FPCompanyRoaContentDTO extends BaseContentDTO{

    @SerializedName("FP COMPANY ROA ID")
    private Integer fpCompanyRoaID;

    @SerializedName("FACILITY PAPER ID")
    private Integer facilityPaperID;

    @SerializedName("FACILITY PAPER REF NUMBER")
    private String facilityPaperRefNumber;

    @SerializedName("DESCRIPTION")
    private String description;

    @SerializedName("STATUS")
    private String status;

    public Integer getFpCompanyRoaID() {
        return fpCompanyRoaID;
    }

    public void setFpCompanyRoaID(Integer fpCompanyRoaID) {
        this.fpCompanyRoaID = fpCompanyRoaID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public String getFacilityPaperRefNumber() {
        return facilityPaperRefNumber;
    }

    public void setFacilityPaperRefNumber(String facilityPaperRefNumber) {
        this.facilityPaperRefNumber = facilityPaperRefNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

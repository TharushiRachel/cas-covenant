package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class FacilityPurposeOfAdvanceContentDTO extends BaseContentDTO {

    @SerializedName("FACILITY PURPOSE OF ADVANCE ID")
    private Integer facilityPurposeOfAdvanceID;

    @SerializedName("FACILITY ID")
    private Integer facilityID;

    @SerializedName("FACILITY REF CODE")
    private String facilityRefCode;

    @SerializedName("PURPOSE OF ADVANCE")
    private String purposeOfAdvance;

    @SerializedName("REF CODE")
    private String referenceCode;

    @SerializedName("REF DESCRIPTION")
    private String referenceDescription;

    @SerializedName("STATUS")
    private String status;

    public Integer getFacilityPurposeOfAdvanceID() {
        return facilityPurposeOfAdvanceID;
    }

    public void setFacilityPurposeOfAdvanceID(Integer facilityPurposeOfAdvanceID) {
        this.facilityPurposeOfAdvanceID = facilityPurposeOfAdvanceID;
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

    public String getPurposeOfAdvance() {
        return purposeOfAdvance;
    }

    public void setPurposeOfAdvance(String purposeOfAdvance) {
        this.purposeOfAdvance = purposeOfAdvance;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public String getReferenceDescription() {
        return referenceDescription;
    }

    public void setReferenceDescription(String referenceDescription) {
        this.referenceDescription = referenceDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

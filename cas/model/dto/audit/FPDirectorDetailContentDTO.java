package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class FPDirectorDetailContentDTO extends BaseContentDTO {

    @SerializedName("FP DIRECTOR DETAIL ID")
    private Integer fpDirectorDetailID;

    @SerializedName("FACILITY PAPER ID")
    private Integer facilityPaperID;

    @SerializedName("FACILITY PAPER REF NUMBER")
    private String facilityPaperRefNumber;

    @SerializedName("DIRECTOR NAME")
    private String directorName;

    @SerializedName("FULL NAME")
    private String fullName;

    @SerializedName("ADDRESS")
    private String address;

    @SerializedName("CIVIL STATUS")
    private String civilStatus;

    @SerializedName("DATE OF BIRTH")
    private String dateOfBirth;

    @SerializedName("STATUS")
    private String status;

    public Integer getFpDirectorDetailID() {
        return fpDirectorDetailID;
    }

    public void setFpDirectorDetailID(Integer fpDirectorDetailID) {
        this.fpDirectorDetailID = fpDirectorDetailID;
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

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

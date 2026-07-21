package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;


public class FPUpcSectionDataContentDTO extends BaseContentDTO {

    @SerializedName("FP UPC SECTION DATA ID")
    private Integer fpUpcSectionDataID;

    @SerializedName("FACILITY PAPER ID")
    private Integer facilityPaperID;

    @SerializedName("FACILITY PAPER REF NUMBER")
    private String fpRefNumber;

    @SerializedName("UPC SECTION ID")
    private Integer upcSectionID;

    @SerializedName("UPC SECTION Name")
    private String upcSectionName;

    @SerializedName("PARENT SECTION ID")
    private Integer parentSectionID;

    @SerializedName("SECTION LEVEL")
    private Integer sectionLevel;

    @SerializedName("DISPLAY ORDER")
    private Integer displayOrder;

    @SerializedName("DATA")
    private String data;

    @SerializedName("STATUS")
    private String status;

    public Integer getFpUpcSectionDataID() {
        return fpUpcSectionDataID;
    }

    public void setFpUpcSectionDataID(Integer fpUpcSectionDataID) {
        this.fpUpcSectionDataID = fpUpcSectionDataID;
    }

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

    public Integer getUpcSectionID() {
        return upcSectionID;
    }

    public void setUpcSectionID(Integer upcSectionID) {
        this.upcSectionID = upcSectionID;
    }

    public String getUpcSectionName() {
        return upcSectionName;
    }

    public void setUpcSectionName(String upcSectionName) {
        this.upcSectionName = upcSectionName;
    }

    public Integer getParentSectionID() {
        return parentSectionID;
    }

    public void setParentSectionID(Integer parentSectionID) {
        this.parentSectionID = parentSectionID;
    }

    public Integer getSectionLevel() {
        return sectionLevel;
    }

    public void setSectionLevel(Integer sectionLevel) {
        this.sectionLevel = sectionLevel;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

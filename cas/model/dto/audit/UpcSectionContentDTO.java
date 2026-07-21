package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class UpcSectionContentDTO extends BaseContentDTO {

    @SerializedName("UPC SECTION ID")
    private Integer upcSectionID;

    @SerializedName("UPC SECTION NAME")
    private String upcSectionName;

    @SerializedName("UPC SECTION DESCRIPTION")
    private String upcSectionDescription;

    @SerializedName("APPROVE STATUS")
    private String approveStatus;

    @SerializedName("APPROVE DATE")
    private String approvedDateStr;

    @SerializedName("STATUS")
    private String status;

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

    public String getUpcSectionDescription() {
        return upcSectionDescription;
    }

    public void setUpcSectionDescription(String upcSectionDescription) {
        this.upcSectionDescription = upcSectionDescription;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApprovedDateStr() {
        return approvedDateStr;
    }

    public void setApprovedDateStr(String approvedDateStr) {
        this.approvedDateStr = approvedDateStr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

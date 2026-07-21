package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class UpmGroupContentDTO extends BaseContentDTO {

    @SerializedName("UPM GROUP ID")
    private Integer upmGroupID;

    @SerializedName("GROUP CODE")
    private String groupCode;

    @SerializedName("REFERENCE NAME")
    private String referenceName;

    @SerializedName("WORK FLOW LEVEL")
    private Integer workFlowLevel;

    @SerializedName("APPROVE STATUS")
    private String approveStatus;

    @SerializedName("APPROVE DATE")
    private String approvedDateStr;

    @SerializedName("STATUS")
    private String status;

    public Integer getUpmGroupID() {
        return upmGroupID;
    }

    public void setUpmGroupID(Integer upmGroupID) {
        this.upmGroupID = upmGroupID;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public Integer getWorkFlowLevel() {
        return workFlowLevel;
    }

    public void setWorkFlowLevel(Integer workFlowLevel) {
        this.workFlowLevel = workFlowLevel;
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

package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class WorkFlowTemplateContentDTO extends BaseContentDTO {

    @SerializedName("WORK FLOW TEMPLATE ID")
    private Integer workFlowTemplateID;

    @SerializedName("WORK FLOW TEMPLATE NAME")
    private String workFlowTemplateName;

    @SerializedName("CODE")
    private String code;

    @SerializedName("DESCRIPTION")
    private String description;

    @SerializedName("APPROVE STATUS")
    private String approveStatus;

    @SerializedName("APPROVE DATE")
    private String approvedDateStr;

    @SerializedName("STATUS")
    private String status;

    public Integer getWorkFlowTemplateID() {
        return workFlowTemplateID;
    }

    public void setWorkFlowTemplateID(Integer workFlowTemplateID) {
        this.workFlowTemplateID = workFlowTemplateID;
    }

    public String getWorkFlowTemplateName() {
        return workFlowTemplateName;
    }

    public void setWorkFlowTemplateName(String workFlowTemplateName) {
        this.workFlowTemplateName = workFlowTemplateName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

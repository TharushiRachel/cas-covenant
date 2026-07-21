package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.common.PagedParamDTO;

import java.io.Serializable;
import java.util.List;

public class WorkFlowTemplateSearchRQ extends PagedParamDTO implements Serializable {

    private Integer workFlowTemplateID;

    private String workFlowTemplateName;

    private String code;

    private String description;

    private AppsConstants.Status status;

    private List<DomainConstants.MasterDataApproveStatus> approveStatusList;

    private String approvedDateStr;

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

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public List<DomainConstants.MasterDataApproveStatus> getApproveStatusList() {
        return approveStatusList;
    }

    public void setApproveStatusList(List<DomainConstants.MasterDataApproveStatus> approveStatusList) {
        this.approveStatusList = approveStatusList;
    }

    public String getApprovedDateStr() {
        return approvedDateStr;
    }

    public void setApprovedDateStr(String approvedDateStr) {
        this.approvedDateStr = approvedDateStr;
    }

    @Override
    public String toString() {
        return "WorkFlowTemplateDTO{" +
                "workFlowTemplateID=" + workFlowTemplateID +
                "workFlowTemplateName=" + workFlowTemplateName +
                "description=" + description +
                "status=" + status +
                "code=" + code +
                "approveStatus=" + approveStatusList +
                "approvedDateStr=" + approvedDateStr +
                '}';
    }
}

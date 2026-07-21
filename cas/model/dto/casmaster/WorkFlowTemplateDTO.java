package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.WorkFlowTemplate;
import com.itechro.cas.model.domain.casmaster.WorkFlowTemplateData;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WorkFlowTemplateDTO implements Serializable {

    private Integer workFlowTemplateID;

    private String workFlowTemplateName;

    private String code;

    private String description;

    private AppsConstants.Status status;

    private DomainConstants.MasterDataApproveStatus approveStatus;

    private String approvedDateStr;

    private String approvedBy;

    private String createdDateStr;

    private String createdBy;

    private String modifiedDateStr;

    private String modifiedBy;

    private List<WorkFlowTemplateDataDTO> workFlowTemplateDataDTOList;

    public WorkFlowTemplateDTO() {
    }

    public WorkFlowTemplateDTO(WorkFlowTemplate workFlowTemplate) {
        this.workFlowTemplateID = workFlowTemplate.getWorkFlowTemplateID();
        this.workFlowTemplateName = workFlowTemplate.getWorkFlowTemplateName();
        this.code = workFlowTemplate.getCode();
        this.description = workFlowTemplate.getDescription();
        this.status = workFlowTemplate.getStatus();
        this.approveStatus = workFlowTemplate.getApproveStatus();
        if (workFlowTemplate.getApprovedDate() != null) {
            this.approvedDateStr = CalendarUtil.getDefaultFormattedDateTime(workFlowTemplate.getApprovedDate());
        }
        this.approvedBy = workFlowTemplate.getApprovedBy();
        if(workFlowTemplate.getCreatedDate() != null){
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(workFlowTemplate.getCreatedDate());
        }
        this.createdBy = workFlowTemplate.getCreatedBy();
        if(workFlowTemplate.getModifiedDate() != null){
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(workFlowTemplate.getModifiedDate());
        }
        this.modifiedBy = workFlowTemplate.getModifiedBy();

        for (WorkFlowTemplateData workFlowTemplateData : workFlowTemplate.getWorkFlowTemplateDataSet()) {
            this.getWorkFlowTemplateDataDTOList().add(new WorkFlowTemplateDataDTO(workFlowTemplateData));
        }
    }

    public Integer getWorkFlowTemplateID() {
        return workFlowTemplateID;
    }

    public void setWorkFlowTemplateID(Integer workFlowTemplateID) {
        this.workFlowTemplateID = workFlowTemplateID;
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

    public String getWorkFlowTemplateName() {
        return workFlowTemplateName;
    }

    public void setWorkFlowTemplateName(String workFlowTemplateName) {
        this.workFlowTemplateName = workFlowTemplateName;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public DomainConstants.MasterDataApproveStatus getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(DomainConstants.MasterDataApproveStatus approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApprovedDateStr() {
        return approvedDateStr;
    }

    public void setApprovedDateStr(String approvedDateStr) {
        this.approvedDateStr = approvedDateStr;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedDateStr() {
        return modifiedDateStr;
    }

    public void setModifiedDateStr(String modifiedDateStr) {
        this.modifiedDateStr = modifiedDateStr;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public List<WorkFlowTemplateDataDTO> getWorkFlowTemplateDataDTOList() {
        if (workFlowTemplateDataDTOList == null) {
            workFlowTemplateDataDTOList = new ArrayList<>();
        }
        return workFlowTemplateDataDTOList;
    }

    public void setWorkFlowTemplateDataDTOList(List<WorkFlowTemplateDataDTO> workFlowTemplateDataDTOList) {
        this.workFlowTemplateDataDTOList = workFlowTemplateDataDTOList;
    }

    @Override
    public String toString() {
        return "WorkFlowTemplateDTO{" +
                "workFlowTemplateID=" + workFlowTemplateID +
                "workFlowTemplateName=" + workFlowTemplateName +
                "description=" + description +
                "status=" + status +
                "code=" + code +
                "approveStatus=" + approveStatus +
                "approvedDateStr=" + approvedDateStr +
                "approvedBy=" + approvedBy +
                '}';
    }
}

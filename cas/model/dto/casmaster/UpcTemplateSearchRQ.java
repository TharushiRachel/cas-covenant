package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.common.PagedParamDTO;

import java.io.Serializable;
import java.util.List;

public class UpcTemplateSearchRQ extends PagedParamDTO implements Serializable {

    private Integer upcTemplateID;

    private String description;

    private String templateName;

    private AppsConstants.Status status;

    private List<DomainConstants.MasterDataApproveStatus> approveStatusList;

    private String approvedDateStr;

    private String approvedBy;

    public Integer getUpcTemplateID() {
        return upcTemplateID;
    }

    public void setUpcTemplateID(Integer upcTemplateID) {
        this.upcTemplateID = upcTemplateID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
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

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }


    @Override
    public String toString() {
        return "UpcTemplateSearchRQ{" +
                "upcTemplateID=" + upcTemplateID +
                ", description='" + description + '\'' +
                ", templateName='" + templateName + '\'' +
                ", status=" + status +
                ", approveStatus=" + approveStatusList +
                ", approvedDateStr='" + approvedDateStr + '\'' +
                ", approvedBy='" + approvedBy + '\'' +
                '}';
    }
}

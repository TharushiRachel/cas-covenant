package com.itechro.cas.model.dto.casmaster;


import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.UpcTemplate;
import com.itechro.cas.model.domain.casmaster.UpcTemplateData;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UpcTemplateDTO implements Serializable {

    private Integer upcTemplateID;

    private String templateName;

    private String description;

    private AppsConstants.Status status;

    private DomainConstants.MasterDataApproveStatus approveStatus;

    private String approvedDateStr;

    private String approvedBy;

    private String createdDateStr;

    private String createdBy;

    private String modifiedDateStr;

    private String modifiedBy;

    private String upcLabel;

    private String upcLabelBackgroundColor;

    private String upcLabelFontColor;

    private String emails;

    private List<UpcTemplateDataDTO> upcTemplateDataDTOList;

    public UpcTemplateDTO() {
    }

    public UpcTemplateDTO(UpcTemplate upcTemplate) {

        this.upcTemplateID = upcTemplate.getUpcTemplateID();
        this.templateName = upcTemplate.getTemplateName();
        this.description = upcTemplate.getDescription();
        this.approveStatus = upcTemplate.getApproveStatus();
        this.status = upcTemplate.getStatus();
        this.emails = upcTemplate.getNotifyEmails();
        for (UpcTemplateData templateData : upcTemplate.getUpcTemplateDataSet()) {
            this.getUpcTemplateDataDTOList().add(new UpcTemplateDataDTO(templateData));
        }
        this.getUpcTemplateDataDTOList().sort(Comparator.comparingInt(UpcTemplateDataDTO::getDisplayOrder));
        if (upcTemplate.getApprovedDate() != null) {
            this.approvedDateStr = CalendarUtil.getDefaultFormattedDateTime(upcTemplate.getApprovedDate());
        }
        this.approvedBy = upcTemplate.getApprovedBy();
        if (upcTemplate.getCreatedDate() != null) {
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(upcTemplate.getCreatedDate());
        }
        this.createdBy = upcTemplate.getCreatedBy();
        if (upcTemplate.getModifiedDate() != null) {
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(upcTemplate.getModifiedDate());
        }
        this.modifiedBy = upcTemplate.getModifiedBy();
        this.upcLabel = upcTemplate.getUpcLabel();
        this.upcLabelBackgroundColor = upcTemplate.getUpcLabelBackgroundColor();
        this.upcLabelFontColor = upcTemplate.getUpcLabelFontColor();
    }

    public Integer getUpcTemplateID() {
        return upcTemplateID;
    }

    public void setUpcTemplateID(Integer upcTemplateID) {
        this.upcTemplateID = upcTemplateID;
    }


    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
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

    public List<UpcTemplateDataDTO> getUpcTemplateDataDTOList() {
        if (upcTemplateDataDTOList == null) {
            upcTemplateDataDTOList = new ArrayList<>();
        }
        return upcTemplateDataDTOList;
    }

    public String getUpcLabel() {
        return upcLabel;
    }

    public void setUpcLabel(String upcLabel) {
        this.upcLabel = upcLabel;
    }

    public String getUpcLabelBackgroundColor() {
        return upcLabelBackgroundColor;
    }

    public void setUpcLabelBackgroundColor(String upcLabelBackgroundColor) {
        this.upcLabelBackgroundColor = upcLabelBackgroundColor;
    }

    public String getUpcLabelFontColor() {
        return upcLabelFontColor;
    }

    public void setUpcLabelFontColor(String upcLabelFontColor) {
        this.upcLabelFontColor = upcLabelFontColor;
    }

    public void setUpcTemplateDataDTOList(List<UpcTemplateDataDTO> upcTemplateDataDTOList) {
        this.upcTemplateDataDTOList = upcTemplateDataDTOList;
    }

    @Override
    public String toString() {
        return "UpcTemplateDTO{" +
                "upcTemplateID=" + upcTemplateID +
                ", templateName='" + templateName + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", approveStatus=" + approveStatus +
                ", approvedDateStr='" + approvedDateStr + '\'' +
                ", approvedBy='" + approvedBy + '\'' +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedDateStr='" + modifiedDateStr + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", upcLabel='" + upcLabel + '\'' +
                ", upcLabelBackgroundColor='" + upcLabelBackgroundColor + '\'' +
                ", upcLabelFontColor='" + upcLabelFontColor + '\'' +
                ", upcTemplateDataDTOList=" + upcTemplateDataDTOList +
                '}';
    }

    public String getEmails() {
        if (emails == null){
            return "";
        }
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }
}

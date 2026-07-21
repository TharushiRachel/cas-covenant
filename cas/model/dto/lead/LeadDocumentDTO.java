package com.itechro.cas.model.dto.lead;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.lead.LeadDocument;
import com.itechro.cas.model.dto.casmaster.SupportingDocDTO;
import com.itechro.cas.model.dto.storage.DocStorageDTO;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class LeadDocumentDTO implements Serializable {

    private Integer leadDocumentID;

    private Integer leadID;

    private SupportingDocDTO supportingDocDTO;

    private DocStorageDTO docStorageDTO;

    private String remark;

    private String createdDateStr;

    private String uploadedUserDisplayName;

    private String uploadedDivCode;

    private AppsConstants.Status status;

    private String createdBy;

    public LeadDocumentDTO() {
    }

    public LeadDocumentDTO(LeadDocument leadDocument) {
        this.leadDocumentID = leadDocument.getLeadDocumentID();
        this.leadID = leadDocument.getLead().getLeadID();
        if (leadDocument.getSupportingDoc() != null) {
            this.supportingDocDTO = new SupportingDocDTO(leadDocument.getSupportingDoc());
        }
        if (leadDocument.getDocStorage() != null) {
            this.docStorageDTO = new DocStorageDTO(leadDocument.getDocStorage(), false);
        }
        if (leadDocument.getCreatedDate() != null) {
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(leadDocument.getCreatedDate());
        }
        this.remark = leadDocument.getRemark();
        this.status = leadDocument.getStatus();
        this.uploadedDivCode = leadDocument.getUploadedDivCode();
        this.uploadedUserDisplayName = leadDocument.getUploadedUserDisplayName();
        this.createdBy = leadDocument.getCreatedBy();
    }

    public Integer getLeadDocumentID() {
        return leadDocumentID;
    }

    public void setLeadDocumentID(Integer leadDocumentID) {
        this.leadDocumentID = leadDocumentID;
    }

    public Integer getLeadID() {
        return leadID;
    }

    public void setLeadID(Integer leadID) {
        this.leadID = leadID;
    }

    public SupportingDocDTO getSupportingDocDTO() {
        return supportingDocDTO;
    }

    public void setSupportingDocDTO(SupportingDocDTO supportingDocDTO) {
        this.supportingDocDTO = supportingDocDTO;
    }

    public DocStorageDTO getDocStorageDTO() {
        return docStorageDTO;
    }

    public void setDocStorageDTO(DocStorageDTO docStorageDTO) {
        this.docStorageDTO = docStorageDTO;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public String getUploadedUserDisplayName() {
        return uploadedUserDisplayName;
    }

    public void setUploadedUserDisplayName(String uploadedUserDisplayName) {
        this.uploadedUserDisplayName = uploadedUserDisplayName;
    }

    public String getUploadedDivCode() {
        return uploadedDivCode;
    }

    public void setUploadedDivCode(String uploadedDivCode) {
        this.uploadedDivCode = uploadedDivCode;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}

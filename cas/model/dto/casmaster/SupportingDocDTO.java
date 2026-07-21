package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.SupportingDoc;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class SupportingDocDTO implements Serializable {

    private Integer supportingDocID;

    private String documentName;

    private String description;

    private String supportDocumentType;

    private AppsConstants.Status status;

    private DomainConstants.MasterDataApproveStatus approveStatus;

    private String approvedDateStr;

    private String approvedBy;

    private String createdDateStr;

    private String createdBy;

    private String modifiedDateStr;

    private String modifiedBy;

    public SupportingDocDTO() {
    }

    public SupportingDocDTO(SupportingDoc supportingDoc) {
        this.supportingDocID = supportingDoc.getSupportingDocID();
        this.documentName = supportingDoc.getDocumentName();
        this.description = supportingDoc.getDescription();
        this.supportDocumentType = supportingDoc.getSupportDocumentType();
        this.status = supportingDoc.getStatus();

        if (supportingDoc.getApprovedDate() != null) {
            this.approvedDateStr = CalendarUtil.getDefaultFormattedDateTime(supportingDoc.getApprovedDate());
        }
        this.approveStatus = supportingDoc.getApproveStatus();
        this.approvedBy = supportingDoc.getApprovedBy();
        if(supportingDoc.getCreatedDate() != null){
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(supportingDoc.getCreatedDate());
        }
        this.createdBy = supportingDoc.getCreatedBy();
        if(supportingDoc.getModifiedDate() != null){
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(supportingDoc.getModifiedDate());
        }
        this.modifiedBy = supportingDoc.getModifiedBy();
    }

    public Integer getSupportingDocID() {
        return supportingDocID;
    }

    public void setSupportingDocID(Integer supportingDocID) {
        this.supportingDocID = supportingDocID;
    }


    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupportDocumentType() {
        return supportDocumentType;
    }


    public void setSupportDocumentType(String supportDocumentType) {
        this.supportDocumentType = supportDocumentType;
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

    @Override
    public String toString() {
        return "SupportingDocDTO{" +
                "supportingDocID=" + supportingDocID +
                "documentName=" + documentName +
                "description=" + description +
                "supportDocumentType=" + supportDocumentType +
                "status=" + status +
                "approveStatus=" + approveStatus +
                "approvedDateStr=" + approvedDateStr +
                "approvedBy=" + approvedBy +
                '}';
    }
}

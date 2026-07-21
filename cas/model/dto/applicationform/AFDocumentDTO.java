package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.applicationform.AFDocument;
import com.itechro.cas.model.dto.storage.DocStorageDTO;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class AFDocumentDTO implements Serializable {

    private Integer afDocumentID;

    private Integer applicationFormID;

    private Integer supportingDocID;

    private String documentName;

    private String supportDocDescription;

    private String supportDocumentType;

    private DocStorageDTO docStorageDTO;

    private String remark;

    private String createdDateStr;

    private String uploadedUserDisplayName;

    private String uploadedDivCode;

    private String createdBy;

    private AppsConstants.Status status;

    public AFDocumentDTO() {
    }

    public AFDocumentDTO(AFDocument afDocument) {
        this(afDocument, true);
    }

    public AFDocumentDTO(AFDocument afDocument, boolean loadDoc) {
        this.afDocumentID = afDocument.getAfDocumentID();
        this.applicationFormID = afDocument.getApplicationForm().getApplicationFormID();
        this.supportingDocID = afDocument.getSupportingDoc().getSupportingDocID();
        this.documentName = afDocument.getSupportingDoc().getDocumentName();
        this.supportDocDescription = afDocument.getSupportingDoc().getDescription();
        this.supportDocumentType = afDocument.getSupportingDoc().getSupportDocumentType();
        if (afDocument.getDocStorage() != null) {
            this.docStorageDTO = new DocStorageDTO(afDocument.getDocStorage(), loadDoc);
        }
        if (afDocument.getCreatedDate() != null) {
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(afDocument.getCreatedDate());
        }
        this.remark = afDocument.getRemark();
        this.status = afDocument.getStatus();
        this.createdBy = afDocument.getCreatedBy();
        this.uploadedUserDisplayName = afDocument.getUploadedUserDisplayName();
        this.uploadedDivCode = afDocument.getUploadedDivCode();
    }

    public Integer getAfDocumentID() {
        return afDocumentID;
    }

    public void setAfDocumentID(Integer afDocumentID) {
        this.afDocumentID = afDocumentID;
    }

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
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

    public String getSupportDocDescription() {
        return supportDocDescription;
    }

    public void setSupportDocDescription(String supportDocDescription) {
        this.supportDocDescription = supportDocDescription;
    }

    public String getSupportDocumentType() {
        return supportDocumentType;
    }

    public void setSupportDocumentType(String supportDocumentType) {
        this.supportDocumentType = supportDocumentType;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    @Override
    public String toString() {
        return "AFDocumentDTO{" +
                "afDocumentID=" + afDocumentID +
                ", applicationFormID=" + applicationFormID +
                ", supportingDocID=" + supportingDocID +
                ", documentName='" + documentName + '\'' +
                ", supportDocDescription='" + supportDocDescription + '\'' +
                ", supportDocumentType='" + supportDocumentType + '\'' +
                ", remark='" + remark + '\'' +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", uploadedUserDisplayName='" + uploadedUserDisplayName + '\'' +
                ", uploadedDivCode='" + uploadedDivCode + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", status=" + status +
                '}';
    }
}

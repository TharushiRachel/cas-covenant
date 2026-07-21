package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.FPDocument;
import com.itechro.cas.model.dto.storage.DocStorageDTO;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class FPDocumentDTO implements Serializable {

    private Integer fpOtherBankFacilityID;

    private Integer facilityPaperID;

    private Integer supportingDocID;

    private String documentName;

    private String supportDocDescription;

    private String supportDocumentType;

    private DocStorageDTO docStorageDTO;

    private String description;

    private AppsConstants.Status status;

    private String createdBy;

    private String createdDateStr;

    private String uploadedUserDisplayName;

    private String uploadedDivCode;

    public FPDocumentDTO() {
    }

    public FPDocumentDTO(FPDocument fpDocument) {
        this(fpDocument, true);
    }

    public FPDocumentDTO(FPDocument fpDocument, boolean loadDoc) {
        this.fpOtherBankFacilityID = fpDocument.getFpDocumentID();
        this.facilityPaperID = fpDocument.getFacilityPaper().getFacilityPaperID();
        this.supportingDocID = fpDocument.getSupportingDoc().getSupportingDocID();
        this.documentName = fpDocument.getSupportingDoc().getDocumentName();
        this.supportDocDescription = fpDocument.getSupportingDoc().getDescription();
        this.supportDocumentType = fpDocument.getSupportingDoc().getSupportDocumentType();
        if (fpDocument.getDocStorage() != null) {
            this.docStorageDTO = new DocStorageDTO(fpDocument.getDocStorage(), loadDoc);
        }
        if (fpDocument.getCreatedDate() != null) {
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(fpDocument.getCreatedDate());
        }
        this.description = fpDocument.getDescription();
        this.status = fpDocument.getStatus();
        this.createdBy = fpDocument.getCreatedBy();
        this.uploadedUserDisplayName = fpDocument.getUploadedUserDisplayName();
        this.uploadedDivCode = fpDocument.getUploadedDivCode();
    }

    public Integer getFpOtherBankFacilityID() {
        return fpOtherBankFacilityID;
    }

    public void setFpOtherBankFacilityID(Integer fpOtherBankFacilityID) {
        this.fpOtherBankFacilityID = fpOtherBankFacilityID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }
}

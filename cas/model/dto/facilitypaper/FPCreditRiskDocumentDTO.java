package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.FPCreditRiskDocument;
import com.itechro.cas.model.dto.storage.DocStorageDTO;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class FPCreditRiskDocumentDTO implements Serializable {

    private Integer creditRiskDocumentID;

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

    private AppsConstants.YesNo isLocked;

    public FPCreditRiskDocumentDTO() {
    }

    public FPCreditRiskDocumentDTO(FPCreditRiskDocument fpCreditRiskDocument) {
        this(fpCreditRiskDocument, true);
    }

    public FPCreditRiskDocumentDTO(FPCreditRiskDocument fpCreditRiskDocument, boolean loadDoc) {
        this.creditRiskDocumentID = fpCreditRiskDocument.getFpCreditRiskDocumentID();
        this.facilityPaperID = fpCreditRiskDocument.getFacilityPaper().getFacilityPaperID();
//        this.supportingDocID = fpCreditRiskDocument.getSupportingDoc().getSupportingDocID();
//        this.documentName = fpCreditRiskDocument.getSupportingDoc().getDocumentName();
//        this.supportDocDescription = fpCreditRiskDocument.getSupportingDoc().getDescription();
//        this.supportDocumentType = fpCreditRiskDocument.getSupportingDoc().getSupportDocumentType();
        if (fpCreditRiskDocument.getDocStorage() != null) {
            this.docStorageDTO = new DocStorageDTO(fpCreditRiskDocument.getDocStorage(), loadDoc);
        }
        if (fpCreditRiskDocument.getCreatedDate() != null) {
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(fpCreditRiskDocument.getCreatedDate());
        }
        this.description = fpCreditRiskDocument.getDescription();
        this.status = fpCreditRiskDocument.getStatus();
        this.createdBy = fpCreditRiskDocument.getCreatedBy();
        this.uploadedUserDisplayName = fpCreditRiskDocument.getUploadedUserDisplayName();
        this.uploadedDivCode = fpCreditRiskDocument.getUploadedDivCode();
        this.isLocked = fpCreditRiskDocument.getIsLocked();
    }

    public Integer getCreditRiskDocumentID() {
        return creditRiskDocumentID;
    }

    public void setCreditRiskDocumentID(Integer creditRiskDocumentID) {
        this.creditRiskDocumentID = creditRiskDocumentID;
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

    public AppsConstants.YesNo getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(AppsConstants.YesNo isLocked) {
        this.isLocked = isLocked;
    }
}

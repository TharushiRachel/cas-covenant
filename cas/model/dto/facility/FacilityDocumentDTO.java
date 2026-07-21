package com.itechro.cas.model.dto.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.casmaster.CftSupportingDoc;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityDocument;
import com.itechro.cas.model.dto.storage.DocStorageDTO;

import java.io.Serializable;

public class FacilityDocumentDTO implements Serializable {

    private Integer facilityDocumentID;

    private Integer facilityID;

    private Integer facilityPaperID;

    private Integer cftSupportDocID;

    private Integer supportingDocID;

    private String supportingDocumentName;

    private DocStorageDTO docStorageDTO;

    private AppsConstants.YesNo mandatory;

    private Integer displayOrder;

    private String remark;

    private AppsConstants.Status status;

    private String createdBy;

    public FacilityDocumentDTO() {
    }

    public FacilityDocumentDTO(FacilityDocument facilityDocument) {

        this(facilityDocument, true);
    }

    public FacilityDocumentDTO(FacilityDocument facilityDocument, boolean loadDoc) {

        this.facilityDocumentID = facilityDocument.getFacilityDocumentID();
        if (facilityDocument.getFacility() != null) {
            this.facilityID = facilityDocument.getFacility().getFacilityID();
        }
        if (facilityDocument.getSupportingDoc() != null) {
            this.supportingDocID = facilityDocument.getSupportingDoc().getSupportingDocID();
            this.supportingDocumentName = facilityDocument.getSupportingDoc().getDocumentName();
        }
        if (facilityDocument.getDocStorage() != null) {
            this.docStorageDTO = new DocStorageDTO(facilityDocument.getDocStorage(), loadDoc);
        }

        this.cftSupportDocID = facilityDocument.getCftSupportDocID();
        this.mandatory = facilityDocument.getMandatory();
        this.displayOrder = facilityDocument.getDisplayOrder();
        this.remark = facilityDocument.getRemark();
        this.status = facilityDocument.getStatus();
        this.createdBy = facilityDocument.getCreatedBy();
    }

    public FacilityDocumentDTO(CftSupportingDoc cftSupportingDoc, Integer facilityID) {

        this.facilityID = facilityID;
        if (cftSupportingDoc.getSupportingDoc() != null) {
            this.supportingDocID = cftSupportingDoc.getSupportingDoc().getSupportingDocID();
            this.supportingDocumentName = cftSupportingDoc.getSupportingDoc().getDocumentName();
        }
        cftSupportDocID = cftSupportingDoc.getCftSupportingDocID();
        this.mandatory = cftSupportingDoc.getMandatory();
        this.status = AppsConstants.Status.ACT;
    }

    public Integer getFacilityDocumentID() {
        return facilityDocumentID;
    }

    public void setFacilityDocumentID(Integer facilityDocumentID) {
        this.facilityDocumentID = facilityDocumentID;
    }

    public Integer getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(Integer facilityID) {
        this.facilityID = facilityID;
    }

    public Integer getCftSupportDocID() {
        return cftSupportDocID;
    }

    public void setCftSupportDocID(Integer cftSupportDocID) {
        this.cftSupportDocID = cftSupportDocID;
    }

    public Integer getSupportingDocID() {
        return supportingDocID;
    }

    public void setSupportingDocID(Integer supportingDocID) {
        this.supportingDocID = supportingDocID;
    }

    public DocStorageDTO getDocStorageDTO() {
        return docStorageDTO;
    }

    public void setDocStorageDTO(DocStorageDTO docStorageDTO) {
        this.docStorageDTO = docStorageDTO;
    }

    public AppsConstants.YesNo getMandatory() {
        return mandatory;
    }

    public void setMandatory(AppsConstants.YesNo mandatory) {
        this.mandatory = mandatory;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
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

    public String getSupportingDocumentName() {
        return supportingDocumentName;
    }

    public void setSupportingDocumentName(String supportingDocumentName) {
        this.supportingDocumentName = supportingDocumentName;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}

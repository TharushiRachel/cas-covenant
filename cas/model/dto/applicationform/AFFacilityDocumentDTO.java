package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.applicationform.AFFacilityDocument;
import com.itechro.cas.model.dto.storage.DocStorageDTO;

import java.io.Serializable;

public class AFFacilityDocumentDTO implements Serializable {

    private Integer afFacilityDocumentID;

    private Integer applicationFormID;

    private Integer afFacilityID;

    private Integer cftSupportDocID;

    private Integer supportingDocID;

    private DocStorageDTO docStorageDTO;

    private AppsConstants.YesNo mandatory;

    private Integer displayOrder;

    private String remark;

    private String supportingDocumentName;

    private AppsConstants.Status status;

    private String createdBy;

    public AFFacilityDocumentDTO() {
    }

    public AFFacilityDocumentDTO(AFFacilityDocument afFacilityDocument) {
        this(afFacilityDocument, true);
    }

    public AFFacilityDocumentDTO(AFFacilityDocument afFacilityDocument, boolean loadDoc) {
        this.afFacilityDocumentID = afFacilityDocument.getAfFacilityDocumentID();
        this.afFacilityID = afFacilityDocument.getAfFacility().getFacilityID();
        if (afFacilityDocument.getSupportingDoc() != null) {
            this.supportingDocID = afFacilityDocument.getSupportingDoc().getSupportingDocID();
            this.supportingDocumentName = afFacilityDocument.getSupportingDoc().getDocumentName();
        }
        this.cftSupportDocID = afFacilityDocument.getCftSupportDocID();
        if (afFacilityDocument.getDocStorage() != null) {
            this.docStorageDTO = new DocStorageDTO(afFacilityDocument.getDocStorage(), loadDoc);
        }
        this.mandatory = afFacilityDocument.getMandatory();
        this.displayOrder = afFacilityDocument.getDisplayOrder();
        this.remark = afFacilityDocument.getRemark();
        this.status = afFacilityDocument.getStatus();
        this.createdBy = afFacilityDocument.getCreatedBy();
    }

    public Integer getAfFacilityDocumentID() {
        return afFacilityDocumentID;
    }

    public void setAfFacilityDocumentID(Integer afFacilityDocumentID) {
        this.afFacilityDocumentID = afFacilityDocumentID;
    }

    public Integer getAfFacilityID() {
        return afFacilityID;
    }

    public void setAfFacilityID(Integer afFacilityID) {
        this.afFacilityID = afFacilityID;
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

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "AFFacilityDocumentDTO{" +
                "afFacilityDocumentID=" + afFacilityDocumentID +
                ", applicationFormID=" + applicationFormID +
                ", afFacilityID=" + afFacilityID +
                ", cftSupportDocID=" + cftSupportDocID +
                ", supportingDocID=" + supportingDocID +
                ", mandatory=" + mandatory +
                ", displayOrder=" + displayOrder +
                ", remark='" + remark + '\'' +
                ", supportingDocumentName='" + supportingDocumentName + '\'' +
                ", status=" + status +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}

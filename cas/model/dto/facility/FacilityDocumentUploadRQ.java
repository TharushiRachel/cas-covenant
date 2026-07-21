package com.itechro.cas.model.dto.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.dto.storage.DocStorageDTO;

import java.io.Serializable;

public class FacilityDocumentUploadRQ implements Serializable {

    private Integer facilityDocumentID;

    private Integer facilityPaperID;

    private Integer facilityID;

    private Integer supportingDocID;

    private Integer cftSupportingDocID;

    private DocStorageDTO docStorageDTO;

    private AppsConstants.YesNo mandatory;

    private String remark;

    private AppsConstants.Status status;

    public Integer getFacilityDocumentID() {
        return facilityDocumentID;
    }

    public void setFacilityDocumentID(Integer facilityDocumentID) {
        this.facilityDocumentID = facilityDocumentID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public Integer getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(Integer facilityID) {
        this.facilityID = facilityID;
    }

    public Integer getSupportingDocID() {
        return supportingDocID;
    }

    public void setSupportingDocID(Integer supportingDocID) {
        this.supportingDocID = supportingDocID;
    }

    public Integer getCftSupportingDocID() {
        return cftSupportingDocID;
    }

    public void setCftSupportingDocID(Integer cftSupportingDocID) {
        this.cftSupportingDocID = cftSupportingDocID;
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

    public AppsConstants.YesNo getMandatory() {
        return mandatory;
    }

    public void setMandatory(AppsConstants.YesNo mandatory) {
        this.mandatory = mandatory;
    }
}

package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.dto.storage.DocStorageDTO;

import java.io.Serializable;

public class FPDocumentUploadRQ implements Serializable {

    private Integer fpDocumentID;

    private Integer facilityPaperID;

    private Integer supportingDocID;

    private DocStorageDTO docStorageDTO;

    private String remark;

    private String uploadedUserDisplayName;

    private String uploadedDivCode;

    private AppsConstants.Status status;

    public Integer getFpDocumentID() {
        return fpDocumentID;
    }

    public void setFpDocumentID(Integer fpDocumentID) {
        this.fpDocumentID = fpDocumentID;
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
}

package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.dto.storage.DocStorageDTO;

import java.io.Serializable;

public class FPCustomerDocumentUploadRQ implements Serializable {

    private Integer casCustomerID;

    private Integer fpCustomerDocumentID;

    private Integer facilityPaperID;

    private Integer supportingDocID;

    private DocStorageDTO docStorageDTO;

    private String remark;

    private AppsConstants.Status status;

    public Integer getCasCustomerID() {
        return casCustomerID;
    }

    public void setCasCustomerID(Integer casCustomerID) {
        this.casCustomerID = casCustomerID;
    }

    public Integer getFpCustomerDocumentID() {
        return fpCustomerDocumentID;
    }

    public void setFpCustomerDocumentID(Integer fpCustomerDocumentID) {
        this.fpCustomerDocumentID = fpCustomerDocumentID;
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
}

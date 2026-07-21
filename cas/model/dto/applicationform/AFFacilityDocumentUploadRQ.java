package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.dto.storage.DocStorageDTO;

import java.io.Serializable;

public class AFFacilityDocumentUploadRQ implements Serializable {

    private Integer facilityDocumentID;

    private Integer applicationFormID;

    private Integer afFacilityID;

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

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    public Integer getAfFacilityID() {
        return afFacilityID;
    }

    public void setAfFacilityID(Integer afFacilityID) {
        this.afFacilityID = afFacilityID;
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

    public AppsConstants.YesNo getMandatory() {
        return mandatory;
    }

    public void setMandatory(AppsConstants.YesNo mandatory) {
        this.mandatory = mandatory;
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

    @Override
    public String toString() {
        return "AFFacilityDocumentUploadRQ{" +
                "facilityDocumentID=" + facilityDocumentID +
                ", applicationFormID=" + applicationFormID +
                ", facilityID=" + afFacilityID +
                ", supportingDocID=" + supportingDocID +
                ", cftSupportingDocID=" + cftSupportingDocID +
                ", docStorageDTO=" + docStorageDTO +
                ", mandatory=" + mandatory +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                '}';
    }
}

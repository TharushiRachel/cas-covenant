package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.dto.storage.DocStorageDTO;

import java.io.Serializable;

public class AFDocumentUploadRQ implements Serializable {

    private Integer afDocumentID;

    private Integer applicationFormID;

    private Integer supportingDocID;

    private DocStorageDTO docStorageDTO;

    private String remark;

    private String uploadedUserDisplayName;

    private String uploadedDivCode;

    private AppsConstants.Status status;

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

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AFDocumentUploadRQ{" +
                "afDocumentID=" + afDocumentID +
                ", applicationFormID=" + applicationFormID +
                ", supportingDocID=" + supportingDocID +
                ", remark='" + remark + '\'' +
                ", uploadedUserDisplayName='" + uploadedUserDisplayName + '\'' +
                ", uploadedDivCode='" + uploadedDivCode + '\'' +
                ", status=" + status +
                '}';
    }
}

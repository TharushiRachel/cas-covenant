package com.itechro.cas.model.dto.lead;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.dto.storage.DocStorageDTO;

import java.io.Serializable;

public class LeadDocumentUploadRQ implements Serializable {

    private Integer leadDocumentID;

    private Integer leadID;

    private Integer supportingDocID;

    private DocStorageDTO docStorageDTO;

    private String remark;

    private String uploadedUserDisplayName;

    private String uploadedDivCode;

    private AppsConstants.Status status;

    public Integer getLeadDocumentID() {
        return leadDocumentID;
    }

    public void setLeadDocumentID(Integer leadDocumentID) {
        this.leadDocumentID = leadDocumentID;
    }

    public Integer getLeadID() {
        return leadID;
    }

    public void setLeadID(Integer leadID) {
        this.leadID = leadID;
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

    @Override
    public String toString() {
        return "LeadDocumentUploadRQ{" +
                "leadDocumentID=" + leadDocumentID +
                ", leadID=" + leadID +
                ", supportingDocID=" + supportingDocID +
                ", remark='" + remark + '\'' +
                ", uploadedUserDisplayName='" + uploadedUserDisplayName + '\'' +
                ", uploadedDivCode='" + uploadedDivCode + '\'' +
                ", status=" + status +
                '}';
    }
}

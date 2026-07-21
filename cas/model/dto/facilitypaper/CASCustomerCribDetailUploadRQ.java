package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.dto.storage.DocStorageDTO;

import java.io.Serializable;
import java.util.Date;

public class CASCustomerCribDetailUploadRQ implements Serializable {

    private Integer casCustomerID;

    private Integer casCustomerCribDetailsID;

    private Integer facilityPaperID;

    private Integer supportingDocID;

    private String documentName;

    private DocStorageDTO docStorageDTO;

    private DomainConstants.CribStatusType cribStatus;

    private Date cribIssueDate;

    private String cribIssueDateStr;

    private String remarks;

    private String uploadedUserDisplayName;

    private String uploadedDivCode;

    private AppsConstants.Status status;

    public Integer getCasCustomerID() {
        return casCustomerID;
    }

    public void setCasCustomerID(Integer casCustomerID) {
        this.casCustomerID = casCustomerID;
    }

    public Integer getCasCustomerCribDetailsID() {
        return casCustomerCribDetailsID;
    }

    public void setCasCustomerCribDetailsID(Integer casCustomerCribDetailsID) {
        this.casCustomerCribDetailsID = casCustomerCribDetailsID;
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

    public DomainConstants.CribStatusType getCribStatus() {
        return cribStatus;
    }

    public void setCribStatus(DomainConstants.CribStatusType cribStatus) {
        this.cribStatus = cribStatus;
    }

    public Date getCribIssueDate() {
        return cribIssueDate;
    }

    public void setCribIssueDate(Date cribIssueDate) {
        this.cribIssueDate = cribIssueDate;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getCribIssueDateStr() {
        return cribIssueDateStr;
    }

    public void setCribIssueDateStr(String cribIssueDateStr) {
        this.cribIssueDateStr = cribIssueDateStr;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

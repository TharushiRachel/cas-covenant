package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.facilitypaper.CASCustomerCribDetail;
import com.itechro.cas.model.dto.storage.DocStorageDTO;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.util.Date;

public class CASCustomerCribDetailDTO implements Serializable {

    private Integer casCustomerCribDetailsID;

    private Integer casCustomerID;

    private Integer supportingDocID;

    private String documentName;

    private Integer facilityPaperID;

    private DomainConstants.CribStatusType cribStatus;

    private Date cribIssueDate;

    private String cribIssueDateStr;

    private String createdDateStr;

    private String uploadedUserDisplayName;

    private String uploadedDivCode;

    private DocStorageDTO docStorageDTO;

    private String remark;

    private AppsConstants.Status status;

    private String createdBy;

    public CASCustomerCribDetailDTO() {
    }

    public CASCustomerCribDetailDTO(CASCustomerCribDetail CASCustomerCribDetail) {
        this(CASCustomerCribDetail, true);
    }

    public CASCustomerCribDetailDTO(CASCustomerCribDetail CASCustomerCribDetail, boolean loadDoc) {
        this.casCustomerCribDetailsID = CASCustomerCribDetail.getCasCustomerCribDetailsID();
        this.casCustomerID = CASCustomerCribDetail.getCASCustomer().getCasCustomerID();
        this.cribStatus = CASCustomerCribDetail.getCribStatus();
        if (CASCustomerCribDetail.getCribIssueDate() != null) {
            this.cribIssueDateStr = CalendarUtil.getDefaultFormattedDateTimeOnly(CASCustomerCribDetail.getCribIssueDate());
        }
        if (CASCustomerCribDetail.getCreatedDate() != null) {
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTimeOnly(CASCustomerCribDetail.getCreatedDate());
        }
        if (CASCustomerCribDetail.getDocStorage() != null) {
            this.docStorageDTO = new DocStorageDTO(CASCustomerCribDetail.getDocStorage(), loadDoc);
        }
        this.remark = CASCustomerCribDetail.getRemark();
        this.status = CASCustomerCribDetail.getStatus();
        this.createdBy = CASCustomerCribDetail.getCreatedBy();
        this.uploadedDivCode = CASCustomerCribDetail.getUploadedDivCode();
        this.uploadedUserDisplayName = CASCustomerCribDetail.getUploadedUserDisplayName();
        //TODO
        if (CASCustomerCribDetail.getSupportingDoc() != null) {
            this.supportingDocID = CASCustomerCribDetail.getSupportingDoc().getSupportingDocID();
            this.documentName = CASCustomerCribDetail.getSupportingDoc().getDocumentName();
        }
        this.facilityPaperID = CASCustomerCribDetail.getCASCustomer().getFacilityPaper().getFacilityPaperID();
    }

    public Integer getCasCustomerCribDetailsID() {
        return casCustomerCribDetailsID;
    }

    public void setCasCustomerCribDetailsID(Integer casCustomerCribDetailsID) {
        this.casCustomerCribDetailsID = casCustomerCribDetailsID;
    }

    public Integer getCasCustomerID() {
        return casCustomerID;
    }

    public void setCasCustomerID(Integer casCustomerID) {
        this.casCustomerID = casCustomerID;
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

    public DocStorageDTO getDocStorageDTO() {
        return docStorageDTO;
    }

    public void setDocStorageDTO(DocStorageDTO docStorageDTO) {
        this.docStorageDTO = docStorageDTO;
    }

    public Integer getSupportingDocID() {
        return supportingDocID;
    }

    public void setSupportingDocID(Integer supportingDocID) {
        this.supportingDocID = supportingDocID;
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

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    @Override
    public String toString() {
        return "CASCustomerCribDetailDTO{" +
                "casCustomerCribDetailsID=" + casCustomerCribDetailsID +
                ", casCustomerID=" + casCustomerID +
                ", supportingDocID=" + supportingDocID +
                ", documentName='" + documentName + '\'' +
                ", facilityPaperID=" + facilityPaperID +
                ", cribStatus=" + cribStatus +
                ", cribIssueDate=" + cribIssueDate +
                ", cribIssueDateStr='" + cribIssueDateStr + '\'' +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", uploadedUserDisplayName='" + uploadedUserDisplayName + '\'' +
                ", uploadedDivCode='" + uploadedDivCode + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}

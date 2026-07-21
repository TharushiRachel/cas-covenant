package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
//import com.itechro.cas.model.domain.casmaster.GlobalSupportingDoc;
import com.itechro.cas.model.domain.casmaster.SupportingDoc;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class GlobalSupportingDocDTO implements Serializable {

    private Integer supportingDocID;

    private String documentName;

    private String description;

    private String supportDocumentType;

    private AppsConstants.Status status;

    private DomainConstants.MasterDataApproveStatus approveStatus;

    private String approvedDateStr;

    private String approvedBy;

    private String createdDateStr;

    private String createdBy;

    private String modifiedDateStr;

    private String modifiedBy;

    private Integer categoryID;

    private String categoryName;

    public GlobalSupportingDocDTO() {
    }

//    public GlobalSupportingDocDTO(GlobalSupportingDoc globalSupportingDoc) {
//        this.supportingDocID = globalSupportingDoc.getSupportingDocID();
//        this.documentName = globalSupportingDoc.getDocumentName();
//        this.description = globalSupportingDoc.getDescription();
//        this.supportDocumentType = globalSupportingDoc.getSupportDocumentType();
//        this.status = globalSupportingDoc.getStatus();
//
//        if (globalSupportingDoc.getApprovedDate() != null) {
//            this.approvedDateStr = CalendarUtil.getDefaultFormattedDateTime(globalSupportingDoc.getApprovedDate());
//        }
//        this.approveStatus = globalSupportingDoc.getApproveStatus();
//        this.approvedBy = globalSupportingDoc.getApprovedBy();
//        if(globalSupportingDoc.getCreatedDate() != null){
//            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(globalSupportingDoc.getCreatedDate());
//        }
//        this.createdBy = globalSupportingDoc.getCreatedBy();
//        if(globalSupportingDoc.getModifiedDate() != null){
//            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(globalSupportingDoc.getModifiedDate());
//        }
//        this.modifiedBy = globalSupportingDoc.getModifiedBy();
//        this.categoryID = globalSupportingDoc.getCategoryID();
//        this.categoryName = globalSupportingDoc.getCategoryName();
//    }

    public Integer getSupportingDocID() {
        return supportingDocID;
    }

    public void setSupportingDocID(Integer supportingDocID) {
        this.supportingDocID = supportingDocID;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupportDocumentType() {
        return supportDocumentType;
    }

    public void setSupportDocumentType(String supportDocumentType) {
        this.supportDocumentType = supportDocumentType;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public DomainConstants.MasterDataApproveStatus getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(DomainConstants.MasterDataApproveStatus approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApprovedDateStr() {
        return approvedDateStr;
    }

    public void setApprovedDateStr(String approvedDateStr) {
        this.approvedDateStr = approvedDateStr;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedDateStr() {
        return modifiedDateStr;
    }

    public void setModifiedDateStr(String modifiedDateStr) {
        this.modifiedDateStr = modifiedDateStr;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "GlobalSupportingDocDTO{" +
                "supportingDocID=" + supportingDocID +
                ", documentName='" + documentName + '\'' +
                ", description='" + description + '\'' +
                ", supportDocumentType='" + supportDocumentType + '\'' +
                ", status=" + status +
                ", approveStatus=" + approveStatus +
                ", approvedDateStr='" + approvedDateStr + '\'' +
                ", approvedBy='" + approvedBy + '\'' +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedDateStr='" + modifiedDateStr + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", categoryID=" + categoryID +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}

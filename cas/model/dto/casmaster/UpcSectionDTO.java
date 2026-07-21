package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.UpcSection;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class UpcSectionDTO implements Serializable {

    private Integer upcSectionID;

    private String upcSectionName;

    private String upcSectionDescription;

    private AppsConstants.Status status;

    private DomainConstants.MasterDataApproveStatus approveStatus;

    private String approvedDateStr;

    private String approvedBy;

    private String createdDateStr;

    private String createdBy;

    private String modifiedDateStr;

    private String modifiedBy;


    public UpcSectionDTO() {
    }

    public UpcSectionDTO(UpcSection upcSection) {
        this.upcSectionID = upcSection.getUpcSectionID();
        this.upcSectionName = upcSection.getUpcSectionName();
        this.upcSectionDescription = upcSection.getUpcSectionDescription();
        this.status = upcSection.getStatus();
        this.approveStatus = upcSection.getApproveStatus();
        if (upcSection.getApprovedDate() != null) {
            this.approvedDateStr = CalendarUtil.getDefaultFormattedDateTime(upcSection.getApprovedDate());
        }
        this.approvedBy = upcSection.getApprovedBy();
        if(upcSection.getCreatedDate() != null){
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(upcSection.getCreatedDate());
        }
        this.createdBy = upcSection.getCreatedBy();
        if(upcSection.getModifiedDate() != null){
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(upcSection.getModifiedDate());
        }
        this.modifiedBy = upcSection.getModifiedBy();

    }

    public Integer getUpcSectionID() {
        return upcSectionID;
    }

    public void setUpcSectionID(Integer upcSectionID) {
        this.upcSectionID = upcSectionID;
    }

    public String getUpcSectionName() {
        return upcSectionName;
    }

    public void setUpcSectionName(String upcSectionName) {
        this.upcSectionName = upcSectionName;
    }

    public String getUpcSectionDescription() {
        return upcSectionDescription;
    }

    public void setUpcSectionDescription(String upcSectionDescription) {
        this.upcSectionDescription = upcSectionDescription;
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

    @Override
    public String toString() {
        return "UpcSectionDTO{" +
                "upcSectionID=" + upcSectionID +
                ", upcSectionName='" + upcSectionName + '\'' +
                ", upcSectionDescription='" + upcSectionDescription + '\'' +
                ", status=" + status +
                '}';
    }
}

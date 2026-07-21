package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.CreditFacilityType;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class CreditFacilityTypeDTO implements Serializable {

    private Integer creditFacilityTypeID;

    private String facilityTypeName;

    private String description;

    private AppsConstants.Status status;

    private DomainConstants.MasterDataApproveStatus approveStatus;

    private String approvedDateStr;

    private String approvedBy;

    private String createdDateStr;

    private String createdBy;

    private String modifiedDateStr;

    private String modifiedBy;

    public CreditFacilityTypeDTO(){}

    public CreditFacilityTypeDTO(CreditFacilityType creditFacilityType){
        this.creditFacilityTypeID = creditFacilityType.getCreditFacilityTypeID();
        this.facilityTypeName = creditFacilityType.getFacilityTypeName();
        this.description = creditFacilityType.getDescription();
        this.status = creditFacilityType.getStatus();

        if (creditFacilityType.getApprovedDate() != null) {
            this.approvedDateStr = CalendarUtil.getDefaultFormattedDateTime(creditFacilityType.getApprovedDate());
        }
        this.approvedBy = creditFacilityType.getApprovedBy();
        this.approveStatus = creditFacilityType.getApproveStatus();
        if(creditFacilityType.getCreatedDate() != null){
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(creditFacilityType.getCreatedDate());
        }
        this.createdBy = creditFacilityType.getCreatedBy();
        if(creditFacilityType.getModifiedDate() != null){
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(creditFacilityType.getModifiedDate());
        }
        this.modifiedBy = creditFacilityType.getModifiedBy();

    }

    public Integer getCreditFacilityTypeID() {
        return creditFacilityTypeID;
    }

    public void setCreditFacilityTypeID(Integer creditFacilityTypeID) {
        this.creditFacilityTypeID = creditFacilityTypeID;
    }

    public String getFacilityTypeName() {
        return facilityTypeName;
    }

    public void setFacilityTypeName(String facilityTypeName) {
        this.facilityTypeName = facilityTypeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "CreditFacilityTypeDTO{" +
                "creditFacilityTypeID=" + creditFacilityTypeID +
                ", facilityTypeName='" + facilityTypeName + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", approveStatus=" + approveStatus +
                ", approvedDateStr='" + approvedDateStr + '\'' +
                ", approvedBy='" + approvedBy + '\'' +
                '}';
    }
}

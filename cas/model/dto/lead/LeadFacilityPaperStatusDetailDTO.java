package com.itechro.cas.model.dto.lead;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;

import java.io.Serializable;

public class LeadFacilityPaperStatusDetailDTO implements Serializable {

    private String actionMessage;

    private String updateBy;

    private String updateDate;

    private String updatedUser;

    private String assignDepartmentCode;

    private String assignUserDisplayName;

    private Integer assignUserID;

    private AppsConstants.YesNo isPublic;

    private AppsConstants.YesNo isDivisionOnly;

    private AppsConstants.YesNo isUsersOnly;

    private DomainConstants.FacilityPaperStatus facilityPaperStatus;

    public LeadFacilityPaperStatusDetailDTO() {
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public DomainConstants.FacilityPaperStatus getFacilityPaperStatus() {
        return facilityPaperStatus;
    }

    public void setFacilityPaperStatus(DomainConstants.FacilityPaperStatus facilityPaperStatus) {
        this.facilityPaperStatus = facilityPaperStatus;
    }

    public Integer getAssignUserID() {
        return assignUserID;
    }

    public void setAssignUserID(Integer assignUserID) {
        this.assignUserID = assignUserID;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    public AppsConstants.YesNo getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(AppsConstants.YesNo isPublic) {
        this.isPublic = isPublic;
    }

    public AppsConstants.YesNo getIsDivisionOnly() {
        return isDivisionOnly;
    }

    public void setIsDivisionOnly(AppsConstants.YesNo isDivisionOnly) {
        this.isDivisionOnly = isDivisionOnly;
    }

    public AppsConstants.YesNo getIsUsersOnly() {
        return isUsersOnly;
    }

    public void setIsUsersOnly(AppsConstants.YesNo isUsersOnly) {
        this.isUsersOnly = isUsersOnly;
    }

    public String getAssignDepartmentCode() {
        return assignDepartmentCode;
    }

    public void setAssignDepartmentCode(String assignDepartmentCode) {
        this.assignDepartmentCode = assignDepartmentCode;
    }

    public String getAssignUserDisplayName() {
        return assignUserDisplayName;
    }

    public void setAssignUserDisplayName(String assignUserDisplayName) {
        this.assignUserDisplayName = assignUserDisplayName;
    }


    @Override
    public String toString() {
        return "LeadFacilityPaperStatusDetailDTO{" +
                "actionMessage='" + actionMessage + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", updatedUser='" + updatedUser + '\'' +
                ", assignDepartmentCode='" + assignDepartmentCode + '\'' +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                ", assignUserID=" + assignUserID +
                ", isPublic=" + isPublic +
                ", isDivisionOnly=" + isDivisionOnly +
                ", isUsersOnly=" + isUsersOnly +
                ", facilityPaperStatus=" + facilityPaperStatus +
                '}';
    }
}

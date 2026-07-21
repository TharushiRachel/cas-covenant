package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.facilitypaper.FPComment;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class FPCommentDTO implements Serializable {

    private Integer fpCommentID;

    private Integer facilityPaperID;

    private String comment;

    private Integer createdUserID;

    private String createdUser;

    private String createdUserDisplayName;

    private String createdUserDivCode;

    private String createdUserUpmCode;

    private Integer assignedUserID;

    private String assignedUser;

    private String assignedUserDisplayName;

    private String assignedUserDivCode;

    private String actionMessage;

    private AppsConstants.YesNo isPublic;

    private AppsConstants.YesNo isDivisionOnly;

    private AppsConstants.YesNo isUsersOnly;

    private DomainConstants.FacilityPaperStatus currentFacilityPaperStatus;

    private AppsConstants.Status status;

    private String createdDateStr;

    private String createdBy;

    private String modifiedDateStr;

    private String modifiedBy;

    public FPCommentDTO() {
    }

    public FPCommentDTO(FPComment fpComment) {
        this.fpCommentID = fpComment.getFpCommentID();
        this.facilityPaperID = fpComment.getFacilityPaper().getFacilityPaperID();
        this.comment = fpComment.getComment();
        this.createdUser = fpComment.getCreatedUser();
        this.createdUserID = fpComment.getCreatedUserID();
        this.createdUserDivCode = fpComment.getCreatedUserDivCode();
        this.createdUserUpmCode = fpComment.getCreatedUserUpmCode();
        this.createdUserDisplayName = fpComment.getCreatedUserDisplayName();
        this.assignedUser = fpComment.getAssignedUser();
        this.assignedUserID = fpComment.getAssignedUserID();
        this.assignedUserDivCode = fpComment.getAssignedUserDivCode();
        this.assignedUserDisplayName = fpComment.getAssignedUserDisplayName();
        this.actionMessage = fpComment.getActionMessage();
        this.isPublic = fpComment.getIsPublic();
        this.isDivisionOnly = fpComment.getIsDivisionOnly();
        this.isUsersOnly = fpComment.getIsUsersOnly();
        this.currentFacilityPaperStatus = fpComment.getCurrentFacilityPaperStatus();
        this.status = fpComment.getStatus();
        if (fpComment.getCreatedDate() != null) {
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(fpComment.getCreatedDate());
        }
        if (fpComment.getModifiedDate() != null) {
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(fpComment.getModifiedDate());
        }
    }

    public Integer getFpCommentID() {
        return fpCommentID;
    }

    public void setFpCommentID(Integer fpCommentID) {
        this.fpCommentID = fpCommentID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getCreatedUserID() {
        return createdUserID;
    }

    public void setCreatedUserID(Integer createdUserID) {
        this.createdUserID = createdUserID;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getCreatedUserDisplayName() {
        return createdUserDisplayName;
    }

    public void setCreatedUserDisplayName(String createdUserDisplayName) {
        this.createdUserDisplayName = createdUserDisplayName;
    }

    public String getCreatedUserDivCode() {
        return createdUserDivCode;
    }

    public void setCreatedUserDivCode(String createdUserDivCode) {
        this.createdUserDivCode = createdUserDivCode;
    }

    public String getAssignedUserDivCode() {
        return assignedUserDivCode;
    }

    public void setAssignedUserDivCode(String assignedUserDivCode) {
        this.assignedUserDivCode = assignedUserDivCode;
    }

    public String getCreatedUserUpmCode() {
        return createdUserUpmCode;
    }

    public void setCreatedUserUpmCode(String createdUserUpmCode) {
        this.createdUserUpmCode = createdUserUpmCode;
    }

    public Integer getAssignedUserID() {
        return assignedUserID;
    }

    public void setAssignedUserID(Integer assignedUserID) {
        this.assignedUserID = assignedUserID;
    }

    public String getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
    }

    public String getAssignedUserDisplayName() {
        return assignedUserDisplayName;
    }

    public void setAssignedUserDisplayName(String assignedUserDisplayName) {
        this.assignedUserDisplayName = assignedUserDisplayName;
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
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

    public DomainConstants.FacilityPaperStatus getCurrentFacilityPaperStatus() {
        return currentFacilityPaperStatus;
    }

    public void setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus currentFacilityPaperStatus) {
        this.currentFacilityPaperStatus = currentFacilityPaperStatus;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
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
        return "FPCommentDTO{" +
                "fpCommentID=" + fpCommentID +
                ", facilityPaperID=" + facilityPaperID +
                ", comment='" + comment + '\'' +
                ", createdUserID=" + createdUserID +
                ", createdUser='" + createdUser + '\'' +
                ", createdUserDisplayName='" + createdUserDisplayName + '\'' +
                ", createdUserDivCode='" + createdUserDivCode + '\'' +
                ", createdUserUpmCode='" + createdUserUpmCode + '\'' +
                ", assignedUserID=" + assignedUserID +
                ", assignedUser='" + assignedUser + '\'' +
                ", assignedUserDisplayName='" + assignedUserDisplayName + '\'' +
                ", assignedUserDivCode='" + assignedUserDivCode + '\'' +
                ", actionMessage='" + actionMessage + '\'' +
                ", isPublic=" + isPublic +
                ", isDivisionOnly=" + isDivisionOnly +
                ", isUsersOnly=" + isUsersOnly +
                ", currentFacilityPaperStatus=" + currentFacilityPaperStatus +
                ", status=" + status +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedDateStr='" + modifiedDateStr + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                '}';
    }
}

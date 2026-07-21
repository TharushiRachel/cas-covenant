package com.itechro.cas.model.dto.lead;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.lead.LeadComment;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class LeadCommentDTO implements Serializable {

    private Integer commentID;

    private Integer leadID;

    private String comment;

    private String actionMessage;

    private String createdUserDisplayName;

    private Integer createdUserID;

    private String createdUser;

    private String createdUserDivCode;

    private String createdUserUpmCode;

    private Integer assignedUserID;

    private String assignedUser;

    private String assignedUserDisplayName;

    private String assignedUserDivCode;

    private String assignDepartmentCode;

    private AppsConstants.YesNo isUsersOnly;

    private AppsConstants.YesNo isDivisionOnly;

    private AppsConstants.YesNo isPublic;

    private AppsConstants.Status status;

    private DomainConstants.LeadStatus currentLeadStatus;

    private String createdDateStr;

    private String createdBy;

    private String modifiedDateStr;

    private String modifiedBy;

    public LeadCommentDTO() {
    }

    public LeadCommentDTO(LeadComment leadComment) {
        this.commentID = leadComment.getCommentID();
        this.leadID = leadComment.getLead().getLeadID();
        this.comment = leadComment.getUserComment();
        this.actionMessage = leadComment.getActionMessage();
        this.createdUserDisplayName = leadComment.getCreatedUserDisplayName();
        this.createdUserID = leadComment.getCreatedUserID();
        this.createdUser = leadComment.getCreatedUser();
        this.createdUserDivCode = leadComment.getCreatedUserDivCode();
        this.createdUserUpmCode = leadComment.getCreatedUserUpmCode();
        this.assignedUserID = leadComment.getAssignedUserID();
        this.assignedUser = leadComment.getAssignedUser();
        this.assignedUserDisplayName = leadComment.getAssignedUserDisplayName();
        this.assignedUserDivCode = leadComment.getAssignedUserDivCode();
        this.assignDepartmentCode = leadComment.getAssignDepartmentCode();
        this.isUsersOnly = leadComment.getIsUsersOnly();
        this.isDivisionOnly = leadComment.getIsDivisionOnly();
        this.isPublic = leadComment.getIsPublic();
        this.status = leadComment.getStatus();
        this.currentLeadStatus = leadComment.getCurrentLeadStatus();
        if (leadComment.getCreatedDate() != null) {
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(leadComment.getCreatedDate());
        }
        if (leadComment.getModifiedDate() != null) {
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(leadComment.getModifiedDate());
        }
    }

    public Integer getCommentID() {
        return commentID;
    }

    public void setCommentID(Integer commentID) {
        this.commentID = commentID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedUserDisplayName() {
        return createdUserDisplayName;
    }

    public void setCreatedUserDisplayName(String createdUserDisplayName) {
        this.createdUserDisplayName = createdUserDisplayName;
    }

    public Integer getCreatedUserID() {
        return createdUserID;
    }

    public void setCreatedUserID(Integer createdUserID) {
        this.createdUserID = createdUserID;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public Integer getLeadID() {
        return leadID;
    }

    public void setLeadID(Integer leadID) {
        this.leadID = leadID;
    }

    public DomainConstants.LeadStatus getCurrentLeadStatus() {
        return currentLeadStatus;
    }

    public void setCurrentLeadStatus(DomainConstants.LeadStatus currentLeadStatus) {
        this.currentLeadStatus = currentLeadStatus;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
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

    public String getAssignDepartmentCode() {
        return assignDepartmentCode;
    }

    public void setAssignDepartmentCode(String assignDepartmentCode) {
        this.assignDepartmentCode = assignDepartmentCode;
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

    public AppsConstants.YesNo getIsUsersOnly() {
        return isUsersOnly;
    }

    public void setIsUsersOnly(AppsConstants.YesNo isUsersOnly) {
        this.isUsersOnly = isUsersOnly;
    }

    public AppsConstants.YesNo getIsDivisionOnly() {
        return isDivisionOnly;
    }

    public void setIsDivisionOnly(AppsConstants.YesNo isDivisionOnly) {
        this.isDivisionOnly = isDivisionOnly;
    }

    public AppsConstants.YesNo getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(AppsConstants.YesNo isPublic) {
        this.isPublic = isPublic;
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

    public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    public String getCreatedUserUpmCode() {
        return createdUserUpmCode;
    }

    public void setCreatedUserUpmCode(String createdUserUpmCode) {
        this.createdUserUpmCode = createdUserUpmCode;
    }

    @Override
    public String toString() {
        return "LeadCommentDTO{" +
                "commentID=" + commentID +
                ", leadID=" + leadID +
                ", comment='" + comment + '\'' +
                ", actionMessage='" + actionMessage + '\'' +
                ", createdUserDisplayName='" + createdUserDisplayName + '\'' +
                ", createdUserID=" + createdUserID +
                ", createdUser='" + createdUser + '\'' +
                ", createdUserDivCode='" + createdUserDivCode + '\'' +
                ", createdUserUpmCode='" + createdUserUpmCode + '\'' +
                ", assignedUserID=" + assignedUserID +
                ", assignedUser='" + assignedUser + '\'' +
                ", assignedUserDisplayName='" + assignedUserDisplayName + '\'' +
                ", assignedUserDivCode='" + assignedUserDivCode + '\'' +
                ", assignDepartmentCode='" + assignDepartmentCode + '\'' +
                ", isUsersOnly=" + isUsersOnly +
                ", isDivisionOnly=" + isDivisionOnly +
                ", isPublic=" + isPublic +
                ", status=" + status +
                ", currentLeadStatus=" + currentLeadStatus +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedDateStr='" + modifiedDateStr + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                '}';
    }
}

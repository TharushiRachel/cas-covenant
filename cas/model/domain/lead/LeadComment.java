package com.itechro.cas.model.domain.lead;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.lead.Lead;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_LEAD_COMMENT")
public class LeadComment extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_LEAD_COMMENT")
    @SequenceGenerator(name = "SEQ_T_LEAD_COMMENT", sequenceName = "SEQ_T_LEAD_COMMENT", allocationSize = 1)
    @Column(name = "LEAD_COMMENT_ID")
    private Integer commentID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEAD_ID")
    private Lead lead;

    @Column(name = "CREATED_USER_ID")
    private Integer createdUserID;

    @Column(name = "CREATED_USER")
    private String createdUser;

    @Column(name = "CREATED_USER_DISPLAY_NAME")
    private String createdUserDisplayName;

    @Column(name = "CREATED_USER_DIV_CODE")
    private String createdUserDivCode;

    @Column(name = "CREATED_USER_UPM_CODE")
    private String createdUserUpmCode;

    @Column(name = "ASSIGNED_USER_ID")
    private Integer assignedUserID;

    @Column(name = "ASSIGNED_USER")
    private String assignedUser;

    @Column(name = "ASSIGNED_USER_DISPLAY_NAME")
    private String assignedUserDisplayName;

    @Column(name = "ASSIGNED_USER_DIV_CODE")
    private String assignedUserDivCode;

    @Column(name = "ASSIGN_DEPARTMENT_CODE")
    private String assignDepartmentCode;

    @Column(name = "USER_COMMENT")
    private String userComment;

    @Column(name = "ACTION_MESSAGE")
    private String actionMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_PUBLIC")
    private AppsConstants.YesNo isPublic;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_DIVISION_ONLY")
    private AppsConstants.YesNo isDivisionOnly;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_USERS_ONLY")
    private AppsConstants.YesNo isUsersOnly;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENT_LEAD_STATUS")
    private DomainConstants.LeadStatus currentLeadStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getCommentID() {
        return commentID;
    }

    public void setCommentID(Integer commentID) {
        this.commentID = commentID;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
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

    public Lead getLead() {
        return lead;
    }

    public void setLead(Lead lead) {
        this.lead = lead;
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

    public String getCreatedUserUpmCode() {
        return createdUserUpmCode;
    }

    public void setCreatedUserUpmCode(String createdUserUpmCode) {
        this.createdUserUpmCode = createdUserUpmCode;
    }
}

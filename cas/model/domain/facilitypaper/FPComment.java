package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;

import javax.persistence.*;

@Entity
@Table(name = "T_FP_COMMENT")
public class FPComment extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_COMMENT")
    @SequenceGenerator(name = "SEQ_T_FP_COMMENT", sequenceName = "SEQ_T_FP_COMMENT", allocationSize = 1)
    @Column(name = "FP_COMMENT_ID")
    private Integer fpCommentID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @Column(name = "FP_COMMENT")
    private String comment;

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
    @Column(name = "CURRENT_FACILITY_PAPER_STATUS")
    private DomainConstants.FacilityPaperStatus currentFacilityPaperStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getFpCommentID() {
        return fpCommentID;
    }

    public void setFpCommentID(Integer fpCommentID) {
        this.fpCommentID = fpCommentID;
    }

    public FacilityPaper getFacilityPaper() {
        return facilityPaper;
    }

    public void setFacilityPaper(FacilityPaper facilityPaper) {
        this.facilityPaper = facilityPaper;
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
}

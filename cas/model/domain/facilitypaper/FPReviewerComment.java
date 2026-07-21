package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;

import javax.persistence.*;

@Entity
@Table(name = "T_FP_REVIEWER_COMMENT")
public class FPReviewerComment extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_REVIEWER_COMMENT")
    @SequenceGenerator(name = "SEQ_T_FP_REVIEWER_COMMENT", sequenceName = "SEQ_T_FP_REVIEWER_COMMENT", allocationSize = 1)
    @Column(name = "T_FP_REVIEWER_COMMENT_ID")
    private Integer fpReviewerCommentID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @Column(name = "FP_REVIEWER_COMMENT")
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @Column(name = "MODIFIED_USER_NAME")
    private String modifiedUserName;

    @Column(name = "CREATED_USER_DISPLAY_NAME")
    private String createdUserDisplayName;

    @Column(name = "CREATED_USER_DIV_CODE")
    private String createdUserDivCode;

    @Column(name = "CREATED_USER_UPM_CODE")
    private String createdUserUpmCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAPER_REVIEW_STATUS")
    private DomainConstants.PaperReviewStatus paperReviewStatus;

    @Column(name = "UPM_ID")
    private Integer upmID;

    public Integer getFpReviewerCommentID() {
        return fpReviewerCommentID;
    }

    public void setFpReviewerCommentID(Integer fpReviewerCommentID) {
        this.fpReviewerCommentID = fpReviewerCommentID;
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

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public String getModifiedUserName() {
        return modifiedUserName;
    }

    public void setModifiedUserName(String modifiedUserName) {
        this.modifiedUserName = modifiedUserName;
    }

    public Integer getUpmID() {
        return upmID;
    }

    public void setUpmID(Integer upmID) {
        this.upmID = upmID;
    }

    public DomainConstants.PaperReviewStatus getPaperReviewStatus() {
        return paperReviewStatus;
    }

    public void setPaperReviewStatus(DomainConstants.PaperReviewStatus paperReviewStatus) {
        this.paperReviewStatus = paperReviewStatus;
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

    public String getCreatedUserUpmCode() {
        return createdUserUpmCode;
    }

    public void setCreatedUserUpmCode(String createdUserUpmCode) {
        this.createdUserUpmCode = createdUserUpmCode;
    }
}

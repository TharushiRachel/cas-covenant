package com.itechro.cas.model.dto.facilitypaper.facilitypaperreview;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.facilitypaper.FPReviewerComment;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class FPReviewerCommentDTO implements Serializable {

    private Integer fpReviewerCommentID;

    private Integer facilityPaperID;

    private String comment;

    private AppsConstants.Status status;

    private String commentedTimeStr;

    private String createdUserDisplayName;

    private String modifiedUserName;

    private Integer upmID;

    private String createdUserDivCode;

    private String createdUserUpmCode;

    private DomainConstants.PaperReviewStatus paperReviewStatus;

    public FPReviewerCommentDTO() {
    }

    public FPReviewerCommentDTO(FPReviewerComment fpReviewerComment) {
        this.fpReviewerCommentID = fpReviewerComment.getFpReviewerCommentID();
        this.facilityPaperID = fpReviewerComment.getFacilityPaper().getFacilityPaperID();
        this.comment = fpReviewerComment.getComment();
        this.status = fpReviewerComment.getStatus();
        this.commentedTimeStr = CalendarUtil.getDefaultFormattedDateTime(fpReviewerComment.getCreatedDate());
        this.paperReviewStatus = fpReviewerComment.getPaperReviewStatus();
        if (fpReviewerComment.getCreatedUserDisplayName() != null) {
            this.createdUserDisplayName = fpReviewerComment.getCreatedUserDisplayName();
        }
        if (fpReviewerComment.getModifiedUserName() != null) {
            this.modifiedUserName = fpReviewerComment.getModifiedUserName();
        }
        if (fpReviewerComment.getUpmID() != null) {
            this.upmID = fpReviewerComment.getUpmID();
        }
        if(fpReviewerComment.getCreatedUserDivCode() != null){
            this.createdUserDivCode = fpReviewerComment.getCreatedUserDivCode();
        }
        if(fpReviewerComment.getCreatedUserUpmCode() != null){
            this.createdUserUpmCode = fpReviewerComment.getCreatedUserUpmCode();
        }
    }

    public Integer getFpReviewerCommentID() {
        return fpReviewerCommentID;
    }

    public void setFpReviewerCommentID(Integer fpReviewerCommentID) {
        this.fpReviewerCommentID = fpReviewerCommentID;
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

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public String getCommentedTimeStr() {
        return commentedTimeStr;
    }

    public void setCommentedTimeStr(String commentedTimeStr) {
        this.commentedTimeStr = commentedTimeStr;
    }

    public String getCreatedUserDisplayName() {
        return createdUserDisplayName;
    }

    public void setCreatedUserDisplayName(String createdUserDisplayName) {
        this.createdUserDisplayName = createdUserDisplayName;
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

    @Override
    public String toString() {
        return "FPReviewerCommentDTO{" +
                "fpReviewerCommentID=" + fpReviewerCommentID +
                ", facilityPaperID=" + facilityPaperID +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                ", commentedTimeStr='" + commentedTimeStr + '\'' +
                ", createdUserDisplayName='" + createdUserDisplayName + '\'' +
                ", modifiedUserName='" + modifiedUserName + '\'' +
                ", upmID=" + upmID +
                ", createdUserDivCode='" + createdUserDivCode + '\'' +
                ", createdUserUpmCode='" + createdUserUpmCode + '\'' +
                ", paperReviewStatus=" + paperReviewStatus +
                '}';
    }
}

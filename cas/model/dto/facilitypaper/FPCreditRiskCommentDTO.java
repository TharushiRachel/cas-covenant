package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.FPCreditRiskComment;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class FPCreditRiskCommentDTO implements Serializable {

    private static final long serialVersionUID = -3250069475253035518L;

    private Integer fpCreditRiskCommentID;

    private Integer facilityPaperID;

    private String UPMPrivilegeCode;

    private String creditRiskComment;

    private String commentedTimeStr;

    private String createdUserName;

    private String modifiedUserName;

    private Integer upmID;

    private AppsConstants.YesNo isLocked;

    private AppsConstants.YesNo isValidComment;

    private AppsConstants.Status status;

    private String createdBy;

    private String modifiedBy;

    private String createdDateStr;

    private String modifiedDateStr;

    private FPCreditRiskReplyDTO fpCreditRiskReplyDTO; //Can have only one Comment so included only one Comment here

    private long version;

    public FPCreditRiskCommentDTO() {
    }

    public FPCreditRiskCommentDTO(FPCreditRiskComment fpCreditRiskComment) {
        this.fpCreditRiskCommentID = fpCreditRiskComment.getFpCreditRiskCommentID();
        if (fpCreditRiskComment.getFacilityPaper() != null) {
            this.facilityPaperID = fpCreditRiskComment.getFacilityPaper().getFacilityPaperID();
        }
        this.UPMPrivilegeCode = fpCreditRiskComment.getUPMPrivilegeCode();
        this.creditRiskComment = fpCreditRiskComment.getCreditRiskComment();
        this.status = fpCreditRiskComment.getStatus();
        this.commentedTimeStr = CalendarUtil.getDefaultFormattedDateTime(fpCreditRiskComment.getCreatedDate());
        this.createdUserName = fpCreditRiskComment.getCreatedUserName();
        this.modifiedUserName = fpCreditRiskComment.getModifiedUserName();
        this.version = fpCreditRiskComment.getVersion();

        if (fpCreditRiskComment.getUpmID() != null) {
            this.upmID = fpCreditRiskComment.getUpmID();
        }
        this.isLocked = fpCreditRiskComment.getIsLocked();
        this.isValidComment = fpCreditRiskComment.getIsValidComment();
        if (fpCreditRiskComment.getCreatedBy() != null) {
            this.createdBy = fpCreditRiskComment.getCreatedBy();
        }
        if (fpCreditRiskComment.getModifiedBy() != null) {
            this.modifiedBy = fpCreditRiskComment.getModifiedBy();
        }

        if (fpCreditRiskComment.getCreatedDate() != null) {
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(fpCreditRiskComment.getCreatedDate());
        }

        if (fpCreditRiskComment.getModifiedDate() != null) {
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(fpCreditRiskComment.getModifiedDate());
        }

        if (fpCreditRiskComment.getActiveFpCreditRiskReply().isPresent()) {
            this.fpCreditRiskReplyDTO = new FPCreditRiskReplyDTO(fpCreditRiskComment.getActiveFpCreditRiskReply().get());
        }
    }

    public Integer getFpCreditRiskCommentID() {
        return fpCreditRiskCommentID;
    }

    public void setFpCreditRiskCommentID(Integer fpCreditRiskCommentID) {
        this.fpCreditRiskCommentID = fpCreditRiskCommentID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public String getUPMPrivilegeCode() {
        return UPMPrivilegeCode;
    }

    public void setUPMPrivilegeCode(String UPMPrivilegeCode) {
        this.UPMPrivilegeCode = UPMPrivilegeCode;
    }

    public String getCreditRiskComment() {
        return creditRiskComment;
    }

    public void setCreditRiskComment(String creditRiskComment) {
        this.creditRiskComment = creditRiskComment;
    }

    public String getCommentedTimeStr() {
        return commentedTimeStr;
    }

    public void setCommentedTimeStr(String commentedTimeStr) {
        this.commentedTimeStr = commentedTimeStr;
    }

    public String getCreatedUserName() {
        return createdUserName;
    }

    public void setCreatedUserName(String createdUserName) {
        this.createdUserName = createdUserName;
    }

    public String getModifiedUserName() {
        return modifiedUserName;
    }

    public void setModifiedUserName(String modifiedUserName) {
        this.modifiedUserName = modifiedUserName;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public Integer getUpmID() {
        return upmID;
    }

    public void setUpmID(Integer upmID) {
        this.upmID = upmID;
    }

    public AppsConstants.YesNo getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(AppsConstants.YesNo isLocked) {
        this.isLocked = isLocked;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public String getModifiedDateStr() {
        return modifiedDateStr;
    }

    public void setModifiedDateStr(String modifiedDateStr) {
        this.modifiedDateStr = modifiedDateStr;
    }

    public AppsConstants.YesNo getIsValidComment() {
        return isValidComment;
    }

    public void setIsValidComment(AppsConstants.YesNo isValidComment) {
        this.isValidComment = isValidComment;
    }

    public FPCreditRiskReplyDTO getFpCreditRiskReplyDTO() {
        return fpCreditRiskReplyDTO;
    }

    public void setFpCreditRiskReplyDTO(FPCreditRiskReplyDTO fpCreditRiskReplyDTO) {
        this.fpCreditRiskReplyDTO = fpCreditRiskReplyDTO;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "FPCreditRiskCommentDTO{" +
                "fpCreditRiskCommentID=" + fpCreditRiskCommentID +
                ", facilityPaperID=" + facilityPaperID +
                ", UPMPrivilegeCode='" + UPMPrivilegeCode + '\'' +
                ", creditRiskComment='" + creditRiskComment + '\'' +
                ", commentedTimeStr='" + commentedTimeStr + '\'' +
                ", createdUserName='" + createdUserName + '\'' +
                ", modifiedUserName='" + modifiedUserName + '\'' +
                ", upmID=" + upmID +
                ", isLocked=" + isLocked +
                ", isValidComment=" + isValidComment +
                ", status=" + status +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", modifiedDateStr='" + modifiedDateStr + '\'' +
                ", fpCreditRiskReplyDTO=" + fpCreditRiskReplyDTO +
                ", version=" + version +
                '}';
    }
}

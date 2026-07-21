package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.FPCreditRiskReply;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class FPCreditRiskReplyDTO implements Serializable {

    private Integer facilityPaperID;

    private Integer fpCreditRiskReplyID;

    private Integer fpCreditRiskCommentID;

    private String replyComment;

    private String createdUserName;

    private String modifiedUserName;

    private String createdDivCode;

    private AppsConstants.Status status;

    private String createdBy;

    private String modifiedBy;

    private String createdDateStr;

    private String modifiedDateStr;

    public FPCreditRiskReplyDTO() {
    }

    public FPCreditRiskReplyDTO(FPCreditRiskReply fpCreditRiskReply) {
        this.fpCreditRiskReplyID = fpCreditRiskReply.getFpCreditRiskReplyID();
        this.fpCreditRiskCommentID = fpCreditRiskReply.getFpCreditRiskComment().getFpCreditRiskCommentID();
        this.replyComment = fpCreditRiskReply.getReplyComment();
        this.createdUserName = fpCreditRiskReply.getCreatedUserName();
        this.modifiedUserName = fpCreditRiskReply.getModifiedUserName();
        this.status = fpCreditRiskReply.getStatus();
        this.createdBy = fpCreditRiskReply.getCreatedBy();
        this.modifiedBy = fpCreditRiskReply.getModifiedBy();
        this.createdDivCode = fpCreditRiskReply.getCreatedDivCode();
        if (fpCreditRiskReply.getCreatedDate() != null) {
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(fpCreditRiskReply.getCreatedDate());
        }
        if (fpCreditRiskReply.getModifiedDate() != null) {
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(fpCreditRiskReply.getModifiedDate());
        }
    }

    public Integer getFpCreditRiskReplyID() {
        return fpCreditRiskReplyID;
    }

    public void setFpCreditRiskReplyID(Integer fpCreditRiskReplyID) {
        this.fpCreditRiskReplyID = fpCreditRiskReplyID;
    }

    public Integer getFpCreditRiskCommentID() {
        return fpCreditRiskCommentID;
    }

    public void setFpCreditRiskCommentID(Integer fpCreditRiskCommentID) {
        this.fpCreditRiskCommentID = fpCreditRiskCommentID;
    }

    public String getReplyComment() {
        return replyComment;
    }

    public void setReplyComment(String replyComment) {
        this.replyComment = replyComment;
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

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public String getCreatedDivCode() {
        return createdDivCode;
    }

    public void setCreatedDivCode(String createdDivCode) {
        this.createdDivCode = createdDivCode;
    }

    @Override
    public String toString() {
        return "FPCreditRiskReplyDTO{" +
                "facilityPaperID=" + facilityPaperID +
                ", fpCreditRiskReplyID=" + fpCreditRiskReplyID +
                ", fpCreditRiskCommentID=" + fpCreditRiskCommentID +
                ", createdUserName='" + createdUserName + '\'' +
                ", modifiedUserName='" + modifiedUserName + '\'' +
                ", createdDivCode='" + createdDivCode + '\'' +
                ", status=" + status +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", modifiedDateStr='" + modifiedDateStr + '\'' +
                '}';
    }
}

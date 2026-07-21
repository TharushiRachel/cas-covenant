package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class FPCommentContetDTO extends BaseContentDTO {

    @SerializedName("FP COMMENT ID")
    private Integer fpCommentID;

    @SerializedName("FACILITY PAPER ID")
    private Integer facilityPaperID;

    @SerializedName("FACILITY PAPER REF NUMBER")
    private String facilityPaperRefNumber;

    @SerializedName("COMMENT")
    private String comment;

    @SerializedName("STATUS")
    private String status;

    @SerializedName("COMMENTED TIME")
    private String commentedTimeStr;

    @SerializedName("COMMENTED BY")
    private String commentedBy;

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

    public String getFacilityPaperRefNumber() {
        return facilityPaperRefNumber;
    }

    public void setFacilityPaperRefNumber(String facilityPaperRefNumber) {
        this.facilityPaperRefNumber = facilityPaperRefNumber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommentedTimeStr() {
        return commentedTimeStr;
    }

    public void setCommentedTimeStr(String commentedTimeStr) {
        this.commentedTimeStr = commentedTimeStr;
    }

    public String getCommentedBy() {
        return commentedBy;
    }

    public void setCommentedBy(String commentedBy) {
        this.commentedBy = commentedBy;
    }
}

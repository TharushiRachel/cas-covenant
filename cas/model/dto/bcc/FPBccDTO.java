package com.itechro.cas.model.dto.bcc;

import com.itechro.cas.model.domain.bcc.FPBcc;
import com.itechro.cas.model.domain.bcc.FPBccDocument;
import com.itechro.cas.model.dto.email.EmailAttachment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FPBccDTO implements Serializable {

    private Integer fpBccId;
    private Integer facilityPaperID;
    private String bccStatus;
    private String createdBy;
    private String approveStatus;
    private String approvedBy;
    private Date approvedDate;
    private String createdUserDisplayName;
    private String approvedUserDisplayName;
    private String actionMessage;
    private String bccActionComment;
    private String commentUserDisplayName;
    private Date bccMeetingDate;


  //  private List<FPBccDocumentDTO> fpBccDocumentSet;

    private List<FPBccDocumentDTO> fpBccDocumentList;

  //  private List<FPBccStatusHistoryDTO> fpBccStatusHistoryList;

    private List<EmailAttachment> attachments;

    private List<String> bccAuthRecipientIds;

    private List<String> bccAuthRecipients;

    private String docsApproveStatus;

    public FPBccDTO() {
    }

    public FPBccDTO(FPBcc fpBcc) {

        this.fpBccId = fpBcc.getFpBccId();
        this.facilityPaperID = fpBcc.getFacilityPaper().getFacilityPaperID();
        this.bccStatus = fpBcc.getBccStatus() != null ? fpBcc.getBccStatus().getValue() : "";
        this.createdBy = fpBcc.getCreatedBy();
        this.approveStatus = fpBcc.getApproveStatus().getValue();
        this.approvedBy = fpBcc.getApprovedBy();
        this.approvedDate = fpBcc.getApprovedDate();
        this.createdUserDisplayName = fpBcc.getCreatedUserDisplayName();
        this.approvedUserDisplayName = fpBcc.getApprovedUserDisplayName();
        this.actionMessage = fpBcc.getActionMessage();
        this.docsApproveStatus = fpBcc.getDocsApproveStatus().getValue();
        //this.fpBccDocumentSet = fpBcc.getFpBccDocumentSet();
        //this.fpBccCommentSet = fpBcc.getFpBccCommentSet();

      /*  if (!fpBcc.getFpBccStatusHistorySet().isEmpty()) {
            for (FPBccStatusHistory fpBccStatusHistory : fpBcc.getFpBccStatusHistorySet()) {
                this.getFpBccStatusHistoryList().add(new FPBccStatusHistoryDTO(fpBccStatusHistory));
            }
        }*/

        if (!fpBcc.getFpBccDocumentSet().isEmpty() && fpBcc.getFpBccDocumentSet() != null) {
            for (FPBccDocument fpBccDocument : fpBcc.getFpBccDocumentSet()) {
                this.getFpBccDocumentList().add(new FPBccDocumentDTO(fpBccDocument));
            }
        }

    }



    public Integer getFpBccId() {
        return fpBccId;
    }

    public void setFpBccId(Integer fpBccId) {
        this.fpBccId = fpBccId;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public String getBccStatus() {
        return bccStatus;
    }

    public void setBccStatus(String bccStatus) {
        this.bccStatus = bccStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getCreatedUserDisplayName() {
        return createdUserDisplayName;
    }

    public void setCreatedUserDisplayName(String createdUserDisplayName) {
        this.createdUserDisplayName = createdUserDisplayName;
    }

    public String getApprovedUserDisplayName() {
        return approvedUserDisplayName;
    }

    public void setApprovedUserDisplayName(String approvedUserDisplayName) {
        this.approvedUserDisplayName = approvedUserDisplayName;
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }


    public List<FPBccDocumentDTO> getFpBccDocumentList() {
        if (fpBccDocumentList == null) {
            this.fpBccDocumentList = new ArrayList<>();
        }
        return fpBccDocumentList;
    }

    public void setFpBccDocumentList(List<FPBccDocumentDTO> fpBccDocumentList) {
        this.fpBccDocumentList = fpBccDocumentList;
    }


    @Override
    public String toString() {
        return "FPBccDTO{" +
                "fpBccId=" + fpBccId +
                ", facilityPaperID=" + facilityPaperID +
                ", bccStatus='" + bccStatus + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", approveStatus='" + approveStatus + '\'' +
                ", approvedBy='" + approvedBy + '\'' +
                ", approvedDate=" + approvedDate +
                ", createdUserDisplayName='" + createdUserDisplayName + '\'' +
                ", approvedUserDisplayName='" + approvedUserDisplayName + '\'' +
                ", actionMessage='" + actionMessage + '\'' +
                ", fpBccDocumentList=" + fpBccDocumentList +
                ", docsApproveStatus=" + docsApproveStatus +
                '}';
    }

    public List<EmailAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<EmailAttachment> attachments) {
        this.attachments = attachments;
    }

    public List<String> getBccAuthRecipientIds() {
        return bccAuthRecipientIds;
    }

    public void setBccAuthRecipientIds(List<String> bccAuthRecipientIds) {
        this.bccAuthRecipientIds = bccAuthRecipientIds;
    }

    public String getBccActionComment() {
        return bccActionComment;
    }

    public void setBccActionComment(String bccActionComment) {
        this.bccActionComment = bccActionComment;
    }

    public String getCommentUserDisplayName() {
        return commentUserDisplayName;
    }

    public void setCommentUserDisplayName(String commentUserDisplayName) {
        this.commentUserDisplayName = commentUserDisplayName;
    }

    public List<String> getBccAuthRecipients() {
        return bccAuthRecipients;
    }

    public void setBccAuthRecipients(List<String> bccAuthRecipients) {
        this.bccAuthRecipients = bccAuthRecipients;
    }

//    public String getIsPendingDocs() {
//        return docsApproveStatus;
//    }
//
//    public void setIsPendingDocs(String docsApproveStatus) {
//        this.docsApproveStatus = docsApproveStatus;
//    }

    public String getDocsApproveStatus() {
        return docsApproveStatus;
    }

    public void setDocsApproveStatus(String docsApproveStatus) {
        this.docsApproveStatus = docsApproveStatus;
    }

    public Date getBccMeetingDate() {
        return bccMeetingDate;
    }

    public void setBccMeetingDate(Date bccMeetingDate) {
        this.bccMeetingDate = bccMeetingDate;
    }
}

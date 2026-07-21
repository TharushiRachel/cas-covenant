package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.dto.email.EmailAttachment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FacilityPaperUpdateDTO implements Serializable {

    private static final long serialVersionUID = -1403362496914465869L;

    private Integer facilityPaperID;

    private FPCommentDTO fpCommentDTO;

    private Integer assignUserID;

    private String assignADUserID;

    private String assignUser;

    private String assignUserDivCode;

    private String assignUserDisplayName;

    private Integer assignUserUpmID;

    private String assignUserUpmGroupCode;

    private String assignUserEmailAddress;

    private String assignDepartmentCode;

    private String actionMessage;

    private String fpRefNumber;

    private String updatedByUserDisplayName;

    private String currentAssignUser;

    private Integer currentUserCommitteeLevel;

    private String currentCommitteePaperStatus;

    private List<CommitteePaperDTO> committeePaperDTOList;

    private AppsConstants.YesNo isFPCancelToAgent;

    private String outstandingDateStr;

    private List<FPAssignDepartmentDTO> fpAssignDepartmentDTOList;

    private DomainConstants.ForwardType forwardType;

    private DomainConstants.FacilityPaperStatus facilityPaperStatus;

    private DomainConstants.FacilityPaperRoutingStatus routingStatus;

    private String authorityLevel;

    private List<EmailAttachment> attachments;

    private List<String> bccAddresses;


    private String bccActionComment;

    private String commentUserDisplayName;

    private AppsConstants.YesNo isReviewPaper;

    private AppsConstants.YesNo isChangeDocumentStatus;

    private AppsConstants.YesNo isAutoApproval;

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public DomainConstants.FacilityPaperStatus getFacilityPaperStatus() {
        return facilityPaperStatus;
    }

    public void setFacilityPaperStatus(DomainConstants.FacilityPaperStatus facilityPaperStatus) {
        this.facilityPaperStatus = facilityPaperStatus;
    }

    public FPCommentDTO getFpCommentDTO() {
        return fpCommentDTO;
    }

    public void setFpCommentDTO(FPCommentDTO fpCommentDTO) {
        this.fpCommentDTO = fpCommentDTO;
    }

    public Integer getAssignUserID() {
        return assignUserID;
    }

    public void setAssignUserID(Integer assignUserID) {
        this.assignUserID = assignUserID;
    }

    public String getAssignADUserID() {
        return assignADUserID;
    }

    public void setAssignADUserID(String assignADUserID) {
        this.assignADUserID = assignADUserID;
    }

    public String getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(String assignUser) {
        this.assignUser = assignUser;
    }

    public String getAssignUserEmailAddress() {
        return assignUserEmailAddress;
    }

    public void setAssignUserEmailAddress(String assignUserEmailAddress) {
        this.assignUserEmailAddress = assignUserEmailAddress;
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    public String getAssignUserDisplayName() {
        return assignUserDisplayName;
    }

    public void setAssignUserDisplayName(String assignUserDisplayName) {
        this.assignUserDisplayName = assignUserDisplayName;
    }

    public String getFpRefNumber() {
        return fpRefNumber;
    }

    public void setFpRefNumber(String fpRefNumber) {
        this.fpRefNumber = fpRefNumber;
    }

    public String getUpdatedByUserDisplayName() {
        return updatedByUserDisplayName;
    }

    public void setUpdatedByUserDisplayName(String updatedByUserDisplayName) {
        this.updatedByUserDisplayName = updatedByUserDisplayName;
    }

    public AppsConstants.YesNo getIsFPCancelToAgent() {
        return isFPCancelToAgent;
    }

    public void setIsFPCancelToAgent(AppsConstants.YesNo isFPCancelToAgent) {
        this.isFPCancelToAgent = isFPCancelToAgent;
    }

    public String getOutstandingDateStr() {
        return outstandingDateStr;
    }

    public void setOutstandingDateStr(String outstandingDateStr) {
        this.outstandingDateStr = outstandingDateStr;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAssignUserDivCode() {
        return assignUserDivCode;
    }

    public void setAssignUserDivCode(String assignUserDivCode) {
        this.assignUserDivCode = assignUserDivCode;
    }

    public Integer getAssignUserUpmID() {
        return assignUserUpmID;
    }

    public void setAssignUserUpmID(Integer assignUserUpmID) {
        this.assignUserUpmID = assignUserUpmID;
    }

    public String getAssignUserUpmGroupCode() {
        return assignUserUpmGroupCode;
    }

    public void setAssignUserUpmGroupCode(String assignUserUpmGroupCode) {
        this.assignUserUpmGroupCode = assignUserUpmGroupCode;
    }

    public String getAssignDepartmentCode() {
        return assignDepartmentCode;
    }

    public void setAssignDepartmentCode(String assignDepartmentCode) {
        this.assignDepartmentCode = assignDepartmentCode;
    }

    public List<FPAssignDepartmentDTO> getFpAssignDepartmentDTOList() {
        if (fpAssignDepartmentDTOList == null) {
            this.fpAssignDepartmentDTOList = new ArrayList<>();
        }
        return fpAssignDepartmentDTOList;
    }

    public void setFpAssignDepartmentDTOList(List<FPAssignDepartmentDTO> fpAssignDepartmentDTOList) {
        this.fpAssignDepartmentDTOList = fpAssignDepartmentDTOList;
    }

    public List<CommitteePaperDTO> getCommitteePaperDTOList() {
        if (committeePaperDTOList == null) {
            this.committeePaperDTOList = new ArrayList<>();
        }
        return committeePaperDTOList;
    }

    public void setCommitteePaperDTOList(List<CommitteePaperDTO> committeePaperDTOList) {
        this.committeePaperDTOList = committeePaperDTOList;
    }

    public DomainConstants.ForwardType getForwardType() {
        return forwardType;
    }

    public void setForwardType(DomainConstants.ForwardType forwardType) {
        this.forwardType = forwardType;
    }

    public DomainConstants.FacilityPaperRoutingStatus getRoutingStatus() {
        return routingStatus;
    }

    public void setRoutingStatus(DomainConstants.FacilityPaperRoutingStatus routingStatus) {
        this.routingStatus = routingStatus;
    }

    public String getAuthorityLevel() {
        return authorityLevel;
    }

    public void setAuthorityLevel(String authorityLevel) {
        this.authorityLevel = authorityLevel;
    }

    public String getCurrentAssignUser() {
        return currentAssignUser;
    }

    public void setCurrentAssignUser(String currentAssignUser) {
        this.currentAssignUser = currentAssignUser;
    }


    public Integer getCurrentUserCommitteeLevel() {
        return currentUserCommitteeLevel;
    }

    public void setCurrentUserCommitteeLevel(Integer currentUserCommitteeLevel) {
        this.currentUserCommitteeLevel = currentUserCommitteeLevel;
    }

    public String getCurrentCommitteePaperStatus() {
        return currentCommitteePaperStatus;
    }

    public void setCurrentCommitteePaperStatus(String currentCommitteePaperStatus) {
        this.currentCommitteePaperStatus = currentCommitteePaperStatus;
    }

    @Override
    public String toString() {
        return "FacilityPaperUpdateDTO{" +
                "facilityPaperID=" + facilityPaperID +
                ", fpCommentDTO=" + fpCommentDTO +
                ", assignUserID=" + assignUserID +
                ", assignADUserID='" + assignADUserID + '\'' +
                ", assignUser='" + assignUser + '\'' +
                ", assignUserDivCode='" + assignUserDivCode + '\'' +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                ", assignUserUpmID=" + assignUserUpmID +
                ", assignUserUpmGroupCode='" + assignUserUpmGroupCode + '\'' +
                ", assignUserEmailAddress='" + assignUserEmailAddress + '\'' +
                ", assignDepartmentCode='" + assignDepartmentCode + '\'' +
                ", actionMessage='" + actionMessage + '\'' +
                ", fpRefNumber='" + fpRefNumber + '\'' +
                ", updatedByUserDisplayName='" + updatedByUserDisplayName + '\'' +
                ", currentAssignUser='" + currentAssignUser + '\'' +
                ", currentUserCommitteeLevel=" + currentUserCommitteeLevel +
                ", currentCommitteePaperStatus='" + currentCommitteePaperStatus + '\'' +
                ", committeePaperDTOList=" + committeePaperDTOList +
                ", isFPCancelToAgent=" + isFPCancelToAgent +
                ", outstandingDateStr='" + outstandingDateStr + '\'' +
                ", fpAssignDepartmentDTOList=" + fpAssignDepartmentDTOList +
                ", forwardType=" + forwardType +
                ", facilityPaperStatus=" + facilityPaperStatus +
                ", routingStatus=" + routingStatus +
                ", authorityLevel='" + authorityLevel + '\'' +
                '}';
    }

    public List<String> getBccAddresses() {
        return bccAddresses;
    }

    public void setBccAddresses(List<String> bccAddresses) {
        this.bccAddresses = bccAddresses;
    }

    public List<EmailAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<EmailAttachment> attachments) {
        this.attachments = attachments;
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

    public AppsConstants.YesNo getIsReviewPaper() {
        if (this.isReviewPaper == null) {
            this.isReviewPaper = AppsConstants.YesNo.N;
        }
        return isReviewPaper;
    }

    public void setIsReviewPaper(AppsConstants.YesNo isReviewPaper) {
        this.isReviewPaper = isReviewPaper;
    }

    public AppsConstants.YesNo getIsChangeDocumentStatus() {
        if (this.isChangeDocumentStatus == null) {
            this.isChangeDocumentStatus = AppsConstants.YesNo.N;
        }
        return isChangeDocumentStatus;
    }

    public void setIsChangeDocumentStatus(AppsConstants.YesNo isChangeDocumentStatus) {
        this.isChangeDocumentStatus = isChangeDocumentStatus;
    }

    public AppsConstants.YesNo getIsAutoApproval() {
        if (this.isAutoApproval == null){
            return AppsConstants.YesNo.N;
        }
        return isAutoApproval;
    }

    public void setIsAutoApproval(AppsConstants.YesNo isAutoApproval) {
        this.isAutoApproval = isAutoApproval;
    }
}

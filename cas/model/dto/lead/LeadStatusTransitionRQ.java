package com.itechro.cas.model.dto.lead;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.lead.Lead;
import com.itechro.cas.model.security.CredentialsDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LeadStatusTransitionRQ implements Serializable {

    private Integer leadID;
    private Integer assignUserID;

    private String assignADUserID;

    private String assignUser;

    private String customerName;

    private String branchName;

    private String leadRefNo;

    private String solID;

    private String totalExposure;

    private String paperCreatedDate;

    private String lastComment;

    private String lastCommentedUser; //Senders Display name

    private String assignUserDisplayName; // Receivers Display Name

    private String action;

    private DomainConstants.LeadStatus newStatus;

    private DomainConstants.FacilityPaperRoutingStatus routingStatus;

    private String userName;

    private List<String> toAddresses;

    private LeadDTO leadDTO;

    private Lead lead;

    private CredentialsDTO credentialsDTO;

    private List<LeadFacilityDetailEmailDTO> leadFacilityDetailEmailDTO;

    public Integer getLeadID() {
        return leadID;
    }

    public void setLeadID(Integer leadID) {
        this.leadID = leadID;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getSolID() {
        return solID;
    }

    public void setSolID(String solID) {
        this.solID = solID;
    }

    public String getTotalExposure() {
        return totalExposure;
    }

    public void setTotalExposure(String totalExposure) {
        this.totalExposure = totalExposure;
    }

    public String getPaperCreatedDate() {
        return paperCreatedDate;
    }

    public void setPaperCreatedDate(String paperCreatedDate) {
        this.paperCreatedDate = paperCreatedDate;
    }

    public String getLastComment() {
        return lastComment;
    }

    public void setLastComment(String lastComment) {
        this.lastComment = lastComment;
    }

    public String getLastCommentedUser() {
        return lastCommentedUser;
    }

    public void setLastCommentedUser(String lastCommentedUser) {
        this.lastCommentedUser = lastCommentedUser;
    }

    public String getAssignUserDisplayName() {
        return assignUserDisplayName;
    }

    public void setAssignUserDisplayName(String assignUserDisplayName) {
        this.assignUserDisplayName = assignUserDisplayName;
    }

    public String getAction() {
        switch (this.getNewStatus()) {
            case SUBMITTED:
                this.action = " has been submitted to you";
                break;
            case APPROVED:
                this.action = " has been approved";
                break;
            case RETURNED:
                this.action = " has been returned to you";
                break;
            case DECLINED:
                this.action = " has been declined";
                break;
            case PAPER_CREATED:
                this.action = " has been converted to an application form ";
                break;
            case ACCEPTED:
                this.action = " has been accepted ";
                break;
            case APPLICATION_CREATED:
                this.action = " has been converted to an application form ";
                break;
            default:
                this.action = "";
        }
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


    public DomainConstants.LeadStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(DomainConstants.LeadStatus newStatus) {
        this.newStatus = newStatus;
    }

    public DomainConstants.FacilityPaperRoutingStatus getRoutingStatus() {
        return routingStatus;
    }

    public void setRoutingStatus(DomainConstants.FacilityPaperRoutingStatus routingStatus) {
        this.routingStatus = routingStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getToAddresses() {
        return toAddresses;
    }

    public void setToAddresses(List<String> toAddresses) {
        this.toAddresses = toAddresses;
    }

    public CredentialsDTO getCredentialsDTO() {
        return credentialsDTO;
    }

    public void setCredentialsDTO(CredentialsDTO credentialsDTO) {
        this.credentialsDTO = credentialsDTO;
    }

    public String getLeadRefNo() {
        return leadRefNo;
    }

    public void setLeadRefNo(String leadRefNo) {
        this.leadRefNo = leadRefNo;
    }

    public Lead getLead() {
        return lead;
    }

    public void setLead(Lead lead) {
        this.lead = lead;
    }

    public LeadDTO getLeadDTO() {
        return leadDTO;
    }

    public void setLeadDTO(LeadDTO leadDTO) {
        this.leadDTO = leadDTO;
    }

    public List<LeadFacilityDetailEmailDTO> getLeadFacilityDetailEmailDTO() {
        if (leadFacilityDetailEmailDTO == null) {
            this.leadFacilityDetailEmailDTO = new ArrayList<>();
        }
        return leadFacilityDetailEmailDTO;
    }

    public void setLeadFacilityDetailEmailDTO(List<LeadFacilityDetailEmailDTO> leadFacilityDetailEmailDTO) {
        this.leadFacilityDetailEmailDTO = leadFacilityDetailEmailDTO;
    }
}

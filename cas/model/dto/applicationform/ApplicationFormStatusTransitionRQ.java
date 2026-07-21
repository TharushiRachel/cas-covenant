package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.dto.facility.FacilityDTO;
import com.itechro.cas.model.security.CredentialsDTO;

import java.io.Serializable;
import java.util.List;

public class ApplicationFormStatusTransitionRQ implements Serializable {

    private Integer applicationFormID;

    private String afRefNumber;

    private Integer assignUserID;

    private String assignADUserID;

    private String assignUser;

    private List<String> customerName;

    private String branchName;

    private String solID;

    private String totalExposure;

    private String paperCreatedDate;

    private String lastComment;

    private String lastCommentedUser; //Senders Display name

    private String assignUserDisplayName; // Receivers Display Name

    private String action;

    private DomainConstants.ApplicationFormStatus newStatus;

    private DomainConstants.FacilityPaperRoutingStatus routingStatus;

    private String userName;

    private List<String> toAddresses;

    private CredentialsDTO credentialsDTO;

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    public String getAfRefNumber() {
        return afRefNumber;
    }

    public void setAfRefNumber(String afRefNumber) {
        this.afRefNumber = afRefNumber;
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

    public List<String> getCustomerName() {
        return customerName;
    }

    public void setCustomerName(List<String> customerName) {
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

//    public String getAction() {
//        return action;
//    }

    public String getAction() {
        switch (this.getNewStatus()) {
            case IN_PROGRESS:
                this.action = " has been forwarded to you";
                break;
            case PAPER_CREATED:
                this.action = " has been converted to a facility paper";
                break;
            case RETURNED:
                this.action = " has been returned to you";
                break;
            case RETURNED_TO_USER_GROUP:
                this.action = " has been returned to user group of managers";
                break;
            case DECLINED:
                this.action = " has been declined";
                break;
            case FORWARDED_TO_USER_GROUP:
                this.action = " has been forwarded to user group of managers";
                break;
            case FORWARDED:
                this.action = " has been forwarded to you";
                break;
            default:
                this.action = "";
        }
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public DomainConstants.ApplicationFormStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(DomainConstants.ApplicationFormStatus newStatus) {
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

    @Override
    public String toString() {
        return "ApplicationFormStatusTransitionRQ{" +
                "applicationFormID=" + applicationFormID +
                ", afRefNumber='" + afRefNumber + '\'' +
                ", assignUserID=" + assignUserID +
                ", assignADUserID='" + assignADUserID + '\'' +
                ", assignUser='" + assignUser + '\'' +
                ", customerName=" + customerName +
                ", branchName='" + branchName + '\'' +
                ", solID='" + solID + '\'' +
                ", totalExposure='" + totalExposure + '\'' +
                ", paperCreatedDate='" + paperCreatedDate + '\'' +
                ", lastComment='" + lastComment + '\'' +
                ", lastCommentedUser='" + lastCommentedUser + '\'' +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                ", action='" + action + '\'' +
                ", newStatus=" + newStatus +
                ", routingStatus=" + routingStatus +
                ", userName='" + userName + '\'' +
                ", toAddresses=" + toAddresses +
                ", credentialsDTO=" + credentialsDTO +
                '}';
    }
}

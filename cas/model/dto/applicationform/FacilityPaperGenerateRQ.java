package com.itechro.cas.model.dto.applicationform;

import java.io.Serializable;

public class FacilityPaperGenerateRQ implements Serializable {

    private Integer applicationFormID;

    private String afRefNumber;

    private Integer currentAssignUserID;

    private String currentAssignADUserID;

    private String currentAssignUser;

    private String currentAssignUserDivCode;

    private String assignUserDisplayName;

    private String createdUserDisplayName;

    private Integer assignUserUpmID;

    private String assignUserUpmGroupCode;

    private AFCommentDTO afCommentDTO;

    public FacilityPaperGenerateRQ() {
    }

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    public Integer getCurrentAssignUserID() {
        return currentAssignUserID;
    }

    public void setCurrentAssignUserID(Integer currentAssignUserID) {
        this.currentAssignUserID = currentAssignUserID;
    }

    public String getCurrentAssignADUserID() {
        return currentAssignADUserID;
    }

    public void setCurrentAssignADUserID(String currentAssignADUserID) {
        this.currentAssignADUserID = currentAssignADUserID;
    }

    public String getCurrentAssignUser() {
        return currentAssignUser;
    }

    public void setCurrentAssignUser(String currentAssignUser) {
        this.currentAssignUser = currentAssignUser;
    }

    public String getAssignUserDisplayName() {
        return assignUserDisplayName;
    }

    public void setAssignUserDisplayName(String assignUserDisplayName) {
        this.assignUserDisplayName = assignUserDisplayName;
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

    public String getAfRefNumber() {
        return afRefNumber;
    }

    public void setAfRefNumber(String afRefNumber) {
        this.afRefNumber = afRefNumber;
    }

    public String getCurrentAssignUserDivCode() {
        return currentAssignUserDivCode;
    }

    public void setCurrentAssignUserDivCode(String currentAssignUserDivCode) {
        this.currentAssignUserDivCode = currentAssignUserDivCode;
    }

    public AFCommentDTO getAfCommentDTO() {
        return afCommentDTO;
    }

    public void setAfCommentDTO(AFCommentDTO afCommentDTO) {
        this.afCommentDTO = afCommentDTO;
    }

    public String getCreatedUserDisplayName() {
        return createdUserDisplayName;
    }

    public void setCreatedUserDisplayName(String createdUserDisplayName) {
        this.createdUserDisplayName = createdUserDisplayName;
    }

    @Override
    public String toString() {
        return "FacilityPaperGenerateRQ{" +
                "applicationFormID=" + applicationFormID +
                ", afRefNumber='" + afRefNumber + '\'' +
                ", currentAssignUserID=" + currentAssignUserID +
                ", currentAssignADUserID='" + currentAssignADUserID + '\'' +
                ", currentAssignUser='" + currentAssignUser + '\'' +
                ", currentAssignUserDivCode='" + currentAssignUserDivCode + '\'' +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                ", createdUserDisplayName='" + createdUserDisplayName + '\'' +
                ", assignUserUpmID=" + assignUserUpmID +
                ", assignUserUpmGroupCode='" + assignUserUpmGroupCode + '\'' +
                ", afCommentDTO=" + afCommentDTO +
                '}';
    }
}

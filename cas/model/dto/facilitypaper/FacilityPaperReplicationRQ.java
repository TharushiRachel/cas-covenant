package com.itechro.cas.model.dto.facilitypaper;

import java.io.Serializable;

public class FacilityPaperReplicationRQ implements Serializable {

    private Integer originalFacilityPaperID;

    private String assignUserDisplayName;

    private String createdUserDisplayName;

    private String currentAssignUser;

    private Integer currentAssignUserID;

    private String currentAssignUserDivCode;

    private String currentAuthorityLevel;

    private Integer workflowTemplateID;

    private String branchCode;

    private Integer assignUserUpmID;

    private String assignUserUpmGroupCode;

    public Integer getOriginalFacilityPaperID() {
        return originalFacilityPaperID;
    }

    public void setOriginalFacilityPaperID(Integer originalFacilityPaperID) {
        this.originalFacilityPaperID = originalFacilityPaperID;
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

    public Integer getWorkflowTemplateID() {
        return workflowTemplateID;
    }

    public void setWorkflowTemplateID(Integer workflowTemplateID) {
        this.workflowTemplateID = workflowTemplateID;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getCurrentAssignUser() {
        return currentAssignUser;
    }

    public void setCurrentAssignUser(String currentAssignUser) {
        this.currentAssignUser = currentAssignUser;
    }

    public Integer getCurrentAssignUserID() {
        return currentAssignUserID;
    }

    public void setCurrentAssignUserID(Integer currentAssignUserID) {
        this.currentAssignUserID = currentAssignUserID;
    }

    public String getCurrentAuthorityLevel() {
        return currentAuthorityLevel;
    }

    public void setCurrentAuthorityLevel(String currentAuthorityLevel) {
        this.currentAuthorityLevel = currentAuthorityLevel;
    }

    public String getCurrentAssignUserDivCode() {
        return currentAssignUserDivCode;
    }

    public void setCurrentAssignUserDivCode(String currentAssignUserDivCode) {
        this.currentAssignUserDivCode = currentAssignUserDivCode;
    }

    public String getCreatedUserDisplayName() {
        return createdUserDisplayName;
    }

    public void setCreatedUserDisplayName(String createdUserDisplayName) {
        this.createdUserDisplayName = createdUserDisplayName;
    }

    @Override
    public String toString() {
        return "FacilityPaperReplicationRQ{" +
                "originalFacilityPaperID=" + originalFacilityPaperID +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                ", createdUserDisplayName='" + createdUserDisplayName + '\'' +
                ", currentAssignUser='" + currentAssignUser + '\'' +
                ", currentAssignUserID=" + currentAssignUserID +
                ", currentAssignUserDivCode='" + currentAssignUserDivCode + '\'' +
                ", currentAuthorityLevel='" + currentAuthorityLevel + '\'' +
                ", workflowTemplateID=" + workflowTemplateID +
                ", branchCode='" + branchCode + '\'' +
                ", assignUserUpmID=" + assignUserUpmID +
                ", assignUserUpmGroupCode='" + assignUserUpmGroupCode + '\'' +
                '}';
    }
}

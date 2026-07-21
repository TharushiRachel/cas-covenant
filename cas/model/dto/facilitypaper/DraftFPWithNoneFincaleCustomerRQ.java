package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;

import java.io.Serializable;

public class DraftFPWithNoneFincaleCustomerRQ implements Serializable {

    private String branchCode;

    private String createdUserBranchCode;

    private AppsConstants.YesNo isCooperate;

    private Integer workflowTemplateID;

    private String currentAssignUser;

    private Integer currentAssignUserID;

    private String currentAssignUserDivCode;

    private String assignUserUpmGroupCode;

    private String assignUserDisplayName;

    private String createdUserDisplayName;

    private Integer assignUserUpmID;

    private CASCustomerDTO casCustomerDTO;

    public DraftFPWithNoneFincaleCustomerRQ() {
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getCreatedUserBranchCode() {
        return createdUserBranchCode;
    }

    public void setCreatedUserBranchCode(String createdUserBranchCode) {
        this.createdUserBranchCode = createdUserBranchCode;
    }

    public AppsConstants.YesNo getIsCooperate() {
        return isCooperate;
    }

    public void setIsCooperate(AppsConstants.YesNo isCooperate) {
        this.isCooperate = isCooperate;
    }

    public Integer getWorkflowTemplateID() {
        return workflowTemplateID;
    }

    public void setWorkflowTemplateID(Integer workflowTemplateID) {
        this.workflowTemplateID = workflowTemplateID;
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

    public String getCurrentAssignUserDivCode() {
        return currentAssignUserDivCode;
    }

    public void setCurrentAssignUserDivCode(String currentAssignUserDivCode) {
        this.currentAssignUserDivCode = currentAssignUserDivCode;
    }

    public String getAssignUserUpmGroupCode() {
        return assignUserUpmGroupCode;
    }

    public void setAssignUserUpmGroupCode(String assignUserUpmGroupCode) {
        this.assignUserUpmGroupCode = assignUserUpmGroupCode;
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

    public CASCustomerDTO getCasCustomerDTO() {
        return casCustomerDTO;
    }

    public void setCasCustomerDTO(CASCustomerDTO casCustomerDTO) {
        this.casCustomerDTO = casCustomerDTO;
    }

    public String getCreatedUserDisplayName() {
        return createdUserDisplayName;
    }

    public void setCreatedUserDisplayName(String createdUserDisplayName) {
        this.createdUserDisplayName = createdUserDisplayName;
    }

    @Override
    public String toString() {
        return "DraftFPWithNoneFincaleCustomerRQ{" +
                "branchCode='" + branchCode + '\'' +
                ", createdUserBranchCode='" + createdUserBranchCode + '\'' +
                ", isCooperate=" + isCooperate +
                ", workflowTemplateID=" + workflowTemplateID +
                ", currentAssignUser='" + currentAssignUser + '\'' +
                ", currentAssignUserID=" + currentAssignUserID +
                ", currentAssignUserDivCode='" + currentAssignUserDivCode + '\'' +
                ", assignUserUpmGroupCode='" + assignUserUpmGroupCode + '\'' +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                ", createdUserDisplayName='" + createdUserDisplayName + '\'' +
                ", assignUserUpmID=" + assignUserUpmID +
                ", casCustomerDTO=" + casCustomerDTO +
                '}';
    }
}

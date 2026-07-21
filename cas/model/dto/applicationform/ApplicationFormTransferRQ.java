package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.DomainConstants;

import java.io.Serializable;
import java.util.List;

public class ApplicationFormTransferRQ implements Serializable {

    private String afRefNumber;

    private String branchCode;

    private String assignUser;

    private String assignUserDisplayName;

    private DomainConstants.ApplicationFormStatus currentApplicationFormStatus;

    private String identificationNumber;

    private DomainConstants.CustomerIdentificationType identificationType;

    private String assignGroupUPMGroupCode;

    private String assignDepartmentCode;

    private List<String> otherUpmAccessLevels;

    private String loginUpmAccessLevel;

    private String loggedInUserBranchCode;

    public ApplicationFormTransferRQ() {
    }

    public String getAfRefNumber() {
        return afRefNumber;
    }

    public void setAfRefNumber(String afRefNumber) {
        this.afRefNumber = afRefNumber;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(String assignUser) {
        this.assignUser = assignUser;
    }

    public String getAssignUserDisplayName() {
        return assignUserDisplayName;
    }

    public void setAssignUserDisplayName(String assignUserDisplayName) {
        this.assignUserDisplayName = assignUserDisplayName;
    }

    public DomainConstants.ApplicationFormStatus getCurrentApplicationFormStatus() {
        return currentApplicationFormStatus;
    }

    public void setCurrentApplicationFormStatus(DomainConstants.ApplicationFormStatus currentApplicationFormStatus) {
        this.currentApplicationFormStatus = currentApplicationFormStatus;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public DomainConstants.CustomerIdentificationType getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(DomainConstants.CustomerIdentificationType identificationType) {
        this.identificationType = identificationType;
    }

    public String getAssignGroupUPMGroupCode() {
        return assignGroupUPMGroupCode;
    }

    public void setAssignGroupUPMGroupCode(String assignGroupUPMGroupCode) {
        this.assignGroupUPMGroupCode = assignGroupUPMGroupCode;
    }

    public String getAssignDepartmentCode() {
        return assignDepartmentCode;
    }

    public void setAssignDepartmentCode(String assignDepartmentCode) {
        this.assignDepartmentCode = assignDepartmentCode;
    }

    public List<String> getOtherUpmAccessLevels() {
        return otherUpmAccessLevels;
    }

    public void setOtherUpmAccessLevels(List<String> otherUpmAccessLevels) {
        this.otherUpmAccessLevels = otherUpmAccessLevels;
    }

    public String getLoginUpmAccessLevel() {
        return loginUpmAccessLevel;
    }

    public void setLoginUpmAccessLevel(String loginUpmAccessLevel) {
        this.loginUpmAccessLevel = loginUpmAccessLevel;
    }

    public String getLoggedInUserBranchCode() {
        return loggedInUserBranchCode;
    }

    public void setLoggedInUserBranchCode(String loggedInUserBranchCode) {
        this.loggedInUserBranchCode = loggedInUserBranchCode;
    }

    @Override
    public String toString() {
        return "ApplicationFormTransferRQ{" +
                "afRefNumber='" + afRefNumber + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", assignUser='" + assignUser + '\'' +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                ", currentApplicationFormStatus=" + currentApplicationFormStatus +
                ", identificationNumber='" + identificationNumber + '\'' +
                ", identificationType=" + identificationType +
                ", assignGroupUPMGroupCode='" + assignGroupUPMGroupCode + '\'' +
                ", assignDepartmentCode='" + assignDepartmentCode + '\'' +
                ", otherUpmAccessLevels=" + otherUpmAccessLevels +
                ", loginUpmAccessLevel='" + loginUpmAccessLevel + '\'' +
                ", loggedInUserBranchCode='" + loggedInUserBranchCode + '\'' +
                '}';
    }
}

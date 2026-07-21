package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;

import java.io.Serializable;

public class ApplicationFormCopyRSDTO implements Serializable {

    private Integer applicationFormID;

    private String afRefNumber;

    private String branchCode;

    private String createdUserDisplayName;

    private Integer createdUserID;

    private DomainConstants.BasicInformationType formType;

    private String createdDateStr;

    private String createdBy;

    private String modifiedDateStr;

    private String modifiedBy;

    private Integer assignUserID;

    private String assignUser;

    private String assignUserDisplayName;

    private Integer assignUserUpmID;

    private String assignUserUpmGroupCode;

    private DomainConstants.ApplicationFormStatus currentApplicationFormStatus;

    private String customerName;

    private AppsConstants.YesNo primaryInformation;

    private String fsType;


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

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getCreatedUserDisplayName() {
        return createdUserDisplayName;
    }

    public void setCreatedUserDisplayName(String createdUserDisplayName) {
        this.createdUserDisplayName = createdUserDisplayName;
    }

    public Integer getCreatedUserID() {
        return createdUserID;
    }

    public void setCreatedUserID(Integer createdUserID) {
        this.createdUserID = createdUserID;
    }

    public DomainConstants.BasicInformationType getFormType() {
        return formType;
    }

    public void setFormType(DomainConstants.BasicInformationType formType) {
        this.formType = formType;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedDateStr() {
        return modifiedDateStr;
    }

    public void setModifiedDateStr(String modifiedDateStr) {
        this.modifiedDateStr = modifiedDateStr;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Integer getAssignUserID() {
        return assignUserID;
    }

    public void setAssignUserID(Integer assignUserID) {
        this.assignUserID = assignUserID;
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

    public DomainConstants.ApplicationFormStatus getCurrentApplicationFormStatus() {
        return currentApplicationFormStatus;
    }

    public void setCurrentApplicationFormStatus(DomainConstants.ApplicationFormStatus currentApplicationFormStatus) {
        this.currentApplicationFormStatus = currentApplicationFormStatus;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public AppsConstants.YesNo getPrimaryInformation() {
        return primaryInformation;
    }

    public void setPrimaryInformation(AppsConstants.YesNo primaryInformation) {
        this.primaryInformation = primaryInformation;
    }

    @Override
    public String toString() {
        return "ApplicationFormCopyRSDTO{" +
                "applicationFormID=" + applicationFormID +
                ", afRefNumber='" + afRefNumber + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", createdUserDisplayName='" + createdUserDisplayName + '\'' +
                ", createdUserID=" + createdUserID +
                ", formType=" + formType +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedDateStr='" + modifiedDateStr + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", assignUserID=" + assignUserID +
                ", assignUser='" + assignUser + '\'' +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                ", assignUserUpmID=" + assignUserUpmID +
                ", assignUserUpmGroupCode='" + assignUserUpmGroupCode + '\'' +
                ", currentApplicationFormStatus=" + currentApplicationFormStatus +
                ", customerName='" + customerName + '\'' +
                ", primaryInformation=" + primaryInformation +
                '}';
    }

    public String getFsType() {
        return fsType;
    }

    public void setFsType(String fsType) {
        this.fsType = fsType;
    }
}

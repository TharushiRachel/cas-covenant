package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.applicationform.*;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.dto.facilitypaper.FPLoadOptionDTO;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ApplicationFormPageRSDTO implements Serializable {

    private Integer applicationFormID;

    private Integer workflowTemplateID;

    private String nameWithInitials; // Customer Name With initials

    private String nameOfBusiness; // If Application Form is business 0r Corporate is Name With initials

    private String registrationNo;// If Application Form is business 0r Corporate is Name With initials

    private String customerName;  // Finacle Customer Full name

    private DomainConstants.CustomerIdentificationType identificationType;

    private String identificationNumber;

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

    private String assignDepartmentCode;

    private String fsType;

    private DomainConstants.ApplicationFormStatus currentApplicationFormStatus;

   public ApplicationFormPageRSDTO(){}

    public ApplicationFormPageRSDTO(ApplicationForm applicationForm) {
        this.applicationFormID = applicationForm.getApplicationFormID();
        this.workflowTemplateID = applicationForm.getWorkFlowTemplate().getWorkFlowTemplateID();
        this.afRefNumber = applicationForm.getAfRefNumber();
        this.branchCode = applicationForm.getBranchCode();
        this.createdUserDisplayName = applicationForm.getCreatedUserDisplayName();
        this.createdUserID = applicationForm.getCreatedUserID();
        this.formType = applicationForm.getFormType();
        this.currentApplicationFormStatus = applicationForm.getCurrentApplicationFormStatus();
        this.assignUserID = applicationForm.getAssignUserID();
        this.assignUser = applicationForm.getAssignUser();
        this.assignUserDisplayName = applicationForm.getAssignUserDisplayName();
        this.assignUserUpmID = applicationForm.getAssignUserUpmID();
        this.assignUserUpmGroupCode = applicationForm.getAssignUserUpmGroupCode();
        //this.assignUserDivCode = applicationForm.getAssignUserDivCode();
        this.createdBy = applicationForm.getCreatedBy();
        this.modifiedBy = applicationForm.getModifiedBy();
        this.assignDepartmentCode = applicationForm.getAssignDepartmentCode();
        //this.leadID = applicationForm.getLeadID();
       // this.fsType = applicationForm.getFsType();
        if (applicationForm.getCreatedDate() != null) {
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(applicationForm.getCreatedDate());
        }
        if (applicationForm.getModifiedDate() != null) {
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(applicationForm.getModifiedDate());
        }
        this.fsType = applicationForm.getFsType();
    }


    public String getNameOfBusiness() {
        return nameOfBusiness;
    }

    public void setNameOfBusiness(String nameOfBusiness) {
        this.nameOfBusiness = nameOfBusiness;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    public String getNameWithInitials() {
        return nameWithInitials;
    }

    public void setNameWithInitials(String nameWithInitials) {
        this.nameWithInitials = nameWithInitials;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getAssignDepartmentCode() {
        return assignDepartmentCode;
    }

    public void setAssignDepartmentCode(String assignDepartmentCode) {
        this.assignDepartmentCode = assignDepartmentCode;
    }

    public DomainConstants.ApplicationFormStatus getCurrentApplicationFormStatus() {
        return currentApplicationFormStatus;
    }

    public void setCurrentApplicationFormStatus(DomainConstants.ApplicationFormStatus currentApplicationFormStatus) {
        this.currentApplicationFormStatus = currentApplicationFormStatus;
    }

    public DomainConstants.CustomerIdentificationType getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(DomainConstants.CustomerIdentificationType identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public Integer getWorkflowTemplateID() {
        return workflowTemplateID;
    }

    public void setWorkflowTemplateID(Integer workflowTemplateID) {
        this.workflowTemplateID = workflowTemplateID;
    }

    @Override
    public String toString() {
        return "ApplicationFormPageRSDTO{" +
                "applicationFormID=" + applicationFormID +
                ", workflowTemplateID=" + workflowTemplateID +
                ", nameWithInitials='" + nameWithInitials + '\'' +
                ", nameOfBusiness='" + nameOfBusiness + '\'' +
                ", registrationNo='" + registrationNo + '\'' +
                ", customerName='" + customerName + '\'' +
                ", identificationType=" + identificationType +
                ", identificationNumber='" + identificationNumber + '\'' +
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
                ", assignDepartmentCode='" + assignDepartmentCode + '\'' +
                ", currentApplicationFormStatus=" + currentApplicationFormStatus +
                '}';
    }

    public String getFsType() {
        return fsType;
    }

    public void setFsType(String fsType) {
        this.fsType = fsType;
    }
}

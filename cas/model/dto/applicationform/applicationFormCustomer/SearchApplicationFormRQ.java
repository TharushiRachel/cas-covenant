package com.itechro.cas.model.dto.applicationform.applicationFormCustomer;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.common.PagedParamDTO;

public class SearchApplicationFormRQ extends PagedParamDTO {

    private String afRefNumber;

    private String branchCode;

    private String assignUser;

    private String assignUserDisplayName;

    private DomainConstants.ApplicationFormStatus currentApplicationFormStatus;

    private String identificationNumber;

    private DomainConstants.CustomerIdentificationType identificationType;

    private String assignGroupUPMGroupCode;

    private String assignDepartmentCode;

    private String createdFromDateStr;

    private String createdToDateStr;

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

    public DomainConstants.ApplicationFormStatus getCurrentApplicationFormStatus() {
        return currentApplicationFormStatus;
    }

    public void setCurrentApplicationFormStatus(DomainConstants.ApplicationFormStatus currentApplicationFormStatus) {
        this.currentApplicationFormStatus = currentApplicationFormStatus;
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

    public String getCreatedFromDateStr() {
        return createdFromDateStr;
    }

    public void setCreatedFromDateStr(String createdFromDateStr) {
        this.createdFromDateStr = createdFromDateStr;
    }

    public String getCreatedToDateStr() {
        return createdToDateStr;
    }

    public void setCreatedToDateStr(String createdToDateStr) {
        this.createdToDateStr = createdToDateStr;
    }

    @Override
    public String toString() {
        return "ApplicationFormSearchRQ{" +
                "afRefNumber='" + afRefNumber + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", assignUser='" + assignUser + '\'' +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                ", currentApplicationFormStatus=" + currentApplicationFormStatus +
                ", identificationNumber='" + identificationNumber + '\'' +
                ", identificationType=" + identificationType +
                ", assignGroupUPMGroupCode='" + assignGroupUPMGroupCode + '\'' +
                ", assignDepartmentCode='" + assignDepartmentCode + '\'' +
                ", createdFromDateStr='" + createdFromDateStr + '\'' +
                ", createdToDateStr='" + createdToDateStr + '\'' +
                '}';
    }

}

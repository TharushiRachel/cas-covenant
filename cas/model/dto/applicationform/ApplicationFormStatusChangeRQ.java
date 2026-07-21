package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;

import java.util.ArrayList;
import java.util.List;

public class ApplicationFormStatusChangeRQ {

    private Integer applicationFormID;

    private String afRefNumber;

    private Integer assignUserID;

    private String assignADUserID;

    private String assignUser;

    private String assignUserDisplayName;

    private Integer assignUserUpmID;

    private String assignUserUpmGroupCode;

    private String assignUserDivCode;

    private String assignDepartmentCode;

    private String updatedByUserDisplayName;

    private List<AFAssignDepartmentDTO> afAssignDepartmentDTOList;

    private String actionMessage;

    private AFCommentDTO afCommentDTO;

    private DomainConstants.ApplicationFormStatus applicationFormStatus;

    private DomainConstants.ForwardType forwardType;

    private AppsConstants.YesNo isESGPaper;

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

    public DomainConstants.ApplicationFormStatus getApplicationFormStatus() {
        return applicationFormStatus;
    }

    public void setApplicationFormStatus(DomainConstants.ApplicationFormStatus applicationFormStatus) {
        this.applicationFormStatus = applicationFormStatus;
    }

    public AFCommentDTO getAfCommentDTO() {
        return afCommentDTO;
    }

    public void setAfCommentDTO(AFCommentDTO afCommentDTO) {
        this.afCommentDTO = afCommentDTO;
    }

    public DomainConstants.ForwardType getForwardType() {
        return forwardType;
    }

    public void setForwardType(DomainConstants.ForwardType forwardType) {
        this.forwardType = forwardType;
    }

    public String getAssignDepartmentCode() {
        return assignDepartmentCode;
    }

    public void setAssignDepartmentCode(String assignDepartmentCode) {
        this.assignDepartmentCode = assignDepartmentCode;
    }

    public List<AFAssignDepartmentDTO> getAfAssignDepartmentDTOList() {
        if (afAssignDepartmentDTOList == null) {
            this.afAssignDepartmentDTOList = new ArrayList<>();
        }
        return afAssignDepartmentDTOList;
    }

    public void setAfAssignDepartmentDTOList(List<AFAssignDepartmentDTO> afAssignDepartmentDTOList) {
        this.afAssignDepartmentDTOList = afAssignDepartmentDTOList;
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    public String getAssignUserDivCode() {
        return assignUserDivCode;
    }

    public void setAssignUserDivCode(String assignUserDivCode) {
        this.assignUserDivCode = assignUserDivCode;
    }

    public String getUpdatedByUserDisplayName() {
        return updatedByUserDisplayName;
    }

    public void setUpdatedByUserDisplayName(String updatedByUserDisplayName) {
        this.updatedByUserDisplayName = updatedByUserDisplayName;
    }

    @Override
    public String toString() {
        return "ApplicationFormStatusChangeRQ{" +
                "applicationFormID=" + applicationFormID +
                ", afRefNumber='" + afRefNumber + '\'' +
                ", assignUserID=" + assignUserID +
                ", assignADUserID='" + assignADUserID + '\'' +
                ", assignUser='" + assignUser + '\'' +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                ", assignUserUpmID=" + assignUserUpmID +
                ", assignUserUpmGroupCode='" + assignUserUpmGroupCode + '\'' +
                ", assignUserDivCode='" + assignUserDivCode + '\'' +
                ", assignDepartmentCode='" + assignDepartmentCode + '\'' +
                ", updatedByUserDisplayName='" + updatedByUserDisplayName + '\'' +
                ", afAssignDepartmentDTOList=" + afAssignDepartmentDTOList +
                ", actionMessage='" + actionMessage + '\'' +
                ", afCommentDTO=" + afCommentDTO +
                ", applicationFormStatus=" + applicationFormStatus +
                ", forwardType=" + forwardType +
                '}';
    }

    public AppsConstants.YesNo getIsESGPaper() {
        return isESGPaper;
    }

    public void setIsESGPaper(AppsConstants.YesNo isESGPaper) {
        this.isESGPaper = isESGPaper;
    }
}

package com.itechro.cas.model.domain.applicationform;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.util.Date;

public class AFStatusHistoryDTO implements Serializable {

    private Integer statusHistoryID;

    private Integer applicationFormID;

    private DomainConstants.ApplicationFormStatus applicationFormStatus;

    private String assignUser;

    private String updateBy;

    private String updatedUserDisplayName;

    private Date updateDate;

    private String updateDateStr;

    private String actionMessage;

    private Integer assignUserID;

    private String assignUserDisplayName;

    private Integer assignUserUpmID;

    private String assignUserUpmGroupCode;

    private String assignUserDivCode;

    private String assignDepartmentCode;

    private DomainConstants.ForwardType forwardType;

    public AFStatusHistoryDTO() {
    }

    public AFStatusHistoryDTO(AFStatusHistory afStatusHistory) {
        this.statusHistoryID = afStatusHistory.getStatusHistoryID();
        this.applicationFormID = afStatusHistory.getApplicationForm().getApplicationFormID();
        this.applicationFormStatus = afStatusHistory.getApplicationFormStatus();
        this.assignUser = afStatusHistory.getAssignUser();
        this.updateBy = afStatusHistory.getUpdateBy();
        this.updatedUserDisplayName = afStatusHistory.getUpdatedUserDisplayName();
        if (afStatusHistory.getUpdateDate() != null) {
            this.updateDateStr = CalendarUtil.getDefaultFormattedDateTime(afStatusHistory.getUpdateDate());
            this.updateDate = afStatusHistory.getUpdateDate();
        }
        this.actionMessage = afStatusHistory.getActionMessage();
        this.assignUserID = afStatusHistory.getAssignUserID();
        this.assignUserDisplayName = afStatusHistory.getAssignUserDisplayName();
        this.assignUserUpmID = afStatusHistory.getAssignUserUpmID();
        this.assignUserDivCode = afStatusHistory.getAssignUserDivCode();
        this.assignUserUpmGroupCode = afStatusHistory.getAssignUserUpmGroupCode();
        this.assignDepartmentCode = afStatusHistory.getAssignDepartmentCode();
        this.forwardType = afStatusHistory.getForwardType();
    }

    public Integer getStatusHistoryID() {
        return statusHistoryID;
    }

    public void setStatusHistoryID(Integer statusHistoryID) {
        this.statusHistoryID = statusHistoryID;
    }

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    public DomainConstants.ApplicationFormStatus getApplicationFormStatus() {
        return applicationFormStatus;
    }

    public void setApplicationFormStatus(DomainConstants.ApplicationFormStatus applicationFormStatus) {
        this.applicationFormStatus = applicationFormStatus;
    }

    public String getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(String assignUser) {
        this.assignUser = assignUser;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    public Integer getAssignUserID() {
        return assignUserID;
    }

    public void setAssignUserID(Integer assignUserID) {
        this.assignUserID = assignUserID;
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

    public String getUpdateDateStr() {
        return updateDateStr;
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }

    public DomainConstants.ForwardType getForwardType() {
        return forwardType;
    }

    public void setForwardType(DomainConstants.ForwardType forwardType) {
        this.forwardType = forwardType;
    }

    public String getAssignUserDivCode() {
        return assignUserDivCode;
    }

    public void setAssignUserDivCode(String assignUserDivCode) {
        this.assignUserDivCode = assignUserDivCode;
    }

    public String getUpdatedUserDisplayName() {
        return updatedUserDisplayName;
    }

    public void setUpdatedUserDisplayName(String updatedUserDisplayName) {
        this.updatedUserDisplayName = updatedUserDisplayName;
    }

    public String getAssignDepartmentCode() {
        return assignDepartmentCode;
    }

    public void setAssignDepartmentCode(String assignDepartmentCode) {
        this.assignDepartmentCode = assignDepartmentCode;
    }

    @Override
    public String toString() {
        return "AFStatusHistoryDTO{" +
                "statusHistoryID=" + statusHistoryID +
                ", applicationFormID=" + applicationFormID +
                ", applicationFormStatus=" + applicationFormStatus +
                ", assignUser='" + assignUser + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", updatedUserDisplayName='" + updatedUserDisplayName + '\'' +
                ", updateDate=" + updateDate +
                ", updateDateStr='" + updateDateStr + '\'' +
                ", actionMessage='" + actionMessage + '\'' +
                ", assignUserID=" + assignUserID +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                ", assignUserUpmID=" + assignUserUpmID +
                ", assignUserUpmGroupCode='" + assignUserUpmGroupCode + '\'' +
                ", assignUserDivCode='" + assignUserDivCode + '\'' +
                ", assignDepartmentCode='" + assignDepartmentCode + '\'' +
                ", forwardType=" + forwardType +
                '}';
    }
}

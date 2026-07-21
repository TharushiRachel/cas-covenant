package com.itechro.cas.model.dto.notification;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.notification.EmailUnSubscribe;
import com.itechro.cas.util.CalendarUtil;

public class EmailUnSubscribeDTO {

    private Integer emailUnSubscribeID;

    private String userName;

    private String userEmail;

    private AppsConstants.Status status;

    private String createdDateStr;

    private String createdBy;

    private String modifiedDateStr;

    private String modifiedBy;

    public EmailUnSubscribeDTO(){}

    public EmailUnSubscribeDTO(EmailUnSubscribe emailUnSubscribe){
        this.emailUnSubscribeID = emailUnSubscribe.getEmailUnSubscribeID();
        this.userName = emailUnSubscribe.getUserName();
        this.userEmail = emailUnSubscribe.getUserEmail();
        this.status = emailUnSubscribe.getStatus();
        if(emailUnSubscribe.getCreatedDate() != null) {
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(emailUnSubscribe.getCreatedDate());
        }
        if (emailUnSubscribe.getModifiedDate() != null) {
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(emailUnSubscribe.getModifiedDate());
        }
        this.createdBy = emailUnSubscribe.getCreatedBy();
        this.modifiedBy = emailUnSubscribe.getModifiedBy();
    }

    public Integer getEmailUnSubscribeID() {
        return emailUnSubscribeID;
    }

    public void setEmailUnSubscribeID(Integer emailUnSubscribeID) {
        this.emailUnSubscribeID = emailUnSubscribeID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public String getModifiedDateStr() {
        return modifiedDateStr;
    }

    public void setModifiedDateStr(String modifiedDateStr) {
        this.modifiedDateStr = modifiedDateStr;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public String toString() {
        return "EmailUnSubscribeDTO{" +
                "emailUnSubscribeID=" + emailUnSubscribeID +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", status=" + status +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedDateStr='" + modifiedDateStr + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                '}';
    }
}

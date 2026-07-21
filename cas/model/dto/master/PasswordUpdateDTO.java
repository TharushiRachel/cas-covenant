package com.itechro.cas.model.dto.master;

import com.itechro.cas.commons.constants.DomainConstants;

import java.io.Serializable;

public class PasswordUpdateDTO implements Serializable {

    private static final long serialVersionUID = -1962640883938445528L;

    private Integer userID;

    private DomainConstants.PasswordUpdateAction action;

    private String oldPassword;

    private String newPassword;

    private String emailAddress;

    private String userPasswordResetUUID;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public DomainConstants.PasswordUpdateAction getAction() {
        return action;
    }

    public void setAction(DomainConstants.PasswordUpdateAction action) {
        this.action = action;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUserPasswordResetUUID() {
        return userPasswordResetUUID;
    }

    public void setUserPasswordResetUUID(String userPasswordResetUUID) {
        this.userPasswordResetUUID = userPasswordResetUUID;
    }

    @Override
    public String toString() {
        return "PasswordUpdateDTO{" +
                "userID=" + userID +
                ", action=" + action +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", userPasswordResetUUID='" + userPasswordResetUUID + '\'' +
                '}';
    }
}

package com.itechro.cas.model.dto.notification;

import java.io.Serializable;

public class EmailUnSubscribeRQ implements Serializable {

    private String userName;

    private String userEmail;

    private boolean emailNotificationDisable;

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

    public boolean isEmailNotificationDisable() {
        return emailNotificationDisable;
    }

    public void setEmailNotificationDisable(boolean emailNotificationDisable) {
        this.emailNotificationDisable = emailNotificationDisable;
    }
}

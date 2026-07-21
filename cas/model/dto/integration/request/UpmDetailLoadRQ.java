package com.itechro.cas.model.dto.integration.request;

import java.io.Serializable;

public class UpmDetailLoadRQ implements Serializable {

    private String userID;

    private String appCode;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }
}

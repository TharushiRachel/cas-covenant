package com.itechro.cas.model.dto.integration.request;

import java.io.Serializable;

public class UmpDetailLoadByAdIdRQ implements Serializable {

    private static final long serialVersionUID = 3361083890708989529L;

    private String adUserID;

    private String appCode;

    public String getAdUserID() {
        return adUserID;
    }

    public void setAdUserID(String adUserID) {
        this.adUserID = adUserID;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    @Override
    public String toString() {
        return "UmpDetailLoadByAdIdRQ{" +
                "adUserID='" + adUserID + '\'' +
                ", appCode='" + appCode + '\'' +
                '}';
    }
}

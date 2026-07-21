package com.itechro.cas.model.dto.integration.request;

import java.io.Serializable;

public class BranchAuthorityLevelRQ implements Serializable {

    private String solId;

    private String roleId;

    private String appCode;

    public String getSolId() {
        return solId;
    }

    public void setSolId(String solId) {
        this.solId = solId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }
}

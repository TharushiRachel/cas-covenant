package com.itechro.cas.model.dto.integration.request;

import java.io.Serializable;

public class AssistantRQ implements Serializable {

    private String solId;

    private String roleId;

    private String appCode;

    private String functionCode1;

    private String functionCode2;

    private String functionCode3;

    private String upmGroupCode;

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

    public String getFunctionCode1() {
        return functionCode1;
    }

    public void setFunctionCode1(String functionCode1) {
        this.functionCode1 = functionCode1;
    }

    public String getFunctionCode2() {
        return functionCode2;
    }

    public void setFunctionCode2(String functionCode2) {
        this.functionCode2 = functionCode2;
    }

    public String getFunctionCode3() {
        return functionCode3;
    }

    public void setFunctionCode3(String functionCode3) {
        this.functionCode3 = functionCode3;
    }

    public String getUpmGroupCode() {
        return upmGroupCode;
    }

    public void setUpmGroupCode(String upmGroupCode) {
        this.upmGroupCode = upmGroupCode;
    }

    @Override
    public String toString() {
        return "AssistantRQ{" +
                "solId='" + solId + '\'' +
                ", roleId='" + roleId + '\'' +
                ", appCode='" + appCode + '\'' +
                ", functionCode1='" + functionCode1 + '\'' +
                ", functionCode2='" + functionCode2 + '\'' +
                ", functionCode3='" + functionCode3 + '\'' +
                ", upmGroupCode='" + upmGroupCode + '\'' +
                '}';
    }
}

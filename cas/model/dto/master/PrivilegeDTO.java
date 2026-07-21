package com.itechro.cas.model.dto.master;

import com.itechro.cas.commons.constants.AppsConstants;

import java.io.Serializable;

public class PrivilegeDTO implements Serializable {

    private Integer privilegeID;

    private String privilegeName;

    private String code;

    private String description;

    private String category;

    private String module;

    private AppsConstants.Status status;

    public Integer getPrivilegeID() {
        return privilegeID;
    }

    public void setPrivilegeID(Integer privilegeID) {
        this.privilegeID = privilegeID;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PrivilegeDTO{" +
                "privilegeID=" + privilegeID +
                ", privilegeName='" + privilegeName + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", module='" + module + '\'' +
                ", status=" + status +
                '}';
    }
}

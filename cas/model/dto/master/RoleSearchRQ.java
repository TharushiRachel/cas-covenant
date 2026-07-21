package com.itechro.cas.model.dto.master;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.common.PagedParamDTO;

public class RoleSearchRQ extends PagedParamDTO {

    private String roleName;

    private String upmPrivilegeCode;

    private AppsConstants.Status status;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUpmPrivilegeCode() {
        return upmPrivilegeCode;
    }

    public void setUpmPrivilegeCode(String upmPrivilegeCode) {
        this.upmPrivilegeCode = upmPrivilegeCode;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RoleSearchRQ{" +
                "roleName='" + roleName + '\'' +
                ", status=" + status +
                '}';
    }
}

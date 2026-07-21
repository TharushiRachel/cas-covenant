package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class RoleContentDTO extends BaseContentDTO {

    @SerializedName("ROLE ID")
    private Integer roleID;

    @SerializedName("ROLE NAME")
    private String roleName;

    @SerializedName("UPM PRIVILEGE CODE")
    private String upmPrivilegeCode;

    @SerializedName("APPROVE STATUS")
    private String approveStatus;

    @SerializedName("APPROVE DATE")
    private String approvedDateStr;

    @SerializedName("STATUS")
    private String status;

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

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

    public String getApprovedDateStr() {
        return approvedDateStr;
    }

    public void setApprovedDateStr(String approvedDateStr) {
        this.approvedDateStr = approvedDateStr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }
}

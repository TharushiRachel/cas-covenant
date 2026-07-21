package com.itechro.cas.model.dto.master;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.master.Privilege;
import com.itechro.cas.model.domain.master.Role;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RoleDTO implements Serializable {

    private Integer roleID;

    private String roleName;

    private String upmPrivilegeCode;

    private AppsConstants.Status status;

    private List<Integer> privileges;

    private List<Integer> addedPrivileges;

    private List<Integer> deletedPrivileges;

    private DomainConstants.MasterDataApproveStatus approveStatus;

    private String approvedDateStr;

    private String approvedBy;

    private String createdDateStr;

    private String createdBy;

    private String modifiedDateStr;

    private String modifiedBy;

    public RoleDTO() {

    }

    public RoleDTO(Role role) {
        this.roleID = role.getRoleID();
        this.roleName = role.getRoleName();
        this.upmPrivilegeCode = role.getUpmPrivilegeCode();
        this.status = role.getStatus();

        for (Privilege privilege : role.getPrivileges()) {
            this.getPrivileges().add(privilege.getPrivilegeID());
        }
        this.approveStatus = role.getApproveStatus();
        if(role.getApprovedDate()!=null){
            this.approvedDateStr = CalendarUtil.getDefaultFormattedDateTime(role.getApprovedDate());
        }
        this.approvedBy = role.getApprovedBy();


        if (role.getApprovedDate() != null) {
            this.approvedDateStr = CalendarUtil.getDefaultFormattedDateTime(role.getApprovedDate());
        }
        this.approvedBy = role.getApprovedBy();
        this.approveStatus = role.getApproveStatus();
        if(role.getCreatedDate() != null){
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(role.getCreatedDate());
        }
        this.createdBy = role.getCreatedBy();
        if(role.getModifiedDate() != null){
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(role.getModifiedDate());
        }
        this.modifiedBy = role.getModifiedBy();

    }

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

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public List<Integer> getPrivileges() {
        if (privileges == null) {
            privileges = new ArrayList<>();
        }
        return privileges;
    }

    public void setPrivileges(List<Integer> privileges) {
        this.privileges = privileges;
    }

    public List<Integer> getAddedPrivileges() {
        if (addedPrivileges == null) {
            this.addedPrivileges = new ArrayList<>();
        }
        return addedPrivileges;
    }

    public void setAddedPrivileges(List<Integer> addedPrivileges) {
        this.addedPrivileges = addedPrivileges;
    }

    public List<Integer> getDeletedPrivileges() {
        if (deletedPrivileges == null) {
            deletedPrivileges = new ArrayList<>();
        }
        return deletedPrivileges;
    }

    public void setDeletedPrivileges(List<Integer> deletedPrivileges) {
        this.deletedPrivileges = deletedPrivileges;
    }



    public DomainConstants.MasterDataApproveStatus getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(DomainConstants.MasterDataApproveStatus approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApprovedDateStr() {
        return approvedDateStr;
    }

    public void setApprovedDateStr(String approvedDateStr) {
        this.approvedDateStr = approvedDateStr;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedDateStr() {
        return modifiedDateStr;
    }

    public void setModifiedDateStr(String modifiedDateStr) {
        this.modifiedDateStr = modifiedDateStr;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "roleID=" + roleID +
                ", roleName='" + roleName + '\'' +
                ", status=" + status +
                ", privileges=" + privileges +
                ", addedPrivileges=" + addedPrivileges +
                ", deletedPrivileges=" + deletedPrivileges +
                ", approveStatus=" + approveStatus +
                ", approvedDateStr='" + approvedDateStr + '\'' +
                ", approvedBy='" + approvedBy + '\'' +
                '}';
    }


}

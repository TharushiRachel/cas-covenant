package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.model.domain.applicationform.AFAssignDepartment;

import java.io.Serializable;

public class AFAssignDepartmentDTO implements Serializable {

    private Integer afAssignDepartmentID;

    private Integer applicationFormID;

    private String divCode;

    private String departmentName;

    private String userGroupUPMCode;

    private String userGroupName;

    public AFAssignDepartmentDTO() {
    }

    public AFAssignDepartmentDTO(AFAssignDepartment afAssignDepartment) {
        this.afAssignDepartmentID = afAssignDepartment.getAfAssignDepartmentID();
        this.applicationFormID = afAssignDepartment.getApplicationForm().getApplicationFormID();
        this.divCode = afAssignDepartment.getDivCode();
        this.departmentName = afAssignDepartment.getDepartmentName();
        this.userGroupUPMCode = afAssignDepartment.getUserGroupUPMCode();
        this.userGroupName = afAssignDepartment.getUserGroupName();
    }

    public Integer getAfAssignDepartmentID() {
        return afAssignDepartmentID;
    }

    public void setAfAssignDepartmentID(Integer afAssignDepartmentID) {
        this.afAssignDepartmentID = afAssignDepartmentID;
    }

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    public String getDivCode() {
        return divCode;
    }

    public void setDivCode(String divCode) {
        this.divCode = divCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getUserGroupUPMCode() {
        return userGroupUPMCode;
    }

    public void setUserGroupUPMCode(String userGroupUPMCode) {
        this.userGroupUPMCode = userGroupUPMCode;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    @Override
    public String toString() {
        return "AFAssignDepartmentDTO{" +
                "afAssignDepartmentID=" + afAssignDepartmentID +
                ", applicationFormID=" + applicationFormID +
                ", divCode='" + divCode + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", userGroupUPMCode='" + userGroupUPMCode + '\'' +
                ", userGroupName='" + userGroupName + '\'' +
                '}';
    }
}

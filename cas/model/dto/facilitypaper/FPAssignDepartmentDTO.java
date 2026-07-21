package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.FPAssignDepartment;

import java.io.Serializable;

public class FPAssignDepartmentDTO implements Serializable {

    private Integer fpAssignDepartmentID;

    private Integer facilityPaperID;

    private String divCode;

    private String departmentName;

    private String userGroupUPMCode;

    private String userGroupName;

    private AppsConstants.Status status;

    public FPAssignDepartmentDTO() {
    }

    public FPAssignDepartmentDTO(FPAssignDepartment fpAssignDepartment) {
        this.fpAssignDepartmentID = fpAssignDepartment.getFpAssignDepartmentID();
        this.facilityPaperID = fpAssignDepartment.getFacilityPaper().getFacilityPaperID();
        this.divCode = fpAssignDepartment.getDivCode();
        this.departmentName = fpAssignDepartment.getDepartmentName();
        this.userGroupUPMCode = fpAssignDepartment.getUserGroupUPMCode();
        this.userGroupName = fpAssignDepartment.getUserGroupName();
        this.status = fpAssignDepartment.getStatus();
    }

    public Integer getFpAssignDepartmentID() {
        return fpAssignDepartmentID;
    }

    public void setFpAssignDepartmentID(Integer fpAssignDepartmentID) {
        this.fpAssignDepartmentID = fpAssignDepartmentID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
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

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

}

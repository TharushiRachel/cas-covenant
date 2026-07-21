package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;

import java.io.Serializable;

public class CASBranchDepartmentDTO implements Serializable {

    private Integer branchDepartmentID;

    private String branchDepartmentCasCode;

    private String branchDepartmentName;

    private String branchDepartmentDivCode;

    private AppsConstants.Status status;

    public CASBranchDepartmentDTO() {
    }

    public Integer getBranchDepartmentID() {
        return branchDepartmentID;
    }

    public void setBranchDepartmentID(Integer branchDepartmentID) {
        this.branchDepartmentID = branchDepartmentID;
    }

    public String getBranchDepartmentCasCode() {
        return branchDepartmentCasCode;
    }

    public void setBranchDepartmentCasCode(String branchDepartmentCasCode) {
        this.branchDepartmentCasCode = branchDepartmentCasCode;
    }

    public String getBranchDepartmentName() {
        return branchDepartmentName;
    }

    public void setBranchDepartmentName(String branchDepartmentName) {
        this.branchDepartmentName = branchDepartmentName;
    }

    public String getBranchDepartmentDivCode() {
        return branchDepartmentDivCode;
    }

    public void setBranchDepartmentDivCode(String branchDepartmentDivCode) {
        this.branchDepartmentDivCode = branchDepartmentDivCode;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CASBranchDepartmentDTO{" +
                "branchDepartmentID=" + branchDepartmentID +
                ", branchDepartmentCasCode='" + branchDepartmentCasCode + '\'' +
                ", branchDepartmentName='" + branchDepartmentName + '\'' +
                ", branchDepartmentDivCode='" + branchDepartmentDivCode + '\'' +
                ", status=" + status +
                '}';
    }
}

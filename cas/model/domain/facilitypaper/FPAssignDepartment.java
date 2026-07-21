package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;

import javax.persistence.*;

@Entity
@Table(name = "T_FP_ASSIGN_DEPARTMENT")
public class FPAssignDepartment extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_ASSIGN_DEPARTMENT")
    @SequenceGenerator(name = "SEQ_T_FP_ASSIGN_DEPARTMENT", sequenceName = "SEQ_T_FP_ASSIGN_DEPARTMENT", allocationSize = 1)
    @Column(name = "FP_ASSIGN_DEPARTMENT_ID")
    private Integer fpAssignDepartmentID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @Column(name = "DIV_CODE")
    private String divCode;

    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;

    @Column(name = "USER_GROUP_UPM_CODE")
    private String userGroupUPMCode;

    @Column(name = "USER_GROUP_NAME")
    private String userGroupName;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getFpAssignDepartmentID() {
        return fpAssignDepartmentID;
    }

    public void setFpAssignDepartmentID(Integer fpAssignDepartmentID) {
        this.fpAssignDepartmentID = fpAssignDepartmentID;
    }

    public FacilityPaper getFacilityPaper() {
        return facilityPaper;
    }

    public void setFacilityPaper(FacilityPaper facilityPaper) {
        this.facilityPaper = facilityPaper;
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

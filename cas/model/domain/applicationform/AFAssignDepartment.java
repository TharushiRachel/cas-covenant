package com.itechro.cas.model.domain.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_AF_ASSIGN_DEPARTMENT")
public class AFAssignDepartment extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_ASSIGN_DEPARTMENT")
    @SequenceGenerator(name = "SEQ_T_AF_ASSIGN_DEPARTMENT", sequenceName = "SEQ_T_AF_ASSIGN_DEPARTMENT", allocationSize = 1)
    @Column(name = "AF_ASSIGN_DEPARTMENT_ID")
    private Integer afAssignDepartmentID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATION_FORM_ID")
    private ApplicationForm applicationForm;

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

    public Integer getAfAssignDepartmentID() {
        return afAssignDepartmentID;
    }

    public void setAfAssignDepartmentID(Integer afAssignDepartmentID) {
        this.afAssignDepartmentID = afAssignDepartmentID;
    }

    public ApplicationForm getApplicationForm() {
        return applicationForm;
    }

    public void setApplicationForm(ApplicationForm applicationForm) {
        this.applicationForm = applicationForm;
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

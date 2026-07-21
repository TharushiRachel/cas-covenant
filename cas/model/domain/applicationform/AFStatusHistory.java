package com.itechro.cas.model.domain.applicationform;

import com.itechro.cas.commons.constants.DomainConstants;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_AF_STATUS_HISTORY")
public class AFStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_STATUS_HISTORY")
    @SequenceGenerator(name = "SEQ_T_AF_STATUS_HISTORY", sequenceName = "SEQ_T_AF_STATUS_HISTORY", allocationSize = 1)
    @Column(name = "AF_STATUS_HISTORY_ID")
    private Integer statusHistoryID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATION_FORM_ID")
    private ApplicationForm applicationForm;

    @Enumerated(EnumType.STRING)
    @Column(name = "APPLICATION_FROM_STATUS")
    private DomainConstants.ApplicationFormStatus applicationFormStatus;

    @Column(name = "ASSIGN_USER")
    private String assignUser;

    @Column(name = "REMARK") // Deprecated ,cause the remark will add as a comment
    private String remark;

    @Column(name = "UPDATE_BY")
    private String updateBy;

    @Column(name = "UPDATED_USER_DISPLAY_NAME", nullable = false)
    private String updatedUserDisplayName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updateDate;

    @Column(name = "ACTION_MESSAGE")
    private String actionMessage;

    @Column(name = "ASSIGN_USER_ID")
    private Integer assignUserID;

    @Column(name = "ASSIGN_USER_DISPLAY_NAME")
    private String assignUserDisplayName;

    @Column(name = "ASSIGN_USER_UPM_ID")
    private Integer assignUserUpmID;

    @Column(name = "ASSIGN_USER_UPM_GROUP_CODE")
    private String assignUserUpmGroupCode;

    @Column(name = "ASSIGN_USER_DIV_CODE")
    private String assignUserDivCode;

    @Column(name = "ASSIGN_DEPARTMENT_CODE")
    private String assignDepartmentCode; // this is for cluster forwarding (assign for Departments (SOL IDs) 900,827....)

    @Enumerated(EnumType.STRING)
    @Column(name = "FORWARDING_TYPE")
    private DomainConstants.ForwardType forwardType; // this filed will fill when forwarding the application form

    public Integer getStatusHistoryID() {
        return statusHistoryID;
    }

    public void setStatusHistoryID(Integer statusHistoryID) {
        this.statusHistoryID = statusHistoryID;
    }

    public ApplicationForm getApplicationForm() {
        return applicationForm;
    }

    public void setApplicationForm(ApplicationForm applicationForm) {
        this.applicationForm = applicationForm;
    }

    public DomainConstants.ApplicationFormStatus getApplicationFormStatus() {
        return applicationFormStatus;
    }

    public void setApplicationFormStatus(DomainConstants.ApplicationFormStatus applicationFormStatus) {
        this.applicationFormStatus = applicationFormStatus;
    }

    public String getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(String assignUser) {
        this.assignUser = assignUser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    public Integer getAssignUserID() {
        return assignUserID;
    }

    public void setAssignUserID(Integer assignUserID) {
        this.assignUserID = assignUserID;
    }

    public String getAssignUserDisplayName() {
        return assignUserDisplayName;
    }

    public void setAssignUserDisplayName(String assignUserDisplayName) {
        this.assignUserDisplayName = assignUserDisplayName;
    }

    public Integer getAssignUserUpmID() {
        return assignUserUpmID;
    }

    public void setAssignUserUpmID(Integer assignUserUpmID) {
        this.assignUserUpmID = assignUserUpmID;
    }

    public String getAssignUserUpmGroupCode() {
        return assignUserUpmGroupCode;
    }

    public void setAssignUserUpmGroupCode(String assignUserUpmGroupCode) {
        this.assignUserUpmGroupCode = assignUserUpmGroupCode;
    }

    public String getAssignDepartmentCode() {
        return assignDepartmentCode;
    }

    public void setAssignDepartmentCode(String assignDepartmentCode) {
        this.assignDepartmentCode = assignDepartmentCode;
    }

    public DomainConstants.ForwardType getForwardType() {
        return forwardType;
    }

    public void setForwardType(DomainConstants.ForwardType forwardType) {
        this.forwardType = forwardType;
    }

    public String getAssignUserDivCode() {
        return assignUserDivCode;
    }

    public void setAssignUserDivCode(String assignUserDivCode) {
        this.assignUserDivCode = assignUserDivCode;
    }

    public String getUpdatedUserDisplayName() {
        return updatedUserDisplayName;
    }

    public void setUpdatedUserDisplayName(String updatedUserDisplayName) {
        this.updatedUserDisplayName = updatedUserDisplayName;
    }
}

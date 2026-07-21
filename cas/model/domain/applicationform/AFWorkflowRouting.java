package com.itechro.cas.model.domain.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_AF_WORKFLOW_ROUTING")
public class AFWorkflowRouting extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_WORKFLOW_ROUTING")
    @SequenceGenerator(name = "SEQ_T_AF_WORKFLOW_ROUTING", sequenceName = "SEQ_T_AF_WORKFLOW_ROUTING", allocationSize = 1)
    @Column(name = "AF_WORKFLOW_ROUTING_ID")
    private Integer afWorkflowRoutingID;

    @JoinColumn(name = "APPLICATION_FORM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private ApplicationForm applicationForm;

    @Column(name = "CYCLE")
    private Integer cycle;

    @Column(name = "ROUTING_REMARKS")
    private String routingRemarks;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROUTING_STATUS")
    private DomainConstants.ApplicationFormRoutingStatus routingStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ASSIGN_DATE")
    private Date assignDate;

    @Column(name = "ASSIGN_USER_ID")
    private Integer assignUserID;

    @Column(name = "ASSIGN_USER")
    private String assignUser;

    @Column(name = "ASSIGN_USER_DISPLAY_NAME")
    private String assignUserDisplayName;

    @Column(name = "ASSIGN_USER_UPM_ID")
    private Integer assignUserUpmID;

    @Column(name = "ASSIGN_USER_UPM_GROUP_CODE")
    private String assignUserUpmGroupCode;

    @Column(name = "ASSIGN_DEPARTMENT_CODE")
    private String assignDepartmentCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getAfWorkflowRoutingID() {
        return afWorkflowRoutingID;
    }

    public void setAfWorkflowRoutingID(Integer afWorkflowRoutingID) {
        this.afWorkflowRoutingID = afWorkflowRoutingID;
    }

    public ApplicationForm getApplicationForm() {
        return applicationForm;
    }

    public void setApplicationForm(ApplicationForm applicationForm) {
        this.applicationForm = applicationForm;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public String getRoutingRemarks() {
        return routingRemarks;
    }

    public void setRoutingRemarks(String routingRemarks) {
        this.routingRemarks = routingRemarks;
    }

    public DomainConstants.ApplicationFormRoutingStatus getRoutingStatus() {
        return routingStatus;
    }

    public void setRoutingStatus(DomainConstants.ApplicationFormRoutingStatus routingStatus) {
        this.routingStatus = routingStatus;
    }

    public Date getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }

    public Integer getAssignUserID() {
        return assignUserID;
    }

    public void setAssignUserID(Integer assignUserID) {
        this.assignUserID = assignUserID;
    }

    public String getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(String assignUser) {
        this.assignUser = assignUser;
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

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}

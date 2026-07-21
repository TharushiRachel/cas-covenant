package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_FP_STATUS_HISTORY")
public class FPStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_STATUS_HISTORY")
    @SequenceGenerator(name = "SEQ_T_FP_STATUS_HISTORY", sequenceName = "SEQ_T_FP_STATUS_HISTORY", allocationSize = 1)
    @Column(name = "FP_STATUS_HISTORY_ID")
    private Integer fpStatusHistoryID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @Enumerated(EnumType.STRING)
    @Column(name = "FACILITY_PAPER_STATUS")
    private DomainConstants.FacilityPaperStatus facilityPaperStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "FORWARD_TYPE")
    private DomainConstants.ForwardType forwardType;

    @Column(name = "AUTHORITY_LEVEL")
    private String authorityLevel;

    @Column(name = "ASSIGN_USER")
    private String assignUser;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "WORK_FLOW_ORDER")
    private String workflowOrder;

    @Column(name = "UPDATE_BY")
    private String updateBy;

    @Column(name = "UPDATED_USER")
    private String updatedUser;

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
    private String assignDepartmentCode; // this is for cluster forwarding (assign for Departments (Div Codes) 900,827....)

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_PUBLIC")
    private AppsConstants.YesNo isPublic;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_DIVISION_ONLY")
    private AppsConstants.YesNo isDivisionOnly;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_USERS_ONLY")
    private AppsConstants.YesNo isUsersOnly;

    public Integer getFpStatusHistoryID() {
        return fpStatusHistoryID;
    }

    public void setFpStatusHistoryID(Integer fpStatusHistoryID) {
        this.fpStatusHistoryID = fpStatusHistoryID;
    }

    public FacilityPaper getFacilityPaper() {
        return facilityPaper;
    }

    public void setFacilityPaper(FacilityPaper facilityPaper) {
        this.facilityPaper = facilityPaper;
    }

    public DomainConstants.FacilityPaperStatus getFacilityPaperStatus() {
        return facilityPaperStatus;
    }

    public void setFacilityPaperStatus(DomainConstants.FacilityPaperStatus facilityPaperStatus) {
        this.facilityPaperStatus = facilityPaperStatus;
    }

    public String getAuthorityLevel() {
        return authorityLevel;
    }

    public void setAuthorityLevel(String authorityLevel) {
        this.authorityLevel = authorityLevel;
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

    public String getWorkflowOrder() {
        return workflowOrder;
    }

    public void setWorkflowOrder(String workflowOrder) {
        this.workflowOrder = workflowOrder;
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

    public String getAssignDepartmentCode() {
        return assignDepartmentCode;
    }

    public void setAssignDepartmentCode(String assignDepartmentCode) {
        this.assignDepartmentCode = assignDepartmentCode;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    public AppsConstants.YesNo getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(AppsConstants.YesNo isPublic) {
        this.isPublic = isPublic;
    }

    public AppsConstants.YesNo getIsDivisionOnly() {
        return isDivisionOnly;
    }

    public void setIsDivisionOnly(AppsConstants.YesNo isDivisionOnly) {
        this.isDivisionOnly = isDivisionOnly;
    }

    public AppsConstants.YesNo getIsUsersOnly() {
        return isUsersOnly;
    }

    public void setIsUsersOnly(AppsConstants.YesNo isUsersOnly) {
        this.isUsersOnly = isUsersOnly;
    }
}

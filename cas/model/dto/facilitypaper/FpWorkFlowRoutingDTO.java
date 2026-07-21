package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.facilitypaper.FPWorkflowRouting;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class FpWorkFlowRoutingDTO implements Serializable {

    private Integer fpWorkflowRoutingID;

    private FacilityPaper facilityPaper;

    private Integer workflowLevel;

    private String authorityLevel;

    private Integer cycle;

    private String routingRemarks;

    private DomainConstants.FacilityPaperRoutingStatus routingStatus;

    private String assignDateStr;

    private Integer assignUserID;

    private String assignUser;

    private String assignUserDisplayName;

    private Integer assignUserUpmID;

    private String assignUserUpmGroupCode;

    private String assignDepartmentCode;

    private AppsConstants.Status status;

    public FpWorkFlowRoutingDTO() {
    }

    FpWorkFlowRoutingDTO(FPWorkflowRouting fpWorkflowRouting) {

        this.fpWorkflowRoutingID = fpWorkflowRouting.getFpWorkflowRoutingID();
        this.facilityPaper = fpWorkflowRouting.getFacilityPaper();
        this.workflowLevel = fpWorkflowRouting.getWorkflowLevel();
        this.authorityLevel = fpWorkflowRouting.getAuthorityLevel();
        this.cycle = fpWorkflowRouting.getCycle();
        this.routingRemarks = fpWorkflowRouting.getRoutingRemarks();
        this.routingStatus = fpWorkflowRouting.getRoutingStatus();
        this.assignUserID = fpWorkflowRouting.getAssignUserID();
        this.assignUserDisplayName = fpWorkflowRouting.getAssignUserDisplayName();
        this.assignUserUpmID = fpWorkflowRouting.getAssignUserUpmID();
        this.assignUserUpmGroupCode = fpWorkflowRouting.getAssignUserUpmGroupCode();
        if (fpWorkflowRouting.getAssignDate() != null) {
            this.assignDateStr = CalendarUtil.getDefaultFormattedDateOnly(fpWorkflowRouting.getAssignDate());
        }
        this.status = fpWorkflowRouting.getStatus();

    }

    public Integer getFpWorkflowRoutingID() {
        return fpWorkflowRoutingID;
    }

    public void setFpWorkflowRoutingID(Integer fpWorkflowRoutingID) {
        this.fpWorkflowRoutingID = fpWorkflowRoutingID;
    }

    public FacilityPaper getFacilityPaper() {
        return facilityPaper;
    }

    public void setFacilityPaper(FacilityPaper facilityPaper) {
        this.facilityPaper = facilityPaper;
    }

    public Integer getWorkflowLevel() {
        return workflowLevel;
    }

    public void setWorkflowLevel(Integer workflowLevel) {
        this.workflowLevel = workflowLevel;
    }

    public String getAuthorityLevel() {
        return authorityLevel;
    }

    public void setAuthorityLevel(String authorityLevel) {
        this.authorityLevel = authorityLevel;
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

    public DomainConstants.FacilityPaperRoutingStatus getRoutingStatus() {
        return routingStatus;
    }

    public void setRoutingStatus(DomainConstants.FacilityPaperRoutingStatus routingStatus) {
        this.routingStatus = routingStatus;
    }

    public String getAssignDateStr() {
        return assignDateStr;
    }

    public void setAssignDateStr(String assignDateStr) {
        this.assignDateStr = assignDateStr;
    }

    public String getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(String assignUser) {
        this.assignUser = assignUser;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "FpWorkFlowRoutingDTO{" +
                "fpWorkflowRoutingID=" + fpWorkflowRoutingID +
                ", facilityPaper=" + facilityPaper +
                ", workflowLevel=" + workflowLevel +
                ", authorityLevel='" + authorityLevel + '\'' +
                ", cycle=" + cycle +
                ", routingRemarks='" + routingRemarks + '\'' +
                ", routingStatus=" + routingStatus +
                ", assignDateStr='" + assignDateStr + '\'' +
                ", assignUserID=" + assignUserID +
                ", assignUser='" + assignUser + '\'' +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                ", assignUserUpmID=" + assignUserUpmID +
                ", assignUserUpmGroupCode='" + assignUserUpmGroupCode + '\'' +
                ", assignDepartmentCode='" + assignDepartmentCode + '\'' +
                ", status=" + status +
                '}';
    }
}

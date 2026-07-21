package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.UpmGroup;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class UpmGroupDTO implements Serializable {

    private Integer upmGroupID;

    private String groupCode;

    private String referenceName;

    private Integer workFlowLevel;

    private AppsConstants.Status status;

    private DomainConstants.MasterDataApproveStatus approveStatus;

    private String approvedDateStr;

    private String approvedBy;

    private String createdDateStr;

    private String createdBy;

    private String modifiedDateStr;

    private String modifiedBy;

    public UpmGroupDTO(){}

    public UpmGroupDTO(UpmGroup upmGroup){
        this.upmGroupID = upmGroup.getUpmGroupID();
        this.groupCode = upmGroup.getGroupCode();
        this.referenceName = upmGroup.getReferenceName();
        this.workFlowLevel = upmGroup.getWorkFlowLevel();
        this.status = upmGroup.getStatus();
        this.approveStatus = upmGroup.getApproveStatus();
        if (upmGroup.getApprovedDate() != null) {
            this.approvedDateStr = CalendarUtil.getDefaultFormattedDateTime(upmGroup.getApprovedDate());
        }
        this.approvedBy = upmGroup.getApprovedBy();
        if(upmGroup.getCreatedDate() != null){
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(upmGroup.getCreatedDate());
        }
        this.createdBy = upmGroup.getCreatedBy();
        if(upmGroup.getModifiedDate() != null){
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(upmGroup.getModifiedDate());
        }
        this.modifiedBy = upmGroup.getModifiedBy();
    }

    public Integer getUpmGroupID() {
        return upmGroupID;
    }

    public void setUpmGroupID(Integer upmGroupID) {
        this.upmGroupID = upmGroupID;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public Integer getWorkFlowLevel() {
        return workFlowLevel;
    }

    public void setWorkFlowLevel(Integer workFlowLevel) {
        this.workFlowLevel = workFlowLevel;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
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
        return "UpmGroupDTO{" +
                "upmGroupID=" + upmGroupID +
                "groupCode=" + groupCode +
                "referenceName=" + referenceName +
                "workFlowLevel=" + workFlowLevel +
                "status=" + status +
                "approveStatus=" + approveStatus +
                "approvedDateStr=" + approvedDateStr +
                "approvedBy=" + approvedBy +
                '}';
    }
}

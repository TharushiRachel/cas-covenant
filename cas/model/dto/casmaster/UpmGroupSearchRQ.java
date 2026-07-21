package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.common.PagedParamDTO;

import java.io.Serializable;
import java.util.List;

public class UpmGroupSearchRQ extends PagedParamDTO implements Serializable {

    private Integer upmGroupID;

    private String groupCode;

    private String referenceName;

    private Integer workFlowLevel;

    private AppsConstants.Status status;

    private List<DomainConstants.MasterDataApproveStatus> approveStatusList;

    private String approvedDateStr;

    private String approvedBy;

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


    public List<DomainConstants.MasterDataApproveStatus> getApproveStatusList() {
        return approveStatusList;
    }

    public void setApproveStatusList(List<DomainConstants.MasterDataApproveStatus> approveStatusList) {
        this.approveStatusList = approveStatusList;
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

    @Override
    public String toString() {
        return "UpmGroupDTO{" +
                "upmGroupID=" + upmGroupID +
                "groupCode=" + groupCode +
                "referenceName=" + referenceName +
                "workFlowLevel=" + workFlowLevel +
                "status=" + status +
                "approveStatus=" + approveStatusList +
                "approvedDateStr=" + approvedDateStr +
                "approvedBy=" + approvedBy +
                '}';
    }
}

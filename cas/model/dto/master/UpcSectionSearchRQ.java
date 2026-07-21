package com.itechro.cas.model.dto.master;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.common.PagedParamDTO;

import java.io.Serializable;
import java.util.List;

public class UpcSectionSearchRQ extends PagedParamDTO implements Serializable {

    private Integer upcSectionID;

    private String upcSectionName;

    private String upcSectionDescription;

    private AppsConstants.Status status;

    private List<DomainConstants.MasterDataApproveStatus> approveStatusList;

    private String approvedDateStr;

    private String approvedBy;

    public Integer getUpcSectionID() {
        return upcSectionID;
    }

    public void setUpcSectionID(Integer upcSectionID) {
        this.upcSectionID = upcSectionID;
    }

    public String getUpcSectionName() {
        return upcSectionName;
    }

    public void setUpcSectionName(String upcSectionName) {
        this.upcSectionName = upcSectionName;
    }

    public String getUpcSectionDescription() {
        return upcSectionDescription;
    }

    public void setUpcSectionDescription(String upcSectionDescription) {
        this.upcSectionDescription = upcSectionDescription;
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
        return "UpcSectionSearchRQ{" +
                "upcSectionID=" + upcSectionID +
                ", upcSectionName='" + upcSectionName + '\'' +
                ", upcSectionDescription='" + upcSectionDescription + '\'' +
                ", status=" + status +
                ", approveStatus=" + approveStatusList +
                ", approvedDateStr='" + approvedDateStr + '\'' +
                ", approvedBy='" + approvedBy + '\'' +
                '}';
    }
}

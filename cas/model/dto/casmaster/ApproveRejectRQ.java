package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;

import java.io.Serializable;

public class ApproveRejectRQ implements Serializable {

    private Integer approveRejectDataID;

    private DomainConstants.MasterDataApproveStatus approveStatus;

    private String groupCode;

    private String userName;

    private AppsConstants.Status userStatus;

    public Integer getApproveRejectDataID() {
        return approveRejectDataID;
    }

    public void setApproveRejectDataID(Integer approveRejectDataID) {
        this.approveRejectDataID = approveRejectDataID;
    }

    public DomainConstants.MasterDataApproveStatus getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(DomainConstants.MasterDataApproveStatus approveStatus) {
        this.approveStatus = approveStatus;
    }

    @Override
    public String toString() {
        return "ApproveRejectRQ{" +
                "approveRejectDataID=" + approveRejectDataID +
                ", approveStatus=" + approveStatus +
                '}';
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public AppsConstants.Status getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(AppsConstants.Status userStatus) {
        this.userStatus = userStatus;
    }
}


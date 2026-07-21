package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.facility.CommitteePaper;
import com.itechro.cas.model.domain.facilitypaper.facility.CommitteeStatusHistory;

import java.io.Serializable;

public class CommitteeStatusHistoryDTO implements Serializable {

    private Integer committeeStatusHistoryID;

    private Integer committeePaperID;

    private Integer facilityPaperID;

    private String facilityPaperStatus;

    private String committeePaperStatus;

    private String actionMessage;

    private String createdUserDisplayName;

    private String createdBy;

    private String modifiedBy;

    private String pathType;

    private String isUserAtSameLevel;





    public CommitteeStatusHistoryDTO() {
    }

    public CommitteeStatusHistoryDTO(CommitteeStatusHistory committeeStatusHistory) {
        this.committeeStatusHistoryID = committeeStatusHistory.getCommitteeStatusHistoryID();
        this.committeePaperID = committeeStatusHistory.getCommitteePaper().getCommitteePaperID();
        this.facilityPaperID = committeeStatusHistory.getFacilityPaper().getFacilityPaperID();
        this.actionMessage = committeeStatusHistory.getActionMessage();
        this.facilityPaperStatus = committeeStatusHistory.getFacilityPaperStatus();
        this.committeePaperStatus = committeeStatusHistory.getCommitteePaperStatus();
        this.createdUserDisplayName = committeeStatusHistory.getCreatedUserDisplayName();
        this.createdBy = committeeStatusHistory.getCreatedBy();
        this.modifiedBy = committeeStatusHistory.getModifiedBy();
        this.pathType = committeeStatusHistory.getPathType();
    }

    public Integer getCommitteeStatusHistoryID() {
        return committeeStatusHistoryID;
    }

    public void setCommitteeStatusHistoryID(Integer committeeStatusHistoryID) {
        this.committeeStatusHistoryID = committeeStatusHistoryID;
    }

    public Integer getCommitteePaperID() {
        return committeePaperID;
    }

    public void setCommitteePaperID(Integer committeePaperID) {
        this.committeePaperID = committeePaperID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public String getFacilityPaperStatus() {
        return facilityPaperStatus;
    }

    public void setFacilityPaperStatus(String facilityPaperStatus) {
        this.facilityPaperStatus = facilityPaperStatus;
    }

    public String getCommitteePaperStatus() {
        return committeePaperStatus;
    }

    public void setCommitteePaperStatus(String committeePaperStatus) {
        this.committeePaperStatus = committeePaperStatus;
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    public String getCreatedUserDisplayName() {
        return createdUserDisplayName;
    }

    public void setCreatedUserDisplayName(String createdUserDisplayName) {
        this.createdUserDisplayName = createdUserDisplayName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getPathType() {
        return pathType;
    }

    public void setPathType(String pathType) {
        this.pathType = pathType;
    }


    public String getIsUserAtSameLevel() {
        return isUserAtSameLevel;
    }

    public void setIsUserAtSameLevel(String isUserAtSameLevel) {
        this.isUserAtSameLevel = isUserAtSameLevel;
    }

    @Override
    public String toString() {
        return "CommitteeStatusHistoryDTO{" +
                "committeeStatusHistoryID=" + committeeStatusHistoryID +
                ", committeePaperID=" + committeePaperID +
                ", facilityPaperID=" + facilityPaperID +
                ", facilityPaperStatus='" + facilityPaperStatus + '\'' +
                ", committeePaperStatus='" + committeePaperStatus + '\'' +
                ", actionMessage='" + actionMessage + '\'' +
                ", createdUserDisplayName='" + createdUserDisplayName + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", pathType='" + pathType + '\'' +
                ", isUserAtSameLevel='" + isUserAtSameLevel + '\'' +
                '}';
    }
}

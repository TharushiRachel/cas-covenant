package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.facility.CommitteePaper;
import com.itechro.cas.model.domain.facilitypaper.facility.CommitteeStatusHistory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommitteePaperDTO implements Serializable {

    private Integer committeePaperID;

    private Integer facilityPaperID;

    private Integer committeeID;

    private String committeeType;

    private Integer committeeTypeID;

    private String committeeName;

    private Integer currentRegLevelID;

    private Integer currentRegRecommendedCount;

    private Integer currentAltLevelID;

    private Integer currentAltRecommendedCount;

    private String currentCommitteePaperStatus;

    private String createdUserDisplayName;

    private String createdBy;

    private String modifiedBy;

    private String currentPath;

    private String status;

    private List<CommitteeStatusHistoryDTO> committeeStatusHistoryList;


    public CommitteePaperDTO() {
    }

    public CommitteePaperDTO(CommitteePaper committeePaper) {
        this.committeePaperID = committeePaper.getCommitteePaperID();
        this.facilityPaperID = committeePaper.getFacilityPaper().getFacilityPaperID();
        this.committeeID = committeePaper.getCommitteeID();
        this.committeeType = committeePaper.getCommitteeType();
        this.committeeTypeID = committeePaper.getCommitteeTypeID();
        this.committeeName = committeePaper.getCommitteeName();
        this.currentCommitteePaperStatus = committeePaper.getCurrentCommitteePaperStatus();
        this.createdUserDisplayName = committeePaper.getCreatedUserDisplayName();
        this.createdBy = committeePaper.getCreatedBy();
        this.modifiedBy = committeePaper.getModifiedBy();
        this.currentRegLevelID = committeePaper.getCurrentRegLevelID();
        this.currentRegRecommendedCount = committeePaper.getCurrentRegRecommendedCount();
        this.currentAltLevelID = committeePaper.getCurrentAltLevelID();
        this.currentAltRecommendedCount = committeePaper.getCurrentAltRecommendedCount();
        this.currentPath = committeePaper.getCurrentPath();
        this.status = committeePaper.getStatus();

        if (!committeePaper.getCommitteeStatusHistorySet().isEmpty()) {
            for (CommitteeStatusHistory committeeStatusHistory : committeePaper.getCommitteeStatusHistorySet()) {
                this.getCommitteeStatusHistoryList().add(new CommitteeStatusHistoryDTO(committeeStatusHistory));
            }
        }
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

    public Integer getCommitteeID() {
        return committeeID;
    }

    public void setCommitteeID(Integer committeeID) {
        this.committeeID = committeeID;
    }

    public String getCommitteeType() {
        return committeeType;
    }

    public void setCommitteeType(String committeeType) {
        this.committeeType = committeeType;
    }

    public Integer getCommitteeTypeID() {
        return committeeTypeID;
    }

    public void setCommitteeTypeID(Integer committeeTypeID) {
        this.committeeTypeID = committeeTypeID;
    }

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }

    public Integer getCurrentRegLevelID() {
        return currentRegLevelID;
    }

    public void setCurrentRegLevelID(Integer currentRegLevelID) {
        this.currentRegLevelID = currentRegLevelID;
    }

    public Integer getCurrentRegRecommendedCount() {
        return currentRegRecommendedCount;
    }

    public void setCurrentRegRecommendedCount(Integer currentRegRecommendedCount) {
        this.currentRegRecommendedCount = currentRegRecommendedCount;
    }

    public Integer getCurrentAltLevelID() {
        return currentAltLevelID;
    }

    public void setCurrentAltLevelID(Integer currentAltLevelID) {
        this.currentAltLevelID = currentAltLevelID;
    }

    public Integer getCurrentAltRecommendedCount() {
        return currentAltRecommendedCount;
    }

    public void setCurrentAltRecommendedCount(Integer currentAltRecommendedCount) {
        this.currentAltRecommendedCount = currentAltRecommendedCount;
    }

    public String getCurrentCommitteePaperStatus() {
        return currentCommitteePaperStatus;
    }

    public void setCurrentCommitteePaperStatus(String currentCommitteePaperStatus) {
        this.currentCommitteePaperStatus = currentCommitteePaperStatus;
    }

    public List<CommitteeStatusHistoryDTO> getCommitteeStatusHistoryList() {
        if (committeeStatusHistoryList == null) {
            this.committeeStatusHistoryList = new ArrayList<>();
        }
        return committeeStatusHistoryList;
    }

    public void setCommitteeStatusHistoryList(List<CommitteeStatusHistoryDTO> committeeStatusHistoryList) {
        this.committeeStatusHistoryList = committeeStatusHistoryList;
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

    public String getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CommitteePaperDTO{" +
                "committeePaperID=" + committeePaperID +
                ", facilityPaperID=" + facilityPaperID +
                ", committeeID=" + committeeID +
                ", committeeType='" + committeeType + '\'' +
                ", committeeTypeID=" + committeeTypeID +
                ", committeeName='" + committeeName + '\'' +
                ", currentRegLevelID=" + currentRegLevelID +
                ", currentRegRecommendedCount=" + currentRegRecommendedCount +
                ", currentAltLevelID=" + currentAltLevelID +
                ", currentAltRecommendedCount=" + currentAltRecommendedCount +
                ", currentCommitteePaperStatus='" + currentCommitteePaperStatus + '\'' +
                ", createdUserDisplayName='" + createdUserDisplayName + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", currentPath='" + currentPath + '\'' +
                ", status='" + status + '\'' +
                ", committeeStatusHistoryList=" + committeeStatusHistoryList +
                '}';
    }
}

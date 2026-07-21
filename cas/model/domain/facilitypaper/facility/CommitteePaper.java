package com.itechro.cas.model.domain.facilitypaper.facility;


import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CA_COMMITTEE_PAPER")
public class CommitteePaper extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CA_COMMITTEE_PAPER")
    @SequenceGenerator(name = "SEQ_CA_COMMITTEE_PAPER", sequenceName = "SEQ_CA_COMMITTEE_PAPER", allocationSize = 1)
    @Column(name = "COMMITTEE_PAPER_ID")
    private Integer committeePaperID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @Column(name = "COMMITTEE_TYPE")
    private String committeeType;

    @Column(name = "COMMITTEE_TYPE_ID")
    private Integer committeeTypeID;

    @Column(name = "COMMITTEE_ID")
    private Integer committeeID;

    @Column(name = "COMMITTEE_NAME")
    private String committeeName;

    @Column(name = "CURRENT_REG_LEVEL_ID")
    private Integer currentRegLevelID;

    @Column(name = "CURRENT_REG_RECOMMENDED_COUNT")
    private Integer currentRegRecommendedCount;

    @Column(name = "CURRENT_ALT_LEVEL_ID")
    private Integer currentAltLevelID;

    @Column(name = "CURRENT_ALT_RECOMMENDED_COUNT")
    private Integer currentAltRecommendedCount;

    @Column(name = "CURRENT_COMMITTEE_PAPER_STATUS")
    private String currentCommitteePaperStatus;

    @Column(name = "CREATED_USER_DISPLAY_NAME")
    private String createdUserDisplayName;

    @Column(name = "CURRENT_PATH")
    private String currentPath;

    @Column(name = "STATUS")
    private String status;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "committeePaper")
//    @NotAudited
    private Set<CommitteeStatusHistory> committeeStatusHistorySet;


    public Set<CommitteeStatusHistory> getCommitteeStatusHistorySet() {
        if (committeeStatusHistorySet == null) {
            this.committeeStatusHistorySet = new HashSet<>();
        }
        return committeeStatusHistorySet;
    }

    public void setCommitteeStatusHistorySet(Set<CommitteeStatusHistory> committeeStatusHistorySet) {
        this.committeeStatusHistorySet = committeeStatusHistorySet;
    }

    public void addCommitteeStatusHistory(CommitteeStatusHistory committeeStatusHistorySet) {
        committeeStatusHistorySet.setCommitteePaper(this);
        this.getCommitteeStatusHistorySet().add(committeeStatusHistorySet);
    }

    public CommitteeStatusHistory getCommitteeStatusHistoryByID(Integer committeeStatusHistoryID) {
        return this.getCommitteeStatusHistorySet().stream().filter(committeeStatusHistory ->
                        committeeStatusHistoryID.equals(committeeStatusHistory.getCommitteeStatusHistoryID()))
                .findFirst().orElse(null);
    }

    public Integer getCommitteePaperID() {
        return committeePaperID;
    }

    public void setCommitteePaperID(Integer committeePaperID) {
        this.committeePaperID = committeePaperID;
    }

    public FacilityPaper getFacilityPaper() {
        return facilityPaper;
    }

    public void setFacilityPaper(FacilityPaper facilityPaper) {
        this.facilityPaper = facilityPaper;
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

    public Integer getCommitteeID() {
        return committeeID;
    }

    public void setCommitteeID(Integer committeeID) {
        this.committeeID = committeeID;
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

    public String getCreatedUserDisplayName() {
        return createdUserDisplayName;
    }

    public void setCreatedUserDisplayName(String createdUserDisplayName) {
        this.createdUserDisplayName = createdUserDisplayName;
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
}



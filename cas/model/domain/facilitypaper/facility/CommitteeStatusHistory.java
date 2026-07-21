package com.itechro.cas.model.domain.facilitypaper.facility;


import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "CA_COMMITTEE_STATUS_HISTORY")
public class CommitteeStatusHistory extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CA_COMMITTEE_STATUS_HISTORY")
    @SequenceGenerator(name = "SEQ_CA_COMMITTEE_STATUS_HISTORY", sequenceName = "SEQ_CA_COMMITTEE_STATUS_HISTORY", allocationSize = 1)
    @Column(name = "COMMITTEE_STATUS_HISTORY_ID")
    private Integer committeeStatusHistoryID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMITTEE_PAPER_ID")
    private CommitteePaper committeePaper;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @Column(name = "FACILITY_PAPER_STATUS")
    private String facilityPaperStatus;

    @Column(name = "COMMITTEE_PAPER_STATUS")
    private String committeePaperStatus;

    @Column(name = "ACTION_MESSAGE")
    private String actionMessage;

    @Column(name = "CREATED_USER_DISPLAY_NAME")
    private String createdUserDisplayName;

    @Column(name = "PATH_TYPE")
    private String pathType;



    public Integer getCommitteeStatusHistoryID() {
        return committeeStatusHistoryID;
    }

    public void setCommitteeStatusHistoryID(Integer committeeStatusHistoryID) {
        this.committeeStatusHistoryID = committeeStatusHistoryID;
    }

    public CommitteePaper getCommitteePaper() {
        return committeePaper;
    }

    public void setCommitteePaper(CommitteePaper committeePaper) {
        this.committeePaper = committeePaper;
    }

    public FacilityPaper getFacilityPaper() {
        return facilityPaper;
    }

    public void setFacilityPaper(FacilityPaper facilityPaper) {
        this.facilityPaper = facilityPaper;
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

    public String getPathType() {
        return pathType;
    }

    public void setPathType(String pathType) {
        this.pathType = pathType;
    }
}



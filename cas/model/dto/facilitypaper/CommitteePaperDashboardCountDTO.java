package com.itechro.cas.model.dto.facilitypaper;

import java.io.Serializable;

public class CommitteePaperDashboardCountDTO implements Serializable {

    private static final long serialVersionUID = 3762005148584521059L;

    private Integer inboxCommitteePaper;

    private Integer inProgressCommitteePaper;

    private Integer returnedCommitteePaper;

    private Integer approvedCommitteePaper;

    private Integer declinedCommitteePaper;


    public CommitteePaperDashboardCountDTO() {
        this.inboxCommitteePaper = 0;
        this.inProgressCommitteePaper = 0;
        this.returnedCommitteePaper = 0;
        this.approvedCommitteePaper= 0;
        this.declinedCommitteePaper = 0;
    }

    public Integer getInboxCommitteePaper() {
        return inboxCommitteePaper;
    }

    public void setInboxCommitteePaper(Integer inboxCommitteePaper) {
        this.inboxCommitteePaper = inboxCommitteePaper;
    }

    public Integer getInProgressCommitteePaper() {
        return inProgressCommitteePaper;
    }

    public void setInProgressCommitteePaper(Integer inProgressCommitteePaper) {
        this.inProgressCommitteePaper = inProgressCommitteePaper;
    }

    public Integer getReturnedCommitteePaper() {
        return returnedCommitteePaper;
    }

    public void setReturnedCommitteePaper(Integer returnedCommitteePaper) {
        this.returnedCommitteePaper = returnedCommitteePaper;
    }

    public Integer getApprovedCommitteePaper() {
        return approvedCommitteePaper;
    }

    public void setApprovedCommitteePaper(Integer approvedCommitteePaper) {
        this.approvedCommitteePaper = approvedCommitteePaper;
    }

    public Integer getDeclinedCommitteePaper() {
        return declinedCommitteePaper;
    }

    public void setDeclinedCommitteePaper(Integer declinedCommitteePaper) {
        this.declinedCommitteePaper = declinedCommitteePaper;
    }

    @Override
    public String toString() {
        return "CommitteePaperDashboardCountDTO{" +
                "inboxCommitteePaper=" + inboxCommitteePaper +
                ", inProgressCommitteePaper=" + inProgressCommitteePaper +
                ", returnedCommitteePaper=" + returnedCommitteePaper +
                ", approvedCommitteePaper=" + approvedCommitteePaper +
                ", declinedCommitteePaper=" + declinedCommitteePaper +
                '}';
    }
}

package com.itechro.cas.model.dto.facilitypaper;

import java.io.Serializable;

public class BCCPaperDashboardCountDTO implements Serializable {

    private static final long serialVersionUID = 3762005148584521059L;

    private Integer inboxBCCPaper;

    private Integer inProgressBCCPaper;
    private Integer approvedBCCPaper;

    private Integer declinedBCCPaper;


    public BCCPaperDashboardCountDTO() {
        this.inboxBCCPaper = 0;
        inProgressBCCPaper = 0;
        this.approvedBCCPaper = 0;
        this.declinedBCCPaper = 0;

    }

    public Integer getInboxBCCPaper() {
        return inboxBCCPaper;
    }

    public void setInboxBCCPaper(Integer inboxBCCPaper) {
        this.inboxBCCPaper = inboxBCCPaper;
    }

    public Integer getApprovedBCCPaper() {
        return approvedBCCPaper;
    }

    public void setApprovedBCCPaper(Integer approvedBCCPaper) {
        this.approvedBCCPaper = approvedBCCPaper;
    }

    public Integer getDeclinedBCCPaper() {
        return declinedBCCPaper;
    }

    public void setDeclinedBCCPaper(Integer declinedBCCPaper) {
        this.declinedBCCPaper = declinedBCCPaper;
    }

    public Integer getInProgressBCCPaper() {
        return inProgressBCCPaper;
    }

    public void setInProgressBCCPaper(Integer inProgressBCCPaper) {
        this.inProgressBCCPaper = inProgressBCCPaper;
    }

    @Override
    public String toString() {
        return "BCCPaperDashboardCountDTO{" +
                "inboxBCCPaper=" + inboxBCCPaper +
                ", inProgressBCCPaper=" + inProgressBCCPaper +
                ", approvedBCCPaper=" + approvedBCCPaper +
                ", declinedBCCPaper=" + declinedBCCPaper +
                '}';
    }
}

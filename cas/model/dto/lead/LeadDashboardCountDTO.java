package com.itechro.cas.model.dto.lead;

import java.io.Serializable;

public class LeadDashboardCountDTO implements Serializable {

    private static final long serialVersionUID = 3762005148584521059L;

    private Integer inboxLead;

    private Integer inProgressLead;

    private Integer acceptedLead;

    private Integer declinedLead;

    private Integer returnedLead;

    private Integer paperApprovedLead;

    private Integer dashboardTimePeriodDays;

    public LeadDashboardCountDTO() {
        this.inboxLead = 0;
        this.inProgressLead = 0;
        this.acceptedLead = 0;
        this.declinedLead = 0;
        this.returnedLead = 0;
        this.paperApprovedLead = 0;
        this.dashboardTimePeriodDays = 0;
    }

    public Integer getInboxLead() {
        return inboxLead;
    }

    public void setInboxLead(Integer inboxLead) {
        this.inboxLead = inboxLead;
    }

    public Integer getInProgressLead() {
        return inProgressLead;
    }

    public void setInProgressLead(Integer inProgressLead) {
        this.inProgressLead = inProgressLead;
    }

    public Integer getAcceptedLead() {
        return acceptedLead;
    }

    public void setAcceptedLead(Integer acceptedLead) {
        this.acceptedLead = acceptedLead;
    }

    public Integer getDeclinedLead() {
        return declinedLead;
    }

    public void setDeclinedLead(Integer declinedLead) {
        this.declinedLead = declinedLead;
    }

    public Integer getReturnedLead() {
        return returnedLead;
    }

    public void setReturnedLead(Integer returnedLead) {
        this.returnedLead = returnedLead;
    }

    public Integer getPaperApprovedLead() {
        return paperApprovedLead;
    }

    public void setPaperApprovedLead(Integer paperApprovedLead) {
        this.paperApprovedLead = paperApprovedLead;
    }

    public Integer getDashboardTimePeriodDays() {
        return dashboardTimePeriodDays;
    }

    public void setDashboardTimePeriodDays(Integer dashboardTimePeriodDays) {
        this.dashboardTimePeriodDays = dashboardTimePeriodDays;
    }

    @Override
    public String toString() {
        return "LeadDashboardCountDTO{" +
                "inboxLead=" + inboxLead +
                ", inProgressLead=" + inProgressLead +
                ", acceptedLead=" + acceptedLead +
                ", declinedLead=" + declinedLead +
                ", returnedLead=" + returnedLead +
                ", paperApprovedLead=" + paperApprovedLead +
                ", dashboardTimePeriodDays=" + dashboardTimePeriodDays +
                '}';
    }
}

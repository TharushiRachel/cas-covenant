package com.itechro.cas.model.dto.applicationform;

import java.io.Serializable;

public class ApplicationFormDashboardCountDTO implements Serializable {

    private static final long serialVersionUID = 3762005148584521059L;

    private Integer inboxApplicationForm;

    private Integer inProgressApplicationForm;

    private Integer acceptedApplicationForm;

    private Integer declinedApplicationForm;

    private Integer returnedApplicationForm;

    private Integer paperApprovedApplicationForm;

    private Integer dashboardTimePeriodDays;

    public ApplicationFormDashboardCountDTO() {
        this.inboxApplicationForm = 0;
        this.inProgressApplicationForm = 0;
        this.acceptedApplicationForm = 0;
        this.declinedApplicationForm= 0;
        this.returnedApplicationForm = 0;
        this.paperApprovedApplicationForm = 0;
        this.dashboardTimePeriodDays = 0;
    }

    public Integer getInboxApplicationForm() {
        return inboxApplicationForm;
    }

    public void setInboxApplicationForm(Integer inboxApplicationForm) {
        this.inboxApplicationForm = inboxApplicationForm;
    }

    public Integer getInProgressApplicationForm() {
        return inProgressApplicationForm;
    }

    public void setInProgressApplicationForm(Integer inProgressApplicationForm) {
        this.inProgressApplicationForm = inProgressApplicationForm;
    }

    public Integer getAcceptedApplicationForm() {
        return acceptedApplicationForm;
    }

    public void setAcceptedApplicationForm(Integer acceptedApplicationForm) {
        this.acceptedApplicationForm = acceptedApplicationForm;
    }

    public Integer getDeclinedApplicationForm() {
        return declinedApplicationForm;
    }

    public void setDeclinedApplicationForm(Integer declinedApplicationForm) {
        this.declinedApplicationForm = declinedApplicationForm;
    }

    public Integer getReturnedApplicationForm() {
        return returnedApplicationForm;
    }

    public void setReturnedApplicationForm(Integer returnedApplicationForm) {
        this.returnedApplicationForm = returnedApplicationForm;
    }

    public Integer getPaperApprovedApplicationForm() {
        return paperApprovedApplicationForm;
    }

    public void setPaperApprovedApplicationForm(Integer paperApprovedApplicationForm) {
        this.paperApprovedApplicationForm = paperApprovedApplicationForm;
    }

    public Integer getDashboardTimePeriodDays() {
        return dashboardTimePeriodDays;
    }

    public void setDashboardTimePeriodDays(Integer dashboardTimePeriodDays) {
        this.dashboardTimePeriodDays = dashboardTimePeriodDays;
    }

    @Override
    public String toString() {
        return "ApplicationFormDashboardCountDTO{" +
                "inboxApplicationForm=" + inboxApplicationForm +
                ", inProgressApplicationForm=" + inProgressApplicationForm +
                ", acceptedApplicationForm=" + acceptedApplicationForm +
                ", declinedApplicationForm=" + declinedApplicationForm +
                ", returnedApplicationForm=" + returnedApplicationForm +
                ", paperApprovedApplicationForm=" + paperApprovedApplicationForm +
                ", dashboardTimePeriodDays=" + dashboardTimePeriodDays +
                '}';
    }
}

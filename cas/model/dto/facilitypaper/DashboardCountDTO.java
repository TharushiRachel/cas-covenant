package com.itechro.cas.model.dto.facilitypaper;

import java.io.Serializable;

public class DashboardCountDTO implements Serializable {

    private static final long serialVersionUID = 3762005148584521059L;

    private Integer draftFacilityPaper;

    private Integer inProgressFacilityPaper;

    private Integer approvedFacilityPaper;

    private Integer rejectedFacilityPaper;

    private Integer cancelFacilityPaper;

    private Integer reviewRejectedPaper;

    public Integer getDraftFacilityPaper() {
        return draftFacilityPaper;
    }

    public void setDraftFacilityPaper(Integer draftFacilityPaper) {
        this.draftFacilityPaper = draftFacilityPaper;
    }

    public Integer getInProgressFacilityPaper() {
        return inProgressFacilityPaper;
    }

    public void setInProgressFacilityPaper(Integer inProgressFacilityPaper) {
        this.inProgressFacilityPaper = inProgressFacilityPaper;
    }

    public Integer getApprovedFacilityPaper() {
        return approvedFacilityPaper;
    }

    public void setApprovedFacilityPaper(Integer approvedFacilityPaper) {
        this.approvedFacilityPaper = approvedFacilityPaper;
    }

    public Integer getRejectedFacilityPaper() {
        return rejectedFacilityPaper;
    }

    public void setRejectedFacilityPaper(Integer rejectedFacilityPaper) {
        this.rejectedFacilityPaper = rejectedFacilityPaper;
    }

    public Integer getCancelFacilityPaper() {
        return cancelFacilityPaper;
    }

    public void setCancelFacilityPaper(Integer cancelFacilityPaper) {
        this.cancelFacilityPaper = cancelFacilityPaper;
    }

    public Integer getReviewRejectedPaper() {
        return reviewRejectedPaper;
    }

    public void setReviewRejectedPaper(Integer reviewRejectedPaper) {
        this.reviewRejectedPaper = reviewRejectedPaper;
    }

    @Override
    public String toString() {
        return "DashboardCountDTO{" +
                "draftFacilityPaper=" + draftFacilityPaper +
                ", inProgressFacilityPaper=" + inProgressFacilityPaper +
                ", approvedFacilityPaper=" + approvedFacilityPaper +
                ", rejectedFacilityPaper=" + rejectedFacilityPaper +
                ", cancelFacilityPaper=" + cancelFacilityPaper +
                ", reviewRejectedPaper=" + reviewRejectedPaper +
                '}';
    }
}

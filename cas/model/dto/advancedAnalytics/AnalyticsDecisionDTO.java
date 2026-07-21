package com.itechro.cas.model.dto.advancedAnalytics;

import com.itechro.cas.model.domain.advanceAnalytics.AnalyticsDecision;
import lombok.Data;

import java.util.Date;

@Data
public class AnalyticsDecisionDTO {

    private Integer decisionId;

    private Integer leadId;

    private String decision;

    private String decisionStatus;

    private String channel;

    private String facilityData;

    private String createdBy;

    private Date createdDate;

    private String createdDateStr;

    public AnalyticsDecisionDTO() {
    }

    public AnalyticsDecisionDTO(AnalyticsDecision analyticsDecision) {
        this.decisionId = analyticsDecision.getDecisionId();
        this.leadId = analyticsDecision.getLeadId();
        this.decision = analyticsDecision.getDecision();
        this.decisionStatus = analyticsDecision.getDecisionStatus();
        this.channel = analyticsDecision.getChannel();
        this.facilityData = analyticsDecision.getFacilityData();
        this.createdBy = analyticsDecision.getCreatedBy();
        this.createdDate = analyticsDecision.getCreatedDate();
    }
}

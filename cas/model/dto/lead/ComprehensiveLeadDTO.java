package com.itechro.cas.model.dto.lead;

import com.itechro.cas.model.dto.advancedAnalytics.AnalyticsDecisionDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ComprehensiveLeadDTO {

    private Long compLeadId;
    private Integer leadId;
    private String leadName;
    private String creationType;
    private String preferredBranch;
    private Date createdDate;
    private String createdBy;
    private Date modifiedDate;
    private String modifiedBy;
    private AnalyticsDecisionDTO analyticsDecision;
    private List<LeadCommentDTO> leadComments;
}

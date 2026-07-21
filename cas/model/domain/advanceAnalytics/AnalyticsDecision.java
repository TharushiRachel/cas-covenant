package com.itechro.cas.model.domain.advanceAnalytics;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Table(name = "T_ANALYTICS_DECISION")
@Setter
public class AnalyticsDecision {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_ANALYTICS_DECISION")
    @SequenceGenerator(
            name = "SEQ_T_ANALYTICS_DECISION",
            sequenceName = "SEQ_T_ANALYTICS_DECISION",
            allocationSize = 1)
    @Column(name = "DECISION_ID")
    private Integer decisionId;

    @Column(name = "LEAD_ID")
    private Integer leadId;

    @Column(name = "DECISION")
    private String decision;

    @Column(name = "DECISION_STATUS")
    private String decisionStatus;

    @Column(name = "CHANNEL")
    private String channel;

    @Column(name = "FACILITY_DATA")
    private String facilityData;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;
}

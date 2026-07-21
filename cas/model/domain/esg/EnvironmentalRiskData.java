package com.itechro.cas.model.domain.esg;

import com.itechro.cas.model.domain.applicationform.ApplicationForm;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "T_ENVIRONMENTAL_RISK_DATA")
public class EnvironmentalRiskData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_ENVIRONMENTAL_RISK_DATA")
    @SequenceGenerator(name = "SEQ_T_ENVIRONMENTAL_RISK_DATA", sequenceName = "SEQ_T_ENVIRONMENTAL_RISK_DATA", allocationSize = 1)
    @Column(name = "RISK_DATA_ID")
    private Integer riskDataId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATION_FORM_ID")
    private ApplicationForm applicationForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @Column(name = "RISK_CATEGORY_ID")
    private Integer riskCategoryId;

    @Column(name = "CATEGORY_PARENT_ID")
    private Integer categoryParentId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SCORE")
    private String score;

    @Column(name = "TYPE")
    private String type;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", updatable = false)
    private Date createdDate;

    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;
}

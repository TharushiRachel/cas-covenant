package com.itechro.cas.model.domain.esg;

import com.itechro.cas.model.domain.applicationform.ApplicationForm;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_ESG_OPINION")
@Getter
@Setter
public class RiskOpinion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_ESG_OPINION")
    @SequenceGenerator(name = "SEQ_T_ESG_OPINION", sequenceName = "SEQ_T_ESG_OPINION", allocationSize = 1)
    @Column(name = "RISK_OPINION_ID")
    private Integer riskOpinionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATION_FORM_ID")
    private ApplicationForm applicationForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @Column(name = "OPINION")
    private String opinion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", updatable = false)
    private Date createdDate;

    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;

    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "riskOpinion")
    private RiskOpinionReply riskOpinionReply;
}

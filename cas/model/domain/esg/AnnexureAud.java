package com.itechro.cas.model.domain.esg;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_ESG_ANNEXURE_AUD")
@Getter
@Setter
public class AnnexureAud {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_ESG_ANNEXURE_AUD")
    @SequenceGenerator(name = "SEQ_T_ESG_ANNEXURE_AUD", sequenceName = "SEQ_T_ESG_ANNEXURE_AUD", allocationSize = 1)
    @Column(name = "ANNEXURE_AUD_ID")
    private Integer annexureAudId;

    @Column(name = "ANNEXURE_ID")
    private Integer annexureId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_MANDATORY")
    private AppsConstants.YesNo isMandatory;

    @Enumerated(EnumType.STRING)
    @Column(name = "ALLOW_RISK_EDIT")
    private AppsConstants.YesNo allowRiskEdit;

    @Column(name = "STATUS")
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", updatable = false)
    private Date createdDate;

    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    @Column(name = "APPROVED_STATUS")
    private String approvedStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "APPROVED_DATE")
    private Date approvedDate;

    @Column(name = "APPROVED_BY")
    private String approvedBy;
}

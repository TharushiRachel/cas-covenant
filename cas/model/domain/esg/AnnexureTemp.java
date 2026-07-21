package com.itechro.cas.model.domain.esg;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "T_ESG_ANNEXURE_TEMP")
@Getter
@Setter
public class AnnexureTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_ESG_ANNEXURE_TEMP")
    @SequenceGenerator(name = "SEQ_T_ESG_ANNEXURE_TEMP", sequenceName = "SEQ_T_ESG_ANNEXURE_TEMP", allocationSize = 1)
    @Column(name = "ANNEXURE_ID")
    private Integer annexureId;

    @Column(name = "PARENT_ID")
    private Integer parentId;

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

    @Column(name = "ACTION_STATUS")
    private String actionStatus;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "annexureTemp")
    private List<AnnexureQuestionTemp> annexureQuestionList;
}

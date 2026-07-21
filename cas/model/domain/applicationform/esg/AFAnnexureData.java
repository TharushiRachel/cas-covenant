package com.itechro.cas.model.domain.applicationform.esg;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.applicationform.ApplicationForm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "T_AF_ESG_ANNEXURE_DATA")
@Getter
@Setter
public class AFAnnexureData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_ESG_ANNEXURE_DATA")
    @SequenceGenerator(name = "SEQ_T_AF_ESG_ANNEXURE_DATA", sequenceName = "SEQ_T_AF_ESG_ANNEXURE_DATA", allocationSize = 1)
    @Column(name = "ANNEXURE_DATA_ID")
    private Integer annexureDataId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATION_FORM_ID")
    private ApplicationForm applicationForm;

    @Column(name = "ANNEXURE_ID")
    private Integer annexureId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_MANDATORY")
    private AppsConstants.YesNo isMandatory;

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

    @Column(name = "CURRENT_ANNEXURE_STATUS")
    private DomainConstants.ApplicationFormStatus currentAnnexureStatus;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "afAnnexureData")
    private List<AFAnnexureQuestionData> afAnnexureQuestionDataList;
}

package com.itechro.cas.model.domain.esg;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.applicationform.ApplicationForm;
import com.itechro.cas.model.domain.applicationform.esg.AFAnnexureQuestionData;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_ESG_ANSWER_DATA")
@Getter
@Setter
public class AnswerData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ANNEXURE_ANSWERS_DATA")
    @SequenceGenerator(name = "SEQ_ANNEXURE_ANSWERS_DATA", sequenceName = "SEQ_ANNEXURE_ANSWERS_DATA", allocationSize = 1)
    @Column(name = "ANSWER_DATA_ID")
    private Integer answerDataId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ANNEXURE_ID")
    private Annexure annexure;

    @Column(name = "NAME")
    private String annexureName;

    @Column(name = "DESCRIPTION")
    private String annexureDescription;

    @Column(name = "IS_MANDATORY")
    @Enumerated(EnumType.STRING)
    private AppsConstants.YesNo isMandatory;

    @Enumerated(EnumType.STRING)
    @Column(name = "ALLOW_RISK_EDIT")
    private AppsConstants.YesNo allowRiskEdit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION_ID")
    private AnnexureQuestion question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ANSWER_ID")
    private AnnexureAnswer answer;

    @Column(name = "ANSWER")
    private String answerData;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATION_FORM_ID")
    private ApplicationForm applicationForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @Column(name = "CREATED_USER_DIV_CODE")
    private String createdUserDivCode;
}

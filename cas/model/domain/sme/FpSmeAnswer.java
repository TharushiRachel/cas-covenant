package com.itechro.cas.model.domain.sme;

import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_FP_SME_ANSWER")
@Data
public class FpSmeAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FP_SME_ANSWER")
    @SequenceGenerator(name = "SEQ_FP_SME_ANSWER", sequenceName = "SEQ_FP_SME_ANSWER", allocationSize = 1)
    @Column(name = "ANSWER_ID")
    private Integer answerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SME_QUESTION_ID", referencedColumnName = "SME_QUESTION_ID")
    private SmeQuestion smeQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SME_ANSWER_ID", referencedColumnName = "SME_ANSWER_ID")
    private SmeQuestionAnswer smeQuestionAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID", referencedColumnName = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @Column(name = "ANSWER")
    private String answer;

    @Column(name = "ANSWER_DESCRIPTION")
    private String answerDescription;

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

    @Column(name = "CREATED_USER_WORK_CLASS")
    private Integer createdUserWorkClass;

    @Column(name = "CREATED_USER_DISPLAY_NAME")
    private String createdUserDisplayName;
}

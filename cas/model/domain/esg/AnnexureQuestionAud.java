package com.itechro.cas.model.domain.esg;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_ESG_ANNEX_QUESTION_AUD")
@Getter
@Setter
public class AnnexureQuestionAud {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_ESG_ANNEX_QUESTION_AUD")
    @SequenceGenerator(name = "SEQ_T_ESG_ANNEX_QUESTION_AUD", sequenceName = "SEQ_T_ESG_ANNEX_QUESTION_AUD", allocationSize = 1)
    @Column(name = "QUESTION_AUD_ID")
    private Integer questionAudId;

    @Column(name = "QUESTION_ID")
    private Integer questionId;

    @Column(name = "ANNEXURE_ID")
    private Integer annexureId;

    @Column(name = "QUESTION")
    private String question;

    @Enumerated(EnumType.STRING)
    @Column(name = "ANSWER_TYPE")
    private AppsConstants.AnswerType answerType;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_MANDATORY")
    private AppsConstants.YesNo isMandatory;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

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

}

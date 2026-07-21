package com.itechro.cas.model.domain.esg;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_ESG_ANNEX_ANSWERS_AUD")
@Getter
@Setter
public class AnnexureAnswerAud {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_ESG_ANNEX_ANSWERS_AUD")
    @SequenceGenerator(name = "SEQ_T_ESG_ANNEX_ANSWERS_AUD", sequenceName = "SEQ_T_ESG_ANNEX_ANSWERS_AUD", allocationSize = 1)
    @Column(name = "ANSWER_AUD_ID")
    private Integer answerAudId;

    @Column(name = "ANSWER_ID")
    private Integer answerId;

    @Column(name = "QUESTION_ID")
    private Integer questionId;

    @Column(name = "ANSWER")
    private String answer;

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

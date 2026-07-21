package com.itechro.cas.model.domain.esg;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_ESG_ANNEX_ANSWERS_TEMP")
@Getter
@Setter
public class AnnexureAnswerTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_ESG_ANNEXURE_ANSWERS_TEMP")
    @SequenceGenerator(name = "SEQ_T_ESG_ANNEXURE_ANSWERS_TEMP", sequenceName = "SEQ_T_ESG_ANNEXURE_ANSWERS_TEMP", allocationSize = 1)
    @Column(name = "ANSWER_ID")
    private Integer answerId;

    @Column(name = "PARENT_ID")
    private Integer parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION_ID")
    private AnnexureQuestionTemp annexureQuestionTemp;

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

    @Column(name = "ACTION_STATUS")
    private String actionStatus;
}

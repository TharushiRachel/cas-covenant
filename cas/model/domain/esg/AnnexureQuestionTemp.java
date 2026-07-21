package com.itechro.cas.model.domain.esg;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "T_ESG_ANNEX_QUESTION_TEMP")
@Getter
@Setter
public class AnnexureQuestionTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_ESG_ANNEXURE_QUESTION_TEMP")
    @SequenceGenerator(name = "SEQ_T_ESG_ANNEXURE_QUESTION_TEMP", sequenceName = "SEQ_T_ESG_ANNEXURE_QUESTION_TEMP", allocationSize = 1)
    @Column(name = "QUESTION_ID")
    private Integer questionId;

    @Column(name = "PARENT_ID")
    private Integer parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ANNEXURE_ID")
    private AnnexureTemp annexureTemp;

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

    @Column(name = "ACTION_STATUS")
    private String actionStatus;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "annexureQuestionTemp")
    private List<AnnexureAnswerTemp> annexureAnswerList;

}

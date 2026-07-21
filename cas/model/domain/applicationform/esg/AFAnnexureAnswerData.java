package com.itechro.cas.model.domain.applicationform.esg;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "T_AF_ESG_ANNEXURE_ANSWERS_DATA")
@Getter
@Setter
public class AFAnnexureAnswerData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ANNEXURE_ANSWERS_DATA")
    @SequenceGenerator(name = "SEQ_ANNEXURE_ANSWERS_DATA", sequenceName = "SEQ_ANNEXURE_ANSWERS_DATA", allocationSize = 1)
    @Column(name = "ANSWER_DATA_ID")
    private Integer answerDataId;

    @Column(name = "ANSWER_ID")
    private Integer answerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION_DATA_ID")
    private AFAnnexureQuestionData afAnnexureQuestionData;

    @Column(name = "ANSWER")
    private String answer;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

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

//    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "afAnnexureAnswerData")
//    private List<AFEsgAnswer> afEsgAnswerList;
}

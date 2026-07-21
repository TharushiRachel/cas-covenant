package com.itechro.cas.model.domain.applicationform.esg;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.dto.applicationform.esg.AFAnnexureQuestionDataDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "T_AF_ESG_ANNEXURE_QUESTION_DATA")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AFAnnexureQuestionData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ANNEXURE_QUESTION_DATA")
    @SequenceGenerator(name = "SEQ_ANNEXURE_QUESTION_DATA", sequenceName = "SEQ_ANNEXURE_QUESTION_DATA", allocationSize = 1)
    @Column(name = "QUESTION_DATA_ID")
    private Integer questionDataId;

    @Column(name = "QUESTION_ID")
    private Integer questionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ANNEXURE_DATA_ID")
    private AFAnnexureData afAnnexureData;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "afAnnexureQuestionData")
    private List<AFAnnexureAnswerData> afAnnexureAnswerDataList;

}

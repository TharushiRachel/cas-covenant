package com.itechro.cas.model.domain.esg;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "T_ESG_ANNEX_QUESTION")
@Getter
@Setter
public class AnnexureQuestion {

    @Id
    @Column(name = "QUESTION_ID")
    private Integer questionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ANNEXURE_ID")
    private Annexure annexure;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "annexureQuestion")
    private List<AnnexureAnswer> annexureAnswerList;

}

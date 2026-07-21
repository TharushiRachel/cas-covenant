package com.itechro.cas.model.domain.applicationform.esg;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_AF_ESG_ANSWERS")
@Getter
@Setter
public class AFEsgAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_ESG_ANSWERS")
    @SequenceGenerator(name = "SEQ_T_AF_ESG_ANSWERS", sequenceName = "SEQ_T_AF_ESG_ANSWERS", allocationSize = 1)
    @Column(name = "ESG_ANSWER_ID")
    private Integer esgAnswerId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ANSWER_ID")
//    private AFAnnexureAnswerData afAnnexureAnswerData;

    @Column(name = "ANSWER")
    private String answer;

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

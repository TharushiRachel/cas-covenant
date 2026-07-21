package com.itechro.cas.model.domain.esg;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_ESG_OPINION_REPLY")
@Getter
@Setter
public class RiskOpinionReply {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_ESG_OPINION_REPLY")
    @SequenceGenerator(name = "SEQ_T_ESG_OPINION_REPLY", sequenceName = "SEQ_T_ESG_OPINION_REPLY", allocationSize = 1)
    @Column(name = "RISK_REPLY_ID")
    private Integer riskReplyId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RISK_OPINION_ID")
    private RiskOpinion riskOpinion;

    @Column(name = "REPLY")
    private String reply;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", updatable = false)
    private Date createdDate;

    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;

    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;
}

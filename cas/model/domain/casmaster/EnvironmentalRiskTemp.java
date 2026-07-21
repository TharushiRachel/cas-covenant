package com.itechro.cas.model.domain.casmaster;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "T_ENVIRONMENTAL_RISK_TEMP")
public class EnvironmentalRiskTemp {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_ENVIRONMENTAL_RISK_TEMP")
    @SequenceGenerator(name = "SEQ_T_ENVIRONMENTAL_RISK_TEMP", sequenceName = "SEQ_T_ENVIRONMENTAL_RISK_TEMP", allocationSize = 1)
    @Column(name = "RISK_CATEGORY_ID")
    private Integer riskCategoryId;

    @Column(name = "PARENT_CATEGORY_ID")
    private Integer parentCategoryId;

    @Column(name = "PARENT_ID")
    private Integer parentId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SCORE")
    private String score;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "STATUS")
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", updatable = false)
    private Date createdDate;

    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;
}

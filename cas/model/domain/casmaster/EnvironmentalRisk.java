package com.itechro.cas.model.domain.casmaster;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "T_ENVIRONMENTAL_RISK")
public class EnvironmentalRisk {

    @Id
    @Column(name = "RISK_CATEGORY_ID")
    private Integer riskCategoryId;

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    @Column(name = "APPROVED_STATUS")
    private String approvedStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "APPROVED_DATE")
    private Date approvedDate;

    @Column(name = "APPROVED_BY")
    private String approvedBy;
}

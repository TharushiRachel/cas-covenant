package com.itechro.cas.model.domain.casmaster;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_DEVIATIONS_TEMP")
@Getter
@Setter
public class TempDeviation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_DEVIATIONS")
    @SequenceGenerator(name = "SEQ_T_DEVIATIONS", sequenceName = "SEQ_T_DEVIATIONS", allocationSize = 1)
    @Column(name = "DEVIATION_ID")
    private Integer deviationId;

    @Column(name = "PARENT_ID")
    private Integer parentId;

    @Column(name = "DEVIATION_TYPE")
    private String deviationType;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS")
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", updatable = false)
    private Date createdDate;

    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;
}

package com.itechro.cas.model.domain.casmaster;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_DEVIATION_TYPE")
@Getter
@Setter
public class DeviationType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_DEVIATION_TYPE")
    @SequenceGenerator(name = "SEQ_T_DEVIATION_TYPE", sequenceName = "SEQ_T_DEVIATION_TYPE", allocationSize = 1)
    @Column(name = "DEVIATION_TYPE_ID")
    private Integer deviationTypeId;

    @Column(name = "DEVIATION_TYPE")
    private String deviationType;

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
}

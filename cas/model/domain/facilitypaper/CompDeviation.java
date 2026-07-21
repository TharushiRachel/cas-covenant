package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "T_COMP_DEVIATIONS")
public class CompDeviation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_COMP_DEVIATIONS")
    @SequenceGenerator(name = "SEQ_T_COMP_DEVIATIONS", sequenceName = "SEQ_T_COMP_DEVIATIONS", allocationSize = 1)
    @Column(name = "FP_DEVIATION_ID")
    private Integer fpDeviationId;

    @Column(name = "DEVIATION_ID")
    private Integer deviationId;

    @Column(name = "FACILITY_PAPER_ID")
    private Integer facilityPaperId;

    @Column(name = "DIV_COMMENT")
    private String divComment;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "IS_CHECKED")
    private String isChecked;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DEVIATION_TYPE")
    private String deviationType;

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

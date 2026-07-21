package com.itechro.cas.model.domain.lead;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "T_DIGITAL_APPLICATION_TAG_DETAILS")
@Audited
public class DigitalApplicationTag  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_DIGITAL_APPLICATION_TAG_DETAILS")
    @SequenceGenerator(name = "SEQ_T_DIGITAL_APPLICATION_TAG_DETAILS", sequenceName = "SEQ_T_DIGITAL_APPLICATION_TAG_DETAILS", allocationSize = 1)
    @Column(name = "TAG_ID")
    private Integer tagId;

    @Column(name = "LEAD_ID")
    private Integer leadId;

    @Column(name = "TAG")
    private String tag;

    @Column(name = "TAG_VALUE")
    private String tagValue;

    @Column(name = "FIELD_TYPE")
    private String fieldType;

    @Column(name = "SAVED_BY")
    private String savedBy;

    @Column(name = "SAVED_DATE")
    private Date savedDate;
}

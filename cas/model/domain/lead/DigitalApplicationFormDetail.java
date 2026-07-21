package com.itechro.cas.model.domain.lead;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "T_DIGITAL_APPLICATION_FORM")
public class DigitalApplicationFormDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_DIGITAL_APPLICATION_FORM")
    @SequenceGenerator(name = "SEQ_T_DIGITAL_APPLICATION_FORM", sequenceName = "SEQ_T_DIGITAL_APPLICATION_FORM", allocationSize = 1)
    @Column(name = "DIGITAL_APPLICATION_FORM_ID")
    private Integer digitalApplicationID;

    @Column(name = "LEAD_ID")
    private Integer leadID;

    @Column(name = "DOCUMENT_CONTENT")
    private String documentContent;

    @Column(name = "DOCUMENT_STATUS")
    private AppsConstants.DigitalApplicationStatus documentStatus;
}

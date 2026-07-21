package com.itechro.cas.model.domain.lead;

import com.itechro.cas.model.domain.applicationform.ApplicationForm;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "T_LEAD_APP_CODE")
public class LeadAppCode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_LEAD_SOURCE")
    @SequenceGenerator(name = "SEQ_T_LEAD_SOURCE", sequenceName = "SEQ_T_LEAD_SOURCE", allocationSize = 1)
    @Column(name = "LEAD_SOURCE_ID")
    private Integer leadSourceId;

    @Column(name = "LEAD_ID")
    private Integer leadId;

    @Column(name = "APP_ID")
    private String appId;

    @Column(name = "APP_REF")
    private String appRef;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APP_ID", referencedColumnName = "APP_ID", insertable = false, updatable = false)
    private AppDescriptions appDescription;

   
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEAD_ID", referencedColumnName = "LEAD_ID", insertable = false, updatable = false)
    private Lead lead;


}

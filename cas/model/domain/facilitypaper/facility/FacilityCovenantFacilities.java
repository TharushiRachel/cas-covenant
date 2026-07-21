package com.itechro.cas.model.domain.facilitypaper.facility;

import com.itechro.cas.model.domain.covenant.ApplicationLevelCovenant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
@Table(name = "T_FACILITY_COVENANT_FACILITIES")
public class FacilityCovenantFacilities {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FACILITY_COVENANTS")
    @SequenceGenerator(name = "SEQ_FACILITY_COVENANTS", sequenceName = "SEQ_FACILITY_COVENANTS", allocationSize = 1)
    @Column(name = "FACILITY_COVENANT_ID")
    private Integer facilityCovenantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATION_COVENANT_ID", referencedColumnName = "APPLICATION_COVENANT_ID")
    private ApplicationLevelCovenant applicationLevelCovenant;

//    @Column(name = "FACILITY_ID")
//    private Integer facilityID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_ID")
    private Facility facility;

    @Column(name = "CREDIT_FACILITY_TEMPLATE_ID")
    private Integer creditFacilityTemplateID;

    @Column(name = "CREDIT_FACILITY_NAME")
    private String creditFacilityName;

    @Column(name = "FACILITY_REF_CODE")
    private String facilityRefCode;

    @Column(name = "FACILITY_CURRENCY")
    private String facilityCurrency;

    @Column(name = "FACILITY_AMOUNT")
    private BigDecimal facilityAmount;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

    //private Integer facilityNumber;
}

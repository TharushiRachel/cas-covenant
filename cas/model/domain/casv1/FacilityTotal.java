package com.itechro.cas.model.domain.casv1;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
@Table(name = "CASV1_FACILITY_TOTALS")
public class FacilityTotal {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FACILITY_DATE")
    private Date facilityDate;

    @Column(name = "CUSTOMER_REF")
    private String customerRef;

    @Column(name = "FACILITY_ID")
    private Integer facilityID;

    @Column(name = "VALUE")
    private String value;

    @Column(name = "PRE_VALUE")
    private String preValue;

    @Column(name = "SUBLIMIT")
    private Integer sublimit;

    @Column(name = "FACILITY_TYPE")
    private String facilityType;

    @Column(name = "CURRENCY")
    private Integer currency;

    @Column(name = "ONEOFF")
    private Integer oneOff;

    @Column(name = "SIGHT_USANCE")
    private String sightUsance;

    @Column(name = "SECTOR_REF")
    private String sectorRef;

    @Column(name = "SUB_SECTOR_REF")
    private String subSectorRef;

    @Column(name = "PRPS_ADVNC_REF")
    private String prpsAdvncRef;
}

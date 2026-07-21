package com.itechro.cas.model.dto.casv1;

import lombok.Data;

import java.util.Date;

@Data
public class FacilityTotalDTO {

    private Date facilityDate;

    private String customerRef;

    private Integer facilityID;

    private String facilityName;

    private String value;

    private String preValue;

    private Integer sublimit;

    private String facilityType;

    private String currency;

    private Integer oneOff;

    private String sightUsance;

    private String sectorRef;

    private String subSectorRef;

    private String prpsAdvncRef;
}

package com.itechro.cas.model.dto.facilitypaper.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FPFacilityCovenantsDTO {

    private Integer creditFacilityTemplateID;

    private String creditFacilityName;

    private String facilityRefCode;

    private String facilityCurrency;

    private BigDecimal facilityAmount;
}

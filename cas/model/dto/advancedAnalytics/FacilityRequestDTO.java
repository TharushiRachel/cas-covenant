package com.itechro.cas.model.dto.advancedAnalytics;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class FacilityRequestDTO implements Serializable {

    private String facilityType;

    private BigDecimal facilityAmount;

    private String tenure;
}

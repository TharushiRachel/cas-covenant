package com.itechro.cas.model.dto.integration.request.cribreport;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CreditFacilityAmountDTO implements Serializable {

    private BigDecimal value;

    private String currency;

    private BigDecimal localValue;
}
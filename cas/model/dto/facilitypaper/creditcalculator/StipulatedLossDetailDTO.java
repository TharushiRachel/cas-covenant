package com.itechro.cas.model.dto.facilitypaper.creditcalculator;

import lombok.Data;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;

@Data
public class StipulatedLossDetailDTO {

    private String key;

    private String value;

    public StipulatedLossDetailDTO(String monthRange, String amount) {
        this.key = monthRange;
        this.value = amount;
    }
}

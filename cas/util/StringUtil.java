package com.itechro.cas.util;

import com.itechro.cas.model.dto.facilitypaper.creditcalculator.CalculationDTO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;

public class StringUtil {

    public static String replaceFormulaValues(List<CalculationDTO> data, String calculation) {
        for (CalculationDTO dto : data) {
            calculation = calculation.replace(dto.getCode(), dto.getValue());
        }
        return calculation;
    }

    public static String generateReqId(String casApplicationCode, Integer facilityID, Integer displayOrder, String facilityReferenceCode) {
        return casApplicationCode.concat(String.valueOf(facilityID)).concat(facilityReferenceCode.trim()).concat(String.valueOf(displayOrder)).concat(String.valueOf(new Timestamp(System.currentTimeMillis())));
    }

    public static String formatRates(BigDecimal rate) {
        DecimalFormat formatter = new DecimalFormat("###.##");
        return formatter.format(rate);
    }
}

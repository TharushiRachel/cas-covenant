package com.itechro.cas.model.common;

import com.itechro.cas.exception.impl.AppsCommonErrorCode;
import com.itechro.cas.exception.impl.AppsRuntimeException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static com.itechro.cas.util.DecimalCalculator.isEqual;
import static com.itechro.cas.util.DecimalCalculator.isGreaterThan;

public class CurrencyMathContext implements Serializable {


    private MathContext mathContext;

    private BigDecimal unitAmount;

    private Integer displayDecimalCount;

    private CurrencyMathContext(BigDecimal roundingUnit, RoundingMode roundingMode, Integer displayDecimalUnits) {

        int decimalPlaces;

        if (isGreaterThan(roundingUnit, BigDecimal.ONE) || isEqual(roundingUnit,
                BigDecimal.ONE)) {  // no decimal places
            decimalPlaces = 0;
        } else {
            String text = roundingUnit.toPlainString();
            decimalPlaces = text.length() - text.indexOf('.') - 1;
        }

        if (decimalPlaces > displayDecimalUnits) {
            throw new AppsRuntimeException(AppsCommonErrorCode.APPS_EXCEPTION_CURRENCY_DISPLAY_SCALE_INVALID);
        }

        this.unitAmount = roundingUnit.setScale(decimalPlaces);
        this.displayDecimalCount = displayDecimalUnits;
        this.mathContext = new MathContext(decimalPlaces, roundingMode);
    }

    public static CurrencyMathContext of(BigDecimal roundingUnit, RoundingMode roundingMode, Integer displayDecimalUnits) {
        return new CurrencyMathContext(roundingUnit, roundingMode, displayDecimalUnits);
    }

    public MathContext getMathContext() {
        return mathContext;
    }

    public BigDecimal getUnitAmount() {
        return unitAmount;
    }

    public Integer getDisplayDecimalCount() {
        return displayDecimalCount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CurrencyMathContext{");
        sb.append("mathContext=").append(mathContext);
        sb.append(", unitAmount=").append(unitAmount);
        sb.append(", displayDecimalCount=").append(displayDecimalCount);
        sb.append('}');
        return sb.toString();
    }
}

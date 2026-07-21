package com.itechro.cas.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberUtil {

    public static BigDecimal getMillionValue(BigDecimal value) {
        if (value != null) {
            return DecimalCalculator.divide(value, 1000000);
        } else {
            return DecimalCalculator.getDefaultZero();
        }
    }

    public static String getFormattedAmount(Object unFormattedAmount) {
        if (unFormattedAmount != null) {
            DecimalFormat df = new DecimalFormat("#,###,###,###,##0.00");
            return df.format(unFormattedAmount);
        } else {
            return null;
        }
    }

    public static BigDecimal roundUp(BigDecimal decimalValue, Integer digits) {
        if (digits > 1) {
//            35.95499999999==> 35.955 ==> 35.96 (35.9549999999,2)   //  44.332 ==> 44.34
            return decimalValue.setScale(digits + 1, RoundingMode.HALF_UP).setScale(digits, RoundingMode.CEILING);
        } else {
            return decimalValue.setScale(digits, RoundingMode.UP);
        }
    }

    public static BigDecimal roundDown(BigDecimal decimalValue, Integer digits) {
        if (digits > 1) {
            return decimalValue.setScale(digits + 1, RoundingMode.HALF_UP).setScale(digits, RoundingMode.FLOOR);
        } else {
            return decimalValue.setScale(digits, RoundingMode.DOWN);
        }
    }


    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}

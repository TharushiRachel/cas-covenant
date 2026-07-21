package com.itechro.cas.util;

import com.itechro.cas.exception.impl.AppsCommonErrorCode;
import com.itechro.cas.exception.impl.AppsRuntimeException;
import com.itechro.cas.model.common.CurrencyMathContext;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Handles floating point arithmetic
 */

public class DecimalCalculator {

    private static final MathContext DECIMAL_64 = MathContext.DECIMAL64;

    public static CurrencyMathContext getDefaultCurrencyMathContext() {
        return CurrencyMathContext.of(DecimalCalculator.parseDouble(0.01d), RoundingMode.HALF_EVEN, 2);
    }

    public static BigDecimal getDefaultZero() {
        return new BigDecimal(0, DECIMAL_64);
    }

    public static BigDecimal getDefaultZero(CurrencyMathContext context) {
        return new BigDecimal(0, context.getMathContext());
    }

    public static BigDecimal round(BigDecimal value, CurrencyMathContext context) {
        MathContext mathContext = context.getMathContext();
        BigDecimal roundedVal = value.setScale(mathContext.getPrecision(), mathContext.getRoundingMode());
        BigDecimal val;
        val = roundedVal.divide(context.getUnitAmount()).setScale(0, mathContext.getRoundingMode()).multiply(
                context.getUnitAmount());

        val = val.setScale(context.getDisplayDecimalCount());
        return val;
    }

    public static boolean isGreaterThan(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) == 1;
    }

    public static boolean isLessThan(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) == -1;
    }

    public static boolean isEqual(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) == 0;
    }

    public static boolean isGreaterThanOrEq(BigDecimal first, BigDecimal second) {
        return (first.compareTo(second) == 1 || first.compareTo(second) == 0);
    }

    public static boolean isLessThanOrEq(BigDecimal first, BigDecimal second) {
        return (first.compareTo(second) == -1 || first.compareTo(second) == 0);
    }

    public static boolean isGreaterThanZero(BigDecimal value) {
        return isGreaterThan(value, getDefaultZero());
    }

    public static boolean isLessThanZero(BigDecimal value) {
        return isLessThan(value, getDefaultZero());
    }

    public static boolean isGreaterThanOrEqZero(BigDecimal value) {
        return isGreaterThanOrEq(value, getDefaultZero());
    }

    public static BigDecimal parseDouble(Double value, CurrencyMathContext context) {
        if (value == null) {
            throw new AppsRuntimeException(AppsCommonErrorCode.APPS_INVALID_VALUE);
        }
        return round(BigDecimal.valueOf(value), context);
    }

    public static BigDecimal parseLong(Long value, CurrencyMathContext context) {
        if (value == null) {
            throw new AppsRuntimeException(AppsCommonErrorCode.APPS_INVALID_VALUE);
        }
        return round(BigDecimal.valueOf(value), context);
    }

    public static BigDecimal parseString(String value, CurrencyMathContext context) {
        if (!StringUtils.isEmpty(value)) {
            return round(new BigDecimal(value), context);
        } else {
            return null;
        }
    }

    public static BigDecimal parseDouble(Double value) {
        if (value == null) {
            throw new AppsRuntimeException(AppsCommonErrorCode.APPS_INVALID_VALUE);
        }
        return BigDecimal.valueOf(value).round(DECIMAL_64);
    }

    public static BigDecimal parseLong(Long value) {
        if (value == null) {
            throw new AppsRuntimeException(AppsCommonErrorCode.APPS_INVALID_VALUE);
        }
        return BigDecimal.valueOf(value).round(DECIMAL_64);
    }

    public static BigDecimal parseString(String value) {
        if (!StringUtils.isEmpty(value)) {
            return new BigDecimal(value).round(DECIMAL_64);
        } else {
            return null;
        }
    }

    public static Integer parseInt(String value) {
        if (value == null) {
            throw new AppsRuntimeException(AppsCommonErrorCode.APPS_INVALID_VALUE);
        }
        return Integer.parseInt(value);
    }

    /**
     * Add
     *
     * @param bigDecimal values to be added
     * @return added value
     */
    public static BigDecimal add(BigDecimal... bigDecimal) {
        BigDecimal value = getDefaultZero();
        for (BigDecimal valueAdd : bigDecimal) {
            if (valueAdd != null) {
                value = value.add(valueAdd);
            }
        }
        return value;
    }

    /**
     * Add
     *
     * @param bigDecimal values to be added
     * @return added value
     */
    public static BigDecimal add(Collection<BigDecimal> bigDecimal) {
        BigDecimal value = getDefaultZero();
        for (BigDecimal valueAdd : bigDecimal) {
            value = value.add(valueAdd);
        }
        return value;
    }


    /**
     * Add
     *
     * @param bigDecimal values to be added
     * @return added value
     */
    public static BigDecimal add(CurrencyMathContext context, BigDecimal... bigDecimal) {
        return round(add(bigDecimal), context);
    }


    /**
     * Performs addition operation for primitive integers
     *
     * @param first  val1
     * @param second int values
     * @return added values
     */
    public static BigDecimal add(BigDecimal first, int... second) {
        int value = 0;
        for (int aSecond : second) {
            value += aSecond;
        }
        return add(first, new BigDecimal(value));
    }

    /**
     * Performs addition operation for primitive integers
     *
     * @param first  val1
     * @param second int values
     * @return added values
     */
    public static BigDecimal add(CurrencyMathContext context, BigDecimal first, int... second) {
        return round(add(first, second), context);
    }

    /**
     * Performs subtraction operation
     *
     * @param first  val1
     * @param second val2
     * @return result
     */
    public static BigDecimal subtract(BigDecimal first, BigDecimal second) {
        return first.subtract(second);
    }

    /**
     * multiply
     *
     * @param first  val1
     * @param second val2
     * @return result
     */
    public static BigDecimal multiply(BigDecimal first, BigDecimal second) {
        return first.multiply(second);
    }


    /**
     * multiply
     *
     * @param first  val1
     * @param second val2
     * @return result
     */
    public static BigDecimal multiply(BigDecimal first, Integer second) {
        return multiply(first, BigDecimal.valueOf(second));
    }

    /**
     * multiply
     *
     * @param first  val1
     * @param second val2
     * @return result
     */
    public static BigDecimal multiply(BigDecimal first, Double second) {
        return multiply(first, BigDecimal.valueOf(second));
    }


    /**
     * multiply
     *
     * @param context rounding context
     * @param first   val1
     * @param second  val2
     * @return result
     */
    public static BigDecimal multiply(CurrencyMathContext context, BigDecimal first, BigDecimal second) {
        return round(multiply(first, second), context);
    }

    public static BigDecimal divide(BigDecimal first, BigDecimal second) {
        // division is always preform with default math context to avoid Non-terminating decimal expansion
        return first.divide(second, DECIMAL_64);
    }

    public static BigDecimal divide(CurrencyMathContext context, BigDecimal first, BigDecimal second) {
        return round(divide(first, second), context);
    }

    public static BigDecimal divide(BigDecimal first, Integer second) {
        return divide(first, BigDecimal.valueOf(second));
    }

    public static BigDecimal valuePercentage(BigDecimal first, int percentage) {
        return divide(multiply(first, percentage), 100);
    }

    public static BigDecimal valuePercentage(BigDecimal first, BigDecimal percentage) {
        return divide(multiply(first, percentage), 100);
    }

    public static BigDecimal valuePercentage(BigDecimal first, BigDecimal percentage, CurrencyMathContext context) {
        return round(divide(multiply(first, percentage), 100), context);
    }

    public static BigDecimal valuePercentage(BigDecimal first, BigDecimal percentage, BigDecimal max, BigDecimal min) {
        BigDecimal pValue = divide(multiply(first, percentage), 100);
        if (max != null && max.compareTo(pValue) < 0) {
            pValue = max;
        }

        if (min != null && min.compareTo(pValue) > 0) {
            pValue = min;
        }
        return pValue;
    }

    /**
     * split given value to collection. Sum of the collection = amount
     * One split will have the remainder also
     *
     * @param size
     */
    public static List<BigDecimal> split(BigDecimal amount, int size) {
        BigDecimal splitPart = divide(amount, new BigDecimal(size));
        BigDecimal splitSum = getDefaultZero();

        for (int i = 0; i < size; i++) {
            splitSum = add(splitSum, splitPart);
        }

        BigDecimal diff = subtract(amount, splitSum);
        List<BigDecimal> values = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            if (i == (size - 1)) {
                values.add(add(splitPart, diff));
            } else {
                values.add(splitPart);
            }
        }
        return values;
    }


    public static List<BigDecimal> split(BigDecimal amount, int size, CurrencyMathContext currencyMathContext) {
        BigDecimal splitPart;
        if (currencyMathContext != null) {
            splitPart = round(divide(amount, new BigDecimal(size)), currencyMathContext);
        } else {
            splitPart = divide(amount, new BigDecimal(size));
        }
        BigDecimal splitSum = getDefaultZero();

        for (int i = 0; i < size; i++) {
            splitSum = add(splitSum, splitPart);
        }

        BigDecimal diff = subtract(amount, splitSum);
        List<BigDecimal> values = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            if (i == (size - 1)) {
                values.add(add(splitPart, diff));
            } else {
                values.add(splitPart);
            }
        }
        return values;
    }

    public static BigDecimal prorate(CurrencyMathContext context, BigDecimal amount, BigDecimal prorate) {
        return round(divide(multiply(amount, prorate), add(BigDecimal.valueOf(100), prorate)), context);
    }

    public static BigDecimal getFormattedValue(Object value) {
        if (value == null) {
            return getDefaultZero();
        }

        try {
            return new BigDecimal(value.toString()).abs();
        } catch (NumberFormatException e) {
            return getDefaultZero();
        }
    }

    public static BigDecimal calculateExposureAdd(Object value1, Object value2) {
        BigDecimal result = DecimalCalculator.add(getFormattedValue(value1), getFormattedValue(value2));
        return getFormattedValue(result);
    }

    public static BigDecimal calculateExposureReduction(Object value1, Object value2) {
        BigDecimal result = DecimalCalculator.subtract(getFormattedValue(value1), getFormattedValue(value2));
        return getFormattedValue(result);
    }

    public static BigDecimal getFormattedActualValue(Object value) {
        if (value == null) {
            return getDefaultZero();
        }

        return new BigDecimal(value.toString());
    }
}

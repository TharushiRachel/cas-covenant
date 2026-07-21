package com.itechro.cas.util;

public class CreditCalculatorUtil {

    private static final String REDUCING_RATE_OUTPUT_CODE = "OUT007";
    private static final String FLAT_RATE_OUTPUT_CODE = "OUT008";
    private static final String PERIOD_OUTPUT_CODE = "OUT002";
    private static final String NO_OF_UP_FRONTS_OUTPUT_CODE = "OUT013";
    private static final String RENTAL_WITH_VAT_OUTPUT_CODE = "OUT012";
    private static final String EFFECTIVE_RATE_OUTPUT_CODE = "OUT003";

    public static String[] getCreditCalculatorSystemOutputsForFacilitySections() {
        return new String[]{REDUCING_RATE_OUTPUT_CODE, FLAT_RATE_OUTPUT_CODE, EFFECTIVE_RATE_OUTPUT_CODE};
    }

    public static String getPeriodOutputCode() {
        return PERIOD_OUTPUT_CODE;
    }

    public static String getReducingRateOutputCode() {
        return REDUCING_RATE_OUTPUT_CODE;
    }

    public static String getFlatRateOutputCode() {
        return FLAT_RATE_OUTPUT_CODE;
    }

    public static String getNoOfUpFrontsOutputCode() {
        return NO_OF_UP_FRONTS_OUTPUT_CODE;
    }

    public static String getRentalWithVatOutputCode() {
        return RENTAL_WITH_VAT_OUTPUT_CODE;
    }

    public static String getEffectiveRateOutputCode() {
        return EFFECTIVE_RATE_OUTPUT_CODE;
    }
}

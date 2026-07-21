package com.itechro.cas.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public abstract class LocationUtil {

    public static String getFormattedDoubles(Double d1, Double d2) {
        NumberFormat formatter1 = new DecimalFormat();
        formatter1.setMaximumFractionDigits(Integer.MAX_VALUE);
        formatter1.setGroupingUsed(false);

        NumberFormat formatter2 = new DecimalFormat();
        formatter2.setMaximumFractionDigits(Integer.MAX_VALUE);
        formatter2.setGroupingUsed(false);

        return formatter1.format(d1) + "," + formatter2.format(d2);
    }

}

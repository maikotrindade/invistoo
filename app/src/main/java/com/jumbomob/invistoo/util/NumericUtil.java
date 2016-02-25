package com.jumbomob.invistoo.util;

import java.math.BigDecimal;

/**
 * @author maiko.trindade
 * @since 21/02/2016
 */
public class NumericUtil {

    public static Double getValidDouble(String stringDouble) {
        if (stringDouble != null && !stringDouble.isEmpty()) {
            stringDouble = stringDouble.replaceAll(",", ".");
            return new Double(stringDouble.trim());
        }
        return null;
    }

    public static Long getValidLong(String stringLong) {
        if (stringLong != null && !stringLong.isEmpty()) {
            stringLong = stringLong.replaceAll(",", ".");
            return new Long(stringLong.trim());
        }
        return null;
    }

    public static Integer getValidInteger(String stringInteger) {
        if (stringInteger != null && !stringInteger.isEmpty()) {
            stringInteger = stringInteger.replaceAll(",", ".");
            return Integer.valueOf(stringInteger.trim());
        }
        return null;
    }

    public static BigDecimal getValidBigDecimal(String stringBigDecimal) {
        if (stringBigDecimal != null && !stringBigDecimal.isEmpty()) {
            stringBigDecimal = stringBigDecimal.replaceAll(",", ".");
            return new BigDecimal(stringBigDecimal.trim());
        }
        return null;
    }
}

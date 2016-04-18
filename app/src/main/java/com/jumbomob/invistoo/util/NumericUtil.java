package com.jumbomob.invistoo.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author maiko.trindade
 * @since 21/02/2016
 */
public class NumericUtil {

    public static final Locale ptBr = new Locale("pt", "BR");

    public static String formatCurrency(final Double number) {
        final NumberFormat formatter = NumberFormat.getCurrencyInstance(ptBr);
        return formatter.format(number);
    }

    public static String formatCurrency(final BigDecimal number) {
        final NumberFormat formatter = NumberFormat.getCurrencyInstance(ptBr);
        return formatter.format(number);
    }

    public static String formatDouble(final Double number) {
        final DecimalFormat formatter = new DecimalFormat("#.00");
        return formatter.format(number);
    }

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

    public static BigDecimal getValidBigDecimal(String stringBigDecimal) {
        if (stringBigDecimal != null && !stringBigDecimal.isEmpty()) {
            stringBigDecimal = stringBigDecimal.replaceAll(",", ".");
            return new BigDecimal(stringBigDecimal.trim());
        }
        return null;
    }
}

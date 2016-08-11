package com.jumbomob.invistoo.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

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

    public static boolean isValidDouble(final String stringDouble) {
        final String Digits = "(\\p{Digit}+)";
        final String HexDigits = "(\\p{XDigit}+)";
        // an exponent is 'e' or 'E' followed by an optionally
        // signed decimal integer.
        final String Exp = "[eE][+-]?" + Digits;
        final String fpRegex =
                ("[\\x00-\\x20]*" + // Optional leading "whitespace"
                        "[+-]?(" +         // Optional sign character
                        "NaN|" +           // "NaN" string
                        "Infinity|" +      // "Infinity" string

                        // A decimal floating-point string representing a finite positive
                        // number without a leading sign has at most five basic pieces:
                        // Digits . Digits ExponentPart FloatTypeSuffix

                        // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
                        "(((" + Digits + "(\\.)?(" + Digits + "?)(" + Exp + ")?)|" +

                        // . Digits ExponentPart_opt FloatTypeSuffix_opt
                        "(\\.(" + Digits + ")(" + Exp + ")?)|" +

                        // Hexadecimal strings
                        "((" +
                        // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
                        "(0[xX]" + HexDigits + "(\\.)?)|" +

                        // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
                        "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

                        ")[pP][+-]?" + Digits + "))" +
                        "[fFdD]?))" +
                        "[\\x00-\\x20]*");// Optional trailing "whitespace"

        if (Pattern.matches(fpRegex, stringDouble)) {
            return true;
        } else {
            return false;
        }
    }
}

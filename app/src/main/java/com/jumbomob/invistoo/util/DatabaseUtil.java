package com.jumbomob.invistoo.util;

/**
 * Useful class for Database manipulation.
 *
 * @author maiko.trindade
 * @since 09/02/2016
 */
public class DatabaseUtil {

    public static boolean getBoolean(int intBoolean) {
        return (intBoolean == 1);
    }

    public static String joinStrings(final String[] array) {
        StringBuilder builder = new StringBuilder();
        for (String string : array) {
            builder.append(string).append(",");
        }
        builder.deleteCharAt(builder.lastIndexOf(","));
        return builder.toString();
    }

    public static int getInt(boolean removed) {
        return removed ? 1 : 0 ;
    }
}

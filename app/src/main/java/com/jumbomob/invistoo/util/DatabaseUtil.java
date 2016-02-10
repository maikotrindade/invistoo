package com.jumbomob.invistoo.util;

/**
 * Useful class for Database manipulation.
 *
 * @author maiko.trindade
 * @since 12/01/2016
 */
public class DatabaseUtil {

    public static boolean getBoolean(int intBoolean) {
        return (intBoolean == 1);
    }

    public static String[] mergeColumns(final String[]... arrays) {
        int arraysQuantity = 0;
        int count = 0;
        for (String[] array : arrays) {
            arraysQuantity++;
            count += array.length;
        }

        String[] mergedArray = (String[]) java.lang.reflect.Array.newInstance(arrays[0][0]
                .getClass(), count);
        int start = 0;
        for (String[] array : arrays) {
            System.arraycopy(array, 0, mergedArray, start, array.length);
            start += array.length;
        }
        return mergedArray;
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

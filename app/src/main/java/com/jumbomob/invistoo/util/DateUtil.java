package com.jumbomob.invistoo.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author maiko.trindade
 * @since 14/02/2016
 */
public class DateUtil {

    public static final String TAG = DateUtil.class.getSimpleName();

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String SIMPLE_FORMAT = "dd/MM/yyyy 'às' HH:mm";

    public static String formatDate(final Date date, final String format) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return (date != null) ? dateFormat.format(date) : "";
    }

    public static String formatDate(final String date) {
        return formatDate(stringToDate(date, DEFAULT_FORMAT), SIMPLE_FORMAT);
    }

    public static String formatDate(final Date date) {
        return formatDate(date, DEFAULT_FORMAT);
    }

    public static Date stringToDate(final String stringDate) {
        return stringToDate(stringDate, DEFAULT_FORMAT);
    }

    public static Date stringToDate(final String stringDate, final String format) {
        final Locale locale = new Locale("pt", "BR");
        final SimpleDateFormat dateFormat = new SimpleDateFormat(format, locale);
        Date date = null;
        try {

            date = dateFormat.parse(stringDate);
        } catch (ParseException e) {
            Log.e(TAG, "Datetime could not be converted.");
        }
        return date;
    }

}

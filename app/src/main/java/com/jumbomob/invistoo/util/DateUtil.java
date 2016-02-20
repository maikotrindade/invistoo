package com.jumbomob.invistoo.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @author maiko.trindade
 * @since 14/02/2016
 */
public class DateUtil {

    public static final String TAG = DateUtil.class.getSimpleName();

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final TimeZone UTC;
    public static final Calendar calendar;

    static {
        UTC = TimeZone.getTimeZone("UTC");
        TimeZone.setDefault(UTC);
        calendar = new GregorianCalendar(UTC);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public static String formatDate(final Date date, final String format) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return (date != null) ? dateFormat.format(date) : "";
    }

    public static String formatDate(final Date date) {
        return formatDate(date, DEFAULT_FORMAT);
    }

    public static Date stringToDate(final String stringDate) {
        return stringToDate(stringDate, DEFAULT_FORMAT);
    }

    public static Date stringToDate(final String stringDate, final String format) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = dateFormat.parse(stringDate);
        } catch (ParseException e) {
            Log.e(TAG, "Datetime could not be converted.");
        }
        return date;
    }

}

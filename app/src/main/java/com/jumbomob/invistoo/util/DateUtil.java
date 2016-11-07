package com.jumbomob.invistoo.util;

import android.content.Context;
import android.util.Log;

import com.jumbomob.invistoo.R;

import org.joda.time.DateTime;
import org.joda.time.Duration;

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
    public static final String SIMPLE_DATETIME_FORMAT = "dd/MM/yyyy 'às' HH:mm";
    public static final String SIMPLE_DATE_FORMAT = "dd/MM/yyyy";

    public static String formatDate(final Date date, final String format) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return (date != null) ? dateFormat.format(date) : "";
    }

    public static String formatDate(final String date) {
        return formatDate(stringToDate(date, DEFAULT_FORMAT), SIMPLE_DATETIME_FORMAT);
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

    public static String formatDateUX(Date unformattedDate) {

        final Context baseContext = InvistooApplication.getInstance().getBaseContext();
        final DateTime date = new DateTime(unformattedDate);
        final DateTime currentDate = new DateTime();

        final Duration timeDifference = new Duration(date, currentDate);
        if (timeDifference.getStandardDays() > 0 && timeDifference.getStandardDays() < 30) {
            long days = timeDifference.getStandardDays();
            if (days == 1) {
                return baseContext.getString(R.string.last_update_day, timeDifference.getStandardDays());
            } else {
                return baseContext.getString(R.string.last_update_days, timeDifference.getStandardDays());
            }
        } else if (timeDifference.getStandardHours() > 0 && timeDifference.getStandardHours() < 24) {
            long hours = timeDifference.getStandardHours();
            if (hours == 1) {
                return baseContext.getString(R.string.last_update_hour, timeDifference.getStandardHours());
            } else {
                return baseContext.getString(R.string.last_update_hours, timeDifference.getStandardHours());
            }
        } else if (timeDifference.getStandardMinutes() > 0 && timeDifference.getStandardMinutes() < 60) {
            long minutes = timeDifference.getStandardMinutes();
            if (minutes == 1) {
                return baseContext.getString(R.string.last_update_minute, timeDifference.getStandardMinutes());
            } else {
                return baseContext.getString(R.string.last_update_minutes, timeDifference.getStandardMinutes());
            }
        } else if (timeDifference.getStandardSeconds() > 0 && timeDifference.getStandardMinutes() < 60) {
            return baseContext.getString(R.string.last_update_less_than_minute);
        } else {
            return baseContext.getString(R.string.last_update_time, formatDate(date.toDate(), SIMPLE_DATE_FORMAT));
        }
    }

}

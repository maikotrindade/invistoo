package com.jumbomob.invistoo.util;


import android.app.Activity;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

/**
 * @author maiko.trindade<mt@ubby.io>
 * @since 10/7/17
 */

public class LogUtil {

    final private static int LOGCAT_TAG_MAX = 23;

    public static void d(Activity activityTag, String message) {
        String tag = activityTag.getClass().getSimpleName();
        if (tag.length() >= LOGCAT_TAG_MAX) {
            tag = tag.substring(0, Math.min(tag.length(), LOGCAT_TAG_MAX));
        }
        message = TextUtils.isEmpty(message) ? "not message set" : message;
        Log.d(tag, message);
    }

    public static void w(Activity activityTag, String message) {
        String tag = activityTag.getClass().getSimpleName();
        if (tag.length() >= LOGCAT_TAG_MAX) {
            tag = tag.substring(0, Math.min(tag.length(), LOGCAT_TAG_MAX));
        }
        message = TextUtils.isEmpty(message) ? "not message set" : message;
        Log.w(tag, message);
    }

    public static void i(Activity activityTag, String message) {
        String tag = activityTag.getClass().getSimpleName();
        if (tag.length() >= LOGCAT_TAG_MAX) {
            tag = tag.substring(0, Math.min(tag.length(), LOGCAT_TAG_MAX));
        }
        message = TextUtils.isEmpty(message) ? "not message set" : message;
        Log.i(tag, message);
    }

    public static void e(Activity activityTag, String message) {
        final String tag = activityTag.getClass().getSimpleName();
        e(0, tag, message);
    }

    public static void e(Fragment fragmentTag, String message) {
        final String tag = fragmentTag.getClass().getSimpleName();
        e(0, tag, message);
    }

    public static void e(String tag, String message) {
        e(0, tag, message);
    }

    public static void e(int priority, String tag, String message) {
        message = TextUtils.isEmpty(message) ? "not message set" : message;
        Crashlytics.log(priority, tag, message);
    }

}

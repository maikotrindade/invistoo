package com.jumbomob.invistoo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.jumbomob.invistoo.R;

/**
 * @author maiko.trindade
 * @since 22/07/2016
 */
public class SharedPrefsUtil {

    public static final String INFORMATION_SYNC = "information_sync";
    public static final String LAST_SYNC = "last_sync";

    public static String getLastSync(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                INFORMATION_SYNC, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LAST_SYNC, context.getString(R.string.no_synced));
    }

    public static void setLastSync(String lastUpdate) {
        final Context context = InvistooApplication.getInstance().getBaseContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                INFORMATION_SYNC, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LAST_SYNC, lastUpdate);
        editor.commit();
    }

}

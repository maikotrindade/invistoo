package com.jumbomob.invistoo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.jumbomob.invistoo.R;

/**
 * @author maiko.trindade
 * @since 22/07/2016
 */
public class SharedPrefsUtil {

    public static final String USER_SESSION = "user_session";
    public static final String IS_USER_LOGGED = "is_user_logged";
    public static final String LAST_USER = "last_user";
    public static final String IS_REMEMBER_USER = "is_remember_user";
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

    public static boolean isRememberUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                USER_SESSION, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_REMEMBER_USER, false);
    }

    public static void setRememberUser(Boolean isRemember) {
        final Context context = InvistooApplication.getInstance().getBaseContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                USER_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_REMEMBER_USER, isRemember);
        editor.commit();
    }

    public static boolean isUserLogged(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                USER_SESSION, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_USER_LOGGED, false);
    }

    public static void setUserLogged(Boolean isLogged) {
        final Context context = InvistooApplication.getInstance().getBaseContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                USER_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_USER_LOGGED, isLogged);
        editor.commit();
    }

    public static String getLastUserUid(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                USER_SESSION, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LAST_USER, "");
    }

    public static void setLastUserUid(String lastUserUid) {
        final Context context = InvistooApplication.getInstance().getBaseContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                USER_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LAST_USER, lastUserUid);
        editor.commit();
    }

}

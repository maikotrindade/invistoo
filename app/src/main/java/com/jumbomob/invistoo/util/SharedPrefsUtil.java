package com.jumbomob.invistoo.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author maiko.trindade
 * @since 22/07/2016
 */
public class SharedPrefsUtil {

    public static final String USER_SESSION = "user_session";
    public static final String IS_USER_LOGGED = "is_user_logged";
    public static final String LAST_USER = "last_user";
    public static final String IS_REMEMBER_USER = "is_remember_user";
    public static final String USER_HAS_GOALS = "user_has_goals";

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

    public static boolean userHasGoals(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                USER_SESSION, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(USER_HAS_GOALS, false);
    }

    public static void setUserHasGoals(Boolean isLogged) {
        final Context context = InvistooApplication.getInstance().getBaseContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                USER_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(USER_HAS_GOALS, isLogged);
        editor.commit();
    }

}

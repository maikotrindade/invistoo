package com.jumbomob.invistoo.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ScaleXSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.jumbomob.invistoo.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author maiko.trindade
 * @since 09/02/2016
 */
public class InvistooUtil {

    private static final String TAG = InvistooUtil.class.getSimpleName();

    private static final ConnectivityManager sConnectivityManager = (ConnectivityManager)
            InvistooApplication.getInstance().getSystemService(
                    Context.CONNECTIVITY_SERVICE);

    public static boolean hasWifiConnection() {
        NetworkInfo wifiNetwork = sConnectivityManager.getNetworkInfo(ConnectivityManager
                .TYPE_WIFI);
        return wifiNetwork != null && wifiNetwork.isConnected();
    }

    public static boolean hasMobileConnection() {
        NetworkInfo mobileNetwork = sConnectivityManager.getNetworkInfo(ConnectivityManager
                .TYPE_MOBILE);
        return mobileNetwork != null && mobileNetwork.isConnected();
    }

    public static boolean isNetworkActivated() {
        NetworkInfo activeNetwork = sConnectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public static boolean isConnected() {
        return (hasWifiConnection() || hasMobileConnection()) && isNetworkActivated();
    }

    public static Snackbar makeSnackBar(Activity activity, CharSequence text, int duration) {
        final View viewById = activity.findViewById(R.id.container);
        Snackbar snackbar = Snackbar.make(viewById, text, duration);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(viewById.getContext(), R.color.colorAccent));
        snackbar.setActionTextColor(ContextCompat.getColor(viewById.getContext(), R.color.material_grey_400));
        return snackbar;
    }

    public static int getVersionCode(final Activity activity) {
        final PackageManager packageManager = activity.getPackageManager();
        final String packageName = activity.getPackageName();
        int versionCode = 0;
        String versionName = "";
        try {
            versionCode = packageManager.getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        return versionCode;
    }

    public static String getVersionName(final Activity activity) {
        final PackageManager packageManager = activity.getPackageManager();
        final String packageName = activity.getPackageName();
        int versionCode = 0;
        String versionName = "";
        try {
            versionName = packageManager.getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        return versionName;
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static SpannableString getSpacedText(CharSequence text) {
        if (text == null) return null;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            builder.append(text.charAt(i));
            if (i + 1 < text.length()) {
                builder.append("\u00A0");
            }
        }
        SpannableString finalText = new SpannableString(builder.toString());
        if (builder.toString().length() > 1) {
            for (int i = 1; i < builder.toString().length(); i += 2) {
                finalText.setSpan(new ScaleXSpan((0.3f + 1) / 10), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return finalText;
    }

    public static final int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean areThereDuplicates(List<Long> unverifiedList) {
        final Set<Long> notRepeatedList = new HashSet<>(unverifiedList);
        return notRepeatedList.size() != unverifiedList.size() ? true : false;
    }

}

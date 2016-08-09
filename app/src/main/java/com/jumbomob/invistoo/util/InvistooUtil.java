package com.jumbomob.invistoo.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.jumbomob.invistoo.R;

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

    public static boolean isNetworkActived() {
        NetworkInfo activeNetwork = sConnectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public static boolean isConnected() {
        return (hasWifiConnection() || hasMobileConnection()) && isNetworkActived();
    }

    public static Snackbar makeSnackBar(Activity activity, CharSequence text, int duration) {
        final View viewById = activity.findViewById(R.id.container);
        Snackbar snackbar = Snackbar.make(viewById, text, duration);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(viewById.getContext(), R.color
                .material_blue_600));
        snackbar.setActionTextColor(ContextCompat.getColor(viewById.getContext(), R.color
                .material_grey_400));
        return snackbar;
    }

    public int getVersionCode(final Activity activity) {
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

    public String getVersionName(final Activity activity) {
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

}

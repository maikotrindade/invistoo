package com.jumbomob.invistoo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author maiko.trindade
 * @since 09/02/2016
 */
public class InvistooUtil {

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

//    public static Snackbar makeSnackBar(View view, CharSequence text, int duration) {
//        Snackbar snackbar = Snackbar.make(view, text, duration);
//        View snackBarView = snackbar.getView();
//        snackBarView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color
//                .red_1));
//        snackbar.setActionTextColor(ContextCompat.getColor(view.getContext(), R.color
//                .grey_300));
//        return snackbar;
//    }

}

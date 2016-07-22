package com.jumbomob.invistoo.util;

import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

/**
 * @author maiko.trindade
 * @since 21/07/2016
 */
public class PermissionUtil {

    public static boolean isGranted(final String permission) {
        return (ContextCompat.checkSelfPermission(InvistooApplication.getInstance()
                .getBaseContext(), permission) == PackageManager.PERMISSION_GRANTED);
    }

    public static void request(final Fragment fragment, String permission, final int
            requestCode) {
        fragment.requestPermissions(new String[]{permission}, requestCode);
    }

    public static void request(final Fragment fragment, final String[] permissions,
                               final int requestCode) {
        fragment.requestPermissions(permissions, requestCode);
    }
}

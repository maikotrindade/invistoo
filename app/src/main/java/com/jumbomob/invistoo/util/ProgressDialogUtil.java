package com.jumbomob.invistoo.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * @author maiko.trindade
 * @since 10/08/2016
 */
public class ProgressDialogUtil {
    private static ProgressDialogUtil progressDialogUtil;
    private ProgressDialog progressDialog;

    /**
     * Get an instance of ProgressDialog
     * @return a instance of Android ProgressDialog
     */
    public static ProgressDialogUtil getInstance() {
        if (null == progressDialogUtil) {
            progressDialogUtil = new ProgressDialogUtil();
        }
        return progressDialogUtil;
    }

    /**
     * Show Progress Dialog
     *
     * @param context
     * @param message text of Progress Dialog
     */
    public void showProgressDialog(Context context, String message) {
        hideProgressDialog();
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    /**
     * Hide ProgressDialog
     */
    public void hideProgressDialog() {
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}

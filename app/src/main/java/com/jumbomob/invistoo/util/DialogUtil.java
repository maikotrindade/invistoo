package com.jumbomob.invistoo.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.jumbomob.invistoo.R;

/**
 * @author maiko.trindade
 * @since 14/08/2016
 */
public class DialogUtil extends Dialog {

    private static DialogUtil sInstance = null;
    private AlertDialog.Builder builder;

    public DialogUtil(Context context) {
        super(context);
    }

    public static DialogUtil getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DialogUtil(context);
        }
        return sInstance;
    }

    public void show(Context context, String title, String message) {
        builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton(context.getString(R.string.OK), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create();
        builder.show();
    }

    public void show(Context context, int titleResourceId, int messageResourceId, OnClickListener yesListener, OnClickListener noListener) {
        builder = new AlertDialog.Builder(context);

        builder.setTitle(context.getString(titleResourceId));
        builder.setMessage(context.getString(messageResourceId));
        builder.setCancelable(true);

        if (yesListener != null) {
            builder.setPositiveButton(context.getString(R.string.YES), yesListener);
        }

        if (noListener != null) {
            builder.setNegativeButton(context.getString(R.string.NO), noListener);
        }

        builder.create();
        builder.show();
    }

}
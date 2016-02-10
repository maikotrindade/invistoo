package com.jumbomob.invistoo.util;

import android.app.Application;

import com.jumbomob.invistoo.model.persistence.DatabaseHelper;

/**
 * @author maiko.trindade
 * @since 04/01/2016
 */
public class InvistooApplication extends Application {

    private static InvistooApplication sInstance;
    private static DatabaseHelper sDatabaseHelper;

    public static synchronized InvistooApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public DatabaseHelper getDatabaseHelper() {
        if (sDatabaseHelper == null) {
            sDatabaseHelper = new DatabaseHelper(getApplicationContext());
        }
        return sDatabaseHelper;
    }
}

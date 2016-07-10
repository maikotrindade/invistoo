package com.jumbomob.invistoo.util;

import android.app.Application;

import com.jumbomob.invistoo.model.persistence.DatabaseHelper;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * @author maiko.trindade
 * @since 09/02/2016
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
        Fabric.with(this, new Crashlytics());
        sInstance = this;
    }

    public DatabaseHelper getDatabaseHelper() {
        if (sDatabaseHelper == null) {
            sDatabaseHelper = new DatabaseHelper(getApplicationContext());
        }
        return sDatabaseHelper;
    }
}

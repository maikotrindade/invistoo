package com.jumbomob.invistoo.util;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.firebase.client.Firebase;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author maiko.trindade
 * @since 09/02/2016
 */
public class InvistooApplication extends Application {

    private static InvistooApplication sInstance;
    private static Realm sRealm;

    public static synchronized InvistooApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        sInstance = this;
        Firebase.setAndroidContext(this);
    }

    public Realm getDatabaseInstance() {
        if (sRealm == null) {
            RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext())
                    .build();
            Realm.setDefaultConfiguration(config);
            sRealm = Realm.getDefaultInstance();
        }
        return sRealm;
    }
}

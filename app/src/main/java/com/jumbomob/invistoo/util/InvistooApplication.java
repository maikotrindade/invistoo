package com.jumbomob.invistoo.util;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.firebase.client.Firebase;
import com.jumbomob.invistoo.model.entity.User;

import net.danlew.android.joda.JodaTimeAndroid;

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
    private static User sLoggedUser;

    public static synchronized InvistooApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Firebase.setAndroidContext(this);
        Fabric.with(this, new Crashlytics());
        JodaTimeAndroid.init(this);
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

    public static User getLoggedUser() {
        return sLoggedUser;
    }

    public static void setLoggedUser(User loggedUser) {
        InvistooApplication.sLoggedUser = loggedUser;
    }
}

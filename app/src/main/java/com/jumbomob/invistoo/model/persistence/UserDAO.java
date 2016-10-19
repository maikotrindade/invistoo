package com.jumbomob.invistoo.model.persistence;

import com.jumbomob.invistoo.model.entity.User;
import com.jumbomob.invistoo.util.InvistooApplication;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * @author maiko.trindade
 * @since 03/10/2016
 */
public class UserDAO {

    private static UserDAO sDaoInstance;

    public static synchronized UserDAO getInstance() {
        if (sDaoInstance == null) {
            sDaoInstance = new UserDAO();
        }
        return sDaoInstance;
    }

    public void insert(final User user) {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        realm.beginTransaction();
        realm.insert(user);
        realm.commitTransaction();
    }

    public User findByEmail(String email) {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        return realm.where(User.class)
                .equalTo("email", email)
                .findFirst();
    }

    public User findByUid(String uid) {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        return realm.where(User.class)
                .equalTo("uid", uid)
                .findFirst();
    }

    public List<User> findAll() {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        RealmQuery<User> query = realm.where(User.class);
        RealmResults<User> users = query.findAll();
        return users;
    }


}

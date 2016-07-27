package com.jumbomob.invistoo.model.persistence;

import com.jumbomob.invistoo.model.entity.Goal;
import com.jumbomob.invistoo.util.InvistooApplication;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Data Access Object for {@link Goal} domain
 *
 * @author maiko.trindade
 * @since 27/07/2016
 */
public class GoalDAO {

    private static GoalDAO sDaoInstance;

    public static synchronized GoalDAO getInstance() {
        if (sDaoInstance == null) {
            sDaoInstance = new GoalDAO();
        }
        return sDaoInstance;
    }

    public void insert(final List<Goal> goals) {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        realm.beginTransaction();
        realm.insert(goals);
        realm.commitTransaction();
    }

    public List<Goal> findAll() {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        RealmQuery<Goal> query = realm.where(Goal.class);
        RealmResults<Goal> goals = query.findAll();
        return goals;
    }
}
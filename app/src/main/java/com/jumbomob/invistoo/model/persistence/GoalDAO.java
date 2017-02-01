package com.jumbomob.invistoo.model.persistence;

import com.jumbomob.invistoo.model.entity.Goal;
import com.jumbomob.invistoo.util.InvistooApplication;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * Data Access Object for {@link Goal} domain
 *
 * @author maiko.trindade
 * @since 27/07/2016
 */
public class GoalDAO {

    private static GoalDAO sDaoInstance;
    private static Realm sRealm;

    public static synchronized GoalDAO getInstance() {
        if (sDaoInstance == null) {
            sDaoInstance = new GoalDAO();
            sRealm = InvistooApplication.getInstance().getDatabaseInstance();
        }
        return sDaoInstance;
    }

    /**
     * Insert or update a list of {@link Goal}, in case there is a Goal without id, the Goal will
     * be inserted otherwise will be updated
     *
     * @param goals a list of {@link Goal} to be inserted of updated
     */
    public void insertOrUpdate(final List<Goal> goals) {
        sRealm.beginTransaction();
        //delete the old entries
        removeAll();
        for (Goal goal : goals) {
            if (goal.getId() == null) {
                goal.setId(RealmAutoIncrement.getInstance().getNextIdFromModel(Goal.class));
                sRealm.insert(goal);
            } else {
                sRealm.insertOrUpdate(goal);
            }
        }
        sRealm.commitTransaction();
    }

    public void removeAll() {
        sRealm.where(Goal.class).findAll().deleteAllFromRealm();
    }

    /**
     * Find all {@link Goal}
     *
     * @return a list of {@link Goal}
     */
    public List<Goal> findAll() {
        RealmQuery<Goal> query = sRealm.where(Goal.class);
        return sRealm.copyFromRealm(query.findAll());
    }

    /**
     * Update the percentage of {@link Goal}
     *
     * @param goal
     * @param percentage
     */
    public void updatePercentage(Goal goal, Double percentage) {
        sRealm.beginTransaction();
        goal.setPercent(percentage);
        sRealm.commitTransaction();
    }

    /**
     * @param goal
     * @param assetTypeId
     */
    public void updateAssetType(Goal goal, Long assetTypeId) {
        sRealm.beginTransaction();
        goal.setAssetTypeEnum(assetTypeId);
        sRealm.commitTransaction();
    }

}
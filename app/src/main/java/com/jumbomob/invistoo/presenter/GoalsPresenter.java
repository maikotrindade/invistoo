package com.jumbomob.invistoo.presenter;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Goal;
import com.jumbomob.invistoo.model.persistence.GoalDAO;
import com.jumbomob.invistoo.util.SharedPrefsUtil;
import com.jumbomob.invistoo.view.GoalsView;

import java.util.List;

import io.realm.RealmList;

/**
 * @author maiko.trindade
 * @since 22/07/2016
 */
public class GoalsPresenter implements BasePresenter<GoalsView> {

    private GoalsView mView;
    private GoalDAO mGoalDAO;

    @Override
    public void attachView(GoalsView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public GoalsPresenter(GoalsView view) {
        attachView(view);
        mGoalDAO = GoalDAO.getInstance();
    }

    public RealmList<Goal> getGoals() {
        return mGoalDAO.findAll();
    }

    public void saveGoals(List<Goal> goals) {
        if (isValidPercentage(goals)) {
            mGoalDAO.insertOrUpdate(goals);
            SharedPrefsUtil.setUserHasGoals(true);
            mView.showMessage(R.string.msg_goals_success);
        }
    }

    private boolean isValidPercentage(List<Goal> goals) {
        double sum = 0;
        for (Goal goal : goals) {
            sum += goal.getPercent();
        }

        if (sum == 100) {
            return true;
        } else {
            mView.showDialog(R.string.error, R.string.error_goal_percentage);
            return false;
        }
    }

}

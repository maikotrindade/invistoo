package com.jumbomob.invistoo.presenter;

import android.content.Context;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Goal;
import com.jumbomob.invistoo.model.persistence.GoalDAO;
import com.jumbomob.invistoo.util.ConstantsUtil;
import com.jumbomob.invistoo.util.DialogUtil;
import com.jumbomob.invistoo.util.InvistooApplication;
import com.jumbomob.invistoo.util.InvistooUtil;
import com.jumbomob.invistoo.view.GoalsView;

import java.util.ArrayList;
import java.util.List;

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

    public List<Goal> getGoals() {
        final String userUid = InvistooApplication.getLoggedUser().getUid();
        return mGoalDAO.findAll(userUid);
    }

    public void saveGoals(List<Goal> goals, boolean isNewInvestmentFlow) {
        if (areValidGoals(goals)) {
            mGoalDAO.insertOrUpdate(goals);
            mView.showMessage(R.string.msg_goals_success);
            if (isNewInvestmentFlow) {
                mView.navigateToNewInvestmentScreen();
            }
        }
    }

    private boolean areValidGoals(List<Goal> goals) {
        boolean valid = true;
        //validating sum of goals and repeated assetTypes
        List<Long> assetTypeIds = new ArrayList<>();
        double sum = 0;
        for (Goal goal : goals) {
            sum += goal.getPercent();
            assetTypeIds.add(goal.getAssetTypeEnum());
            if (goal.getAssetTypeEnum() == null) {
                mView.showDialog(R.string.error, R.string.error_goal_asset_type_wrong);
                return false;
            }
        }
        if (sum != 100) {
            mView.showDialog(R.string.error, R.string.error_goal_percentage);
            valid = false;
        } else {
            if (InvistooUtil.areThereDuplicates(assetTypeIds)) {
                mView.showDialog(R.string.error, R.string.error_goal_asset_type_enum);
                return false;
            }
        }
        return valid;
    }

    public double getPercentLeft(List<Goal> goals) {
        double sum = 0;
        for (Goal goal : goals) {
            sum += goal.getPercent();
        }
        final double percentLeft = 100 - sum;
        return (percentLeft > 0) ? percentLeft : 0;
    }

    public void addNewGoal(final List<Goal> goals, Context context) {
        if (goals.size() <= ConstantsUtil.MAX_NUMBER_OF_GOALS) {
            final String userUid = InvistooApplication.getLoggedUser().getUid();
            final Goal goal = new Goal();
            goal.setUserId(userUid);
            goal.setPercent(getPercentLeft(goals));
            goals.add(goal);
            mView.updateGoalList(goals);
        } else {
            DialogUtil.getInstance(context).show(context, context.getString(R.string.app_name),
                    context.getString(R.string.limit_goals));
        }
    }

}

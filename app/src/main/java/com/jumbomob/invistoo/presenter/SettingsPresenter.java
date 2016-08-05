package com.jumbomob.invistoo.presenter;

import com.jumbomob.invistoo.model.entity.Goal;
import com.jumbomob.invistoo.model.persistence.GoalDAO;
import com.jumbomob.invistoo.view.SettingsView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maiko.trindade
 * @since 22/07/2016
 */
public class SettingsPresenter implements BasePresenter<SettingsView> {

    private SettingsView mView;

    @Override
    public void attachView(SettingsView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public List<Goal> getGoals() {
        final GoalDAO goalDAO = GoalDAO.getInstance();
        final List<Goal> goals = goalDAO.findAll();
        if (goals != null && !goals.isEmpty()) {
            return goals;
        }
        return new ArrayList<>();
    }

}

package com.jumbomob.invistoo.view;

import com.jumbomob.invistoo.model.entity.Goal;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 22/07/2016
 */
public interface GoalsView {

    void showMessage(int resourceId);

    void showDialog(int titleResourceId, int messageResourceId);

    void navigateToNewInvestmentScreen();

    void updateGoalList(List<Goal> goals);

}
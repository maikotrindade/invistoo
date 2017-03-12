package com.jumbomob.invistoo.presenter;

import com.jumbomob.invistoo.business.OperationsManager;
import com.jumbomob.invistoo.model.dto.GrossValueDTO;
import com.jumbomob.invistoo.model.dto.InvestmentSuggestionDTO;
import com.jumbomob.invistoo.model.entity.Goal;
import com.jumbomob.invistoo.model.persistence.GoalDAO;
import com.jumbomob.invistoo.util.InvistooApplication;
import com.jumbomob.invistoo.view.GrossValuesListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public class GrossValuesListPresenter implements BasePresenter<GrossValuesListView> {

    private final static String TAG = GrossValuesListPresenter.class.getSimpleName();

    private GrossValuesListView mView;

    @Override
    public void attachView(GrossValuesListView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public GrossValuesListPresenter(GrossValuesListView view) {
        attachView(view);
    }

    public List<GrossValueDTO> getGrossValues() {
        List<GrossValueDTO> grossValues = new ArrayList<>();
        final GoalDAO goalDAO = GoalDAO.getInstance();
        final String userUid = InvistooApplication.getLoggedUser().getUid();
        List<Goal> goals = goalDAO.findAll(userUid);
        for (Goal goal : goals) {
            GrossValueDTO grossValue = new GrossValueDTO();
            grossValue.setUserId(userUid);
            grossValue.setAssetType(goal.getAssetTypeEnum());
            grossValues.add(grossValue);
        }
        return grossValues;
    }

    public List<InvestmentSuggestionDTO> calculateBalance(Double contribution, List<GrossValueDTO> grossValues) {
        final OperationsManager operationsManager = new OperationsManager();
        return operationsManager.calculateBalance(contribution, grossValues);
    }

}
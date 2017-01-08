package com.jumbomob.invistoo.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.jumbomob.invistoo.model.entity.Investment;
import com.jumbomob.invistoo.model.persistence.InvestmentDAO;
import com.jumbomob.invistoo.util.NumericUtil;
import com.jumbomob.invistoo.util.SharedPrefsUtil;
import com.jumbomob.invistoo.view.InvestmentListView;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public class InvestmentListPresenter implements BasePresenter<InvestmentListView> {

    private InvestmentListView mView;

    @Override
    public void attachView(InvestmentListView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public InvestmentListPresenter(InvestmentListView view) {
        attachView(view);
    }

    public boolean isContributionValid(String contribution) {
        return !TextUtils.isEmpty(contribution) && NumericUtil.isValidDouble(contribution);
    }

    public void orderListByDate(boolean sortAsc) {
        final InvestmentDAO dao = InvestmentDAO.getInstance();
        mView.updateList(dao.findAllOrderedByDate(sortAsc));
    }

    public List<Investment> findInvestments() {
        final InvestmentDAO dao = InvestmentDAO.getInstance();
        return dao.findAll();
    }

    public void redirectUserNewInvestment(final Context context) {
        final boolean userHasGoals = SharedPrefsUtil.userHasGoals(context);
        if (userHasGoals) {
            mView.showContributionDialog();
        } else {
            mView.showGoalsDialog();
        }
    }
}
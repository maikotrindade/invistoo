package com.jumbomob.invistoo.presenter;

import com.jumbomob.invistoo.view.NewBalancedInvestmentView;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public class NewBalancedInvestmentPresenter implements BasePresenter<NewBalancedInvestmentView> {

    private NewBalancedInvestmentView mView;

    @Override
    public void attachView(NewBalancedInvestmentView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public NewBalancedInvestmentPresenter(NewBalancedInvestmentView view) {
        attachView(view);
    }
}
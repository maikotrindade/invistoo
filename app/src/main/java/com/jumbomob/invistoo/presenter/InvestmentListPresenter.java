package com.jumbomob.invistoo.presenter;

import com.jumbomob.invistoo.view.InvestmentListView;

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
}
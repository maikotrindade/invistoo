package com.jumbomob.invistoo.presenter;

import com.jumbomob.invistoo.view.NewInvestmentView;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public class NewInvestmentPresenter implements BasePresenter<NewInvestmentView> {

    private NewInvestmentView mView;

    @Override
    public void attachView(NewInvestmentView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public NewInvestmentPresenter(NewInvestmentView view) {
        attachView(view);
    }
}
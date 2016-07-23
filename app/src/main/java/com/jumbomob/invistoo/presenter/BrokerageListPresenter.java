package com.jumbomob.invistoo.presenter;

import com.jumbomob.invistoo.view.BrokerageListView;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public class BrokerageListPresenter implements BasePresenter<BrokerageListView> {

    private BrokerageListView mView;

    @Override
    public void attachView(BrokerageListView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
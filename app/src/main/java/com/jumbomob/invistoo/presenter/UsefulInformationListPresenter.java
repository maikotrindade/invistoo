package com.jumbomob.invistoo.presenter;

import com.jumbomob.invistoo.view.UsefulInformationListView;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public class UsefulInformationListPresenter implements BasePresenter<UsefulInformationListView> {

    private UsefulInformationListView mView;

    @Override
    public void attachView(UsefulInformationListView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public UsefulInformationListPresenter(UsefulInformationListView view) {
        attachView(view);
    }
}
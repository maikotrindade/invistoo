package com.jumbomob.invistoo.presenter;

import com.jumbomob.invistoo.view.HomeView;

/**
 * @author maiko.trindade
 * @since 13/07/2016
 */
public class HomePresenter implements BasePresenter<HomeView> {

    private HomeView mHomeView;

    @Override
    public void attachView(HomeView view) {
        mHomeView = view;
    }

    @Override
    public void detachView() {
        mHomeView = null;
    }
}
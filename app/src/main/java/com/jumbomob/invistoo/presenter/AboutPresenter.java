package com.jumbomob.invistoo.presenter;

import com.jumbomob.invistoo.view.AboutView;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public class AboutPresenter implements BasePresenter<AboutView> {

    private AboutView mView;

    @Override
    public void attachView(AboutView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
package com.jumbomob.invistoo.presenter;

import com.jumbomob.invistoo.view.SettingsView;

/**
 * @author maiko.trindade
 * @since 22/07/2016
 */
public class SettingsPresenter implements BasePresenter<SettingsView> {

    private SettingsView mView;

    @Override
    public void attachView(SettingsView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

}

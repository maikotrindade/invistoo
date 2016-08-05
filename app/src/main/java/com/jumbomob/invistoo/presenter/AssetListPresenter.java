package com.jumbomob.invistoo.presenter;

import com.jumbomob.invistoo.view.AssetListView;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public class AssetListPresenter implements BasePresenter<AssetListView> {

    private AssetListView mView;

    @Override
    public void attachView(AssetListView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }


}
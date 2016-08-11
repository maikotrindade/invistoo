package com.jumbomob.invistoo.presenter;

import com.jumbomob.invistoo.view.AssetChartView;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public class AssetChartPresenter implements BasePresenter<AssetChartView> {

    private AssetChartView mView;

    @Override
    public void attachView(AssetChartView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public AssetChartPresenter(AssetChartView view) {
        attachView(view);
    }
}
package com.jumbomob.invistoo.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.presenter.AssetChartPresenter;
import com.jumbomob.invistoo.view.AssetChartView;

/**
 * @author maiko.trindade
 * @since 03/04/2016
 */
public class AssetChartFragment extends BaseFragment implements AssetChartView {

    private static final String TAG = AssetChartFragment.class.getSimpleName();

    private View mRootView;
    private AssetChartPresenter mPresenter;
    private LineChart mChart;

    public static AssetChartFragment newInstance() {
        AssetChartFragment fragment = new AssetChartFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mRootView = inflater.inflate(R.layout.fragment_asset_chart, container, false);
        mPresenter = new AssetChartPresenter(this);
        configureChart();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.my_investments);
    }

    @Override
    public void showProgressDialog(final int resourceId) {
        super.showProgressDialog(resourceId);
    }

    @Override
    public void hideProgressDialog() {
        super.hideProgressDialog();
    }

    protected void configureChart() {
        mChart = (LineChart) mRootView.findViewById(R.id.asset_line_chart);

        mChart.setDescription("");
        mChart.setNoDataText(getString(R.string.no_data_chart));
        mChart.setTouchEnabled(true);
        mChart.setDragDecelerationFrictionCoef(0.9f);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);
        mChart.setPinchZoom(true);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setData(mPresenter.setData(20, 30));

        mChart.animateX(3500);
        // get the legend (only possible after setting data)

        Legend legend = mChart.getLegend();
        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(11f);
        legend.setTextColor(Color.DKGRAY);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
//        l.setYOffset(11f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.DKGRAY);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setSpaceBetweenLabels(1);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaxValue(200f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setTextColor(Color.RED);
        rightAxis.setAxisMaxValue(900);
        rightAxis.setAxisMinValue(-200);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
    }

}
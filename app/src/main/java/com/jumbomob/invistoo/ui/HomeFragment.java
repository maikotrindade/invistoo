package com.jumbomob.invistoo.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.presenter.HomePresenter;
import com.jumbomob.invistoo.view.HomeView;

/**
 * @author maiko.trindade
 * @since 30/03/2016
 */
public class HomeFragment extends BaseFragment implements HomeView {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private View mRootView;

    private PieChart mChart;
    private HomePresenter mPresenter;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRootView = inflater.inflate(R.layout.fragment_home, container, false);
        mPresenter = new HomePresenter(this);
        configureChart();
        return mRootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.investment_list_context_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.title_home);
    }

    private void configureChart() {
        mChart = (PieChart) mRootView.findViewById(R.id.investments_chart);

        mChart.setUsePercentValues(true);
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(20f);
        mChart.setTransparentCircleRadius(22f);
        mChart.setDrawCenterText(true);
        mChart.setDescription("");
        mChart.setNoDataText(getString(R.string.no_data_chart));
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int dataSetIndex, Highlight highlight) {
                if (entry == null) {
                    return;
                }
                Log.i(TAG, "Value: " + entry.getVal() + ", xIndex: " + entry.getXIndex() + ", DataSet index: " + dataSetIndex);
            }

            @Override
            public void onNothingSelected() {
                Log.i(TAG, "nothing selected");
            }
        });

        mPresenter.getChartData(mChart);
    }

    @Override
    public void setChartData(final PieData data) {
        mChart.setData(data);
        mChart.highlightValues(null);
        mChart.invalidate();
    }
}
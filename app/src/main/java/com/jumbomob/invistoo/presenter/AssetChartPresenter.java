package com.jumbomob.invistoo.presenter;

import android.graphics.Color;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jumbomob.invistoo.view.AssetChartView;

import java.util.ArrayList;

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

    public LineData setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            xVals.add((i) + "");
        }

        ArrayList<Entry> yVals1 = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float mult = range / 2f;
            float val = (float) (Math.random() * mult) + 50;
            yVals1.add(new Entry(val, i));
        }

        LineDataSet set1 = new LineDataSet(yVals1, "ASSET");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setCircleColor(Color.BLUE);
        set1.setLineWidth(2f);
        set1.setCircleRadius(3f);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1);

        LineData data = new LineData(xVals, dataSets);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        return data;
    }
}
package com.jumbomob.invistoo.view;

import com.github.mikephil.charting.data.PieData;

/**
 * @author maiko.trindade
 * @since 13/07/2016
 */
public interface DashboardView {

    void setChartData(PieData data);

    void setNoChartData();
}

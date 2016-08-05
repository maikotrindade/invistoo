package com.jumbomob.invistoo.presenter;

import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.jumbomob.invistoo.model.dto.InvestmentChartDTO;
import com.jumbomob.invistoo.model.entity.AssetTypeEnum;
import com.jumbomob.invistoo.model.entity.Investment;
import com.jumbomob.invistoo.model.persistence.InvestmentDAO;
import com.jumbomob.invistoo.util.ChartUtil;
import com.jumbomob.invistoo.view.HomeView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author maiko.trindade
 * @since 13/07/2016
 */
public class HomePresenter implements BasePresenter<HomeView> {

    private HomeView mHomeView;

    public HomePresenter(HomeView view) {
        this.mHomeView = view;
    }

    @Override
    public void attachView(HomeView view) {
        mHomeView = view;
    }

    @Override
    public void detachView() {
        mHomeView = null;
    }

    public void getChartData(PieChart chart) {
        final InvestmentDAO dao = InvestmentDAO.getInstance();
        Long total = new Long(0);
        List<InvestmentChartDTO> chartDTOs = new ArrayList<>();
        final List<AssetTypeEnum> assetTypes = Arrays.asList(AssetTypeEnum.values());
        for (AssetTypeEnum assetTypeEnum : assetTypes) {
            final List<Investment> investments = dao.findByAssetType(assetTypeEnum.getId());
            if (investments != null && !investments.isEmpty()) {
                InvestmentChartDTO chartDTO = new InvestmentChartDTO();
                chartDTO.setDescription(assetTypeEnum.getTitle());
                chartDTO.setInvestments(investments);
                Long chartDTOSum = new Long(0);
                for (Investment investment : investments) {
                    chartDTOSum = chartDTOSum + Long.parseLong(investment.getPrice());
                }
                total = total + chartDTOSum;
                chartDTO.setSum(chartDTOSum);
                chartDTOs.add(chartDTO);
            }
        }

        PieData pieData;
        if (chartDTOs.size() > 0) {
            ArrayList<Entry> chartEntries = new ArrayList<>();
            ArrayList<String> subtitles = new ArrayList<>();
            for (int index = 0; index < chartDTOs.size(); index++) {
                chartEntries.add(new Entry((float) (total / chartDTOs.get(index).getSum()), index));
                subtitles.add(chartDTOs.get(index).getDescription());
            }

            PieDataSet dataSet = new PieDataSet(chartEntries, "Total Investido");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(ChartUtil.getMaterialTheme());

            pieData = new PieData(subtitles, dataSet);
            pieData.setValueFormatter(new PercentFormatter());
            pieData.setValueTextSize(11f);
            pieData.setValueTextColor(Color.DKGRAY);
            generateLegend(chart.getLegend());
            mHomeView.setChartData(pieData);
        }
    }

    private void generateLegend(Legend legend) {
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        legend.setXEntrySpace(7f);
        legend.setYEntrySpace(0f);
        legend.setYOffset(0f);
    }
}
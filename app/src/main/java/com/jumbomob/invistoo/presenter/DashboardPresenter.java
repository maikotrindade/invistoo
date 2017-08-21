package com.jumbomob.invistoo.presenter;

import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.jumbomob.invistoo.model.dto.BalanceChartDTO;
import com.jumbomob.invistoo.model.entity.AssetTypeEnum;
import com.jumbomob.invistoo.model.entity.Balance;
import com.jumbomob.invistoo.model.entity.Investment;
import com.jumbomob.invistoo.model.persistence.BalanceDAO;
import com.jumbomob.invistoo.model.persistence.InvestmentDAO;
import com.jumbomob.invistoo.util.ChartUtil;
import com.jumbomob.invistoo.util.InvistooApplication;
import com.jumbomob.invistoo.view.DashboardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author maiko.trindade
 * @since 13/07/2016
 */
public class DashboardPresenter implements BasePresenter<DashboardView> {

    private DashboardView mHomeView;

    public DashboardPresenter(DashboardView view) {
        attachView(view);
    }

    @Override
    public void attachView(DashboardView view) {
        mHomeView = view;
    }

    @Override
    public void detachView() {
        mHomeView = null;
    }

    public void getChartData(PieChart chart) {
        final BalanceDAO dao = BalanceDAO.getInstance();
        final String userId = InvistooApplication.getLoggedUser().getUid();
        Long total = new Long(0);
        List<BalanceChartDTO> chartDTOs = new ArrayList<>();
        final List<AssetTypeEnum> assetTypes = Arrays.asList(AssetTypeEnum.values());
        for (AssetTypeEnum assetTypeEnum : assetTypes) {
            final Balance balance = dao.getBalanceByAssetId(assetTypeEnum.getId(), userId);
            if (balance != null) {
                BalanceChartDTO chartDTO = new BalanceChartDTO();
                chartDTO.setDescription(assetTypeEnum.getAbbreviation());
                final long balanceTotal = balance.getTotal().longValue();
                chartDTO.setSum(balanceTotal);
                chartDTOs.add(chartDTO);
                total = total + balanceTotal;
            }
        }

        PieData pieData;
        if (chartDTOs.size() > 0) {
            ArrayList<Entry> chartEntries = new ArrayList<>();
            ArrayList<String> subtitles = new ArrayList<>();
            for (int index = 0; index < chartDTOs.size(); index++) {
                chartEntries.add(new Entry((float) (chartDTOs.get(index).getSum()), index));
                subtitles.add(chartDTOs.get(index).getDescription());
            }

            PieDataSet dataSet = new PieDataSet(chartEntries, "");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(ChartUtil.getMaterialTheme());

            pieData = new PieData(subtitles, dataSet);
            pieData.setValueFormatter(new PercentFormatter());
            pieData.setValueTextSize(11f);
            pieData.setValueTextColor(Color.DKGRAY);
            generateLegend(chart.getLegend());
            mHomeView.setChartData(pieData);
        } else {
            mHomeView.setNoChartData();
        }
    }

    private void generateLegend(Legend legend) {
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        legend.setXEntrySpace(7f);
        legend.setYEntrySpace(0f);
        legend.setYOffset(0f);
    }

    public Double getBalanceBought() {
        final String userUid = InvistooApplication.getLoggedUser().getUid();
        final List<Balance> balances = BalanceDAO.getInstance().findAllBalances(userUid);
        Double balanceTotal = new Double(0);
        for (Balance balance : balances) {
            balanceTotal += balance.getTotal();
        }
        return balanceTotal;
    }

    public Long getBalanceSold() {
        InvestmentDAO investmentDAO = InvestmentDAO.getInstance();
        final String userUid = InvistooApplication.getLoggedUser().getUid();
        final List<Investment> soldInvestments = investmentDAO.findSoldInvestments(userUid);
        long balanceSold = 0;
        for (Investment sold : soldInvestments) {
            balanceSold += Long.parseLong(sold.getPrice());
        }
        return balanceSold;
    }

    public List<Balance> getBalanceAssets() {
        final String userId = InvistooApplication.getLoggedUser().getUid();
        return BalanceDAO.getInstance().getBalances(userId);
    }

    public void editBalance(long assetId, Double newTotal) {
        final String userId = InvistooApplication.getLoggedUser().getUid();
        BalanceDAO.getInstance().update(assetId, newTotal, userId);
    }
}
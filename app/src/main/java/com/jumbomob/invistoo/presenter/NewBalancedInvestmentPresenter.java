package com.jumbomob.invistoo.presenter;

import android.util.Log;

import com.jumbomob.invistoo.model.dto.InvestmentTest;
import com.jumbomob.invistoo.model.entity.AssetTypeEnum;
import com.jumbomob.invistoo.model.entity.Goal;
import com.jumbomob.invistoo.model.entity.Investment;
import com.jumbomob.invistoo.model.persistence.GoalDAO;
import com.jumbomob.invistoo.model.persistence.InvestmentDAO;
import com.jumbomob.invistoo.util.NumericUtil;
import com.jumbomob.invistoo.view.NewBalancedInvestmentView;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public class NewBalancedInvestmentPresenter implements BasePresenter<NewBalancedInvestmentView> {

    private final static String TAG = NewBalancedInvestmentPresenter.class.getSimpleName();

    private NewBalancedInvestmentView mView;

    @Override
    public void attachView(NewBalancedInvestmentView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public NewBalancedInvestmentPresenter(NewBalancedInvestmentView view) {
        attachView(view);
    }

    public void calculateBalance(Double aporte) {
        Log.d(TAG, "Aporte: " + aporte);

        final GoalDAO goalDAO = GoalDAO.getInstance();
        final InvestmentDAO investmentDAO = InvestmentDAO.getInstance();

        //encontrar porcentagem de cada meta
        final RealmList<Goal> goals = goalDAO.findAll();

        List<InvestmentTest> investTestList = new ArrayList<>();

        for (Goal goal : goals) {
            InvestmentTest invesTest = new InvestmentTest();
            Log.d(TAG, "Goal com assetType #" + goal.getAssetTypeEnum());

            //quantidade investida em cada assetType
            final List<Investment> byAssetType = investmentDAO.findByAssetType(goal
                    .getAssetTypeEnum());
            Double sum = 0D;
            for (Investment investment : byAssetType) {
                sum += (NumericUtil.getValidDouble(investment.getPrice())
                        * investment.getQuantity());
            }

            Log.d(TAG, "Goal com somatoria de :" + sum + "\n\n");

            invesTest.setAssetType(goal.getAssetTypeEnum());
            invesTest.setSum(sum);

            investTestList.add(invesTest);
        }

        Double totalinvestido = 0D;
        //calcula o total investido
        for (InvestmentTest investmentTest : investTestList) {
            totalinvestido += investmentTest.getSum();
        }

        Log.d(TAG, "\nTotal Investido :" + totalinvestido + "\n\n");

        //VAT - total investido atualmente em todso os ativos + aporte
        Double vat = totalinvestido + aporte;

        Log.d(TAG, "\n\nVAT (total investido atualmente em todso os ativos + aporte):" + vat +
                "\n\n");
        // ------------------------------------------------------------------******************

        for (Goal goal : goals) {

            //quantidade investida em cada assetType
            final List<Investment> byAssetType = investmentDAO.findByAssetType(goal
                    .getAssetTypeEnum());
            Double sum = 0D;
            for (Investment investment : byAssetType) {
                sum += (NumericUtil.getValidDouble(investment.getPrice())
                        * investment.getQuantity());
            }

            double balancedValue = (vat * (goal.getPercent() / 100) - sum);
            AssetTypeEnum assetTypeEnum = AssetTypeEnum.getById(goal.getAssetTypeEnum());
            Log.d(TAG, "Valor balanceado: " + balancedValue +
                    " para o AssetEnum: " + assetTypeEnum.getTitle());
        }
    }
}
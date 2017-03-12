package com.jumbomob.invistoo.business;

import android.util.Log;

import com.jumbomob.invistoo.model.dto.GrossValueDTO;
import com.jumbomob.invistoo.model.dto.InvestmentSuggestionDTO;
import com.jumbomob.invistoo.model.entity.AssetTypeEnum;
import com.jumbomob.invistoo.model.entity.Goal;
import com.jumbomob.invistoo.model.entity.Investment;
import com.jumbomob.invistoo.model.persistence.GoalDAO;
import com.jumbomob.invistoo.model.persistence.InvestmentDAO;
import com.jumbomob.invistoo.util.InvistooApplication;
import com.jumbomob.invistoo.util.NumericUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maiko.trindade
 * @since 28/08/2016
 */
public class OperationsManager {

    private final static String TAG = OperationsManager.class.getSimpleName();

    public List<InvestmentSuggestionDTO> calculateBalance(Double aporte) {
        Log.d(TAG, "Aporte: " + aporte);

        final GoalDAO goalDAO = GoalDAO.getInstance();
        final InvestmentDAO investmentDAO = InvestmentDAO.getInstance();

        //busca porcentagem de cada meta
        final String userUid = InvistooApplication.getLoggedUser().getUid();
        final List<Goal> goals = goalDAO.findAll(userUid);

        List<InvestmentSuggestionDTO> auxInvestmentList = new ArrayList<>();

        for (Goal goal : goals) {
            InvestmentSuggestionDTO invesTest = new InvestmentSuggestionDTO();
            Log.d(TAG, "Goal com assetType #" + goal.getAssetTypeEnum());

            //busca quantidade investida em cada assetType
            final List<Investment> byAssetType = investmentDAO.findByAssetType(goal
                    .getAssetTypeEnum(), userUid);
            Double sum = 0D;
            for (Investment investment : byAssetType) {
                sum += (NumericUtil.getValidDouble(investment.getPrice())
                        * investment.getQuantity());
            }

            Log.d(TAG, "Goal com somatoria de :" + sum + "\n\n");

            invesTest.setAssetType(goal.getAssetTypeEnum());
            invesTest.setTotal(sum);

            auxInvestmentList.add(invesTest);
        }

        Double totalinvestido = 0D;
        //calcula o total investido
        for (InvestmentSuggestionDTO investment : auxInvestmentList) {
            totalinvestido += investment.getTotal();
        }

        Log.d(TAG, "\nTotal Investido :" + totalinvestido + "\n\n");

        //VAT - total investido atualmente em todso os ativos + aporte
        Double vat = totalinvestido + aporte;

        Log.d(TAG, "\n\nVAT (total investido atualmente em todso os ativos + aporte):" + vat +
                "\n\n");
        // ------------------------------------------------------------------******************

        List<InvestmentSuggestionDTO> balancedInvestments = new ArrayList<>();
        for (Goal goal : goals) {
            InvestmentSuggestionDTO suggestion = new InvestmentSuggestionDTO();
            suggestion.setAssetType(goal.getAssetTypeEnum());

            //quantidade investida em cada assetType
            final List<Investment> byAssetType = investmentDAO.findByAssetType(goal
                    .getAssetTypeEnum(), userUid);
            Double sum = 0D;
            for (Investment investment : byAssetType) {
                sum += (NumericUtil.getValidDouble(investment.getPrice())
                        * investment.getQuantity());
            }

            double balancedValue = (vat * (goal.getPercent() / 100) - sum);
            suggestion.setSuggestion((long) balancedValue);
            suggestion.setTotal(sum);
            balancedInvestments.add(suggestion);

            //TODO apagar LOG
            AssetTypeEnum assetTypeEnum = AssetTypeEnum.getById(goal.getAssetTypeEnum());
            Log.d(TAG, "Valor balanceado: " + balancedValue +
                    " para o AssetEnum: " + assetTypeEnum.getTitle());
        }

        return balancedInvestments;
    }

    public List<InvestmentSuggestionDTO> calculateBalance(Double contribution, List<GrossValueDTO> grossValues) {
        Log.d(TAG, "Aporte: " + contribution);

        final GoalDAO goalDAO = GoalDAO.getInstance();
        final InvestmentDAO investmentDAO = InvestmentDAO.getInstance();

        //busca porcentagem de cada meta
        final String userUid = InvistooApplication.getLoggedUser().getUid();
        final List<Goal> goals = goalDAO.findAll(userUid);

        List<InvestmentSuggestionDTO> auxInvestmentList = new ArrayList<>();

        Double grossAmount = 0D;
        for (Goal goal : goals) {
            InvestmentSuggestionDTO suggestion = new InvestmentSuggestionDTO();
            Log.d(TAG, "Goal com assetType #" + goal.getAssetTypeEnum());

            String goalAsset = AssetTypeEnum.getById(goal.getAssetTypeEnum()).getTitle();

            for (GrossValueDTO grossValue : grossValues) {
                if (grossValue.getAssetType().equals(goal.getAssetTypeEnum())) {
                    grossAmount = grossValue.getAmount();
                }
            }

            Log.d(TAG, "Goal " + goalAsset + " com gross value de : " + grossAmount + "\n\n");
            suggestion.setAssetType(goal.getAssetTypeEnum());
            suggestion.setTotal(grossAmount);
            auxInvestmentList.add(suggestion);
        }

        Double totalinvestido = 0D;
        //calcula o total investido
        for (InvestmentSuggestionDTO investment : auxInvestmentList) {
            totalinvestido += investment.getTotal();
        }

        Log.d(TAG, "\nTotal Investido :" + totalinvestido + "\n\n");

        //VAT - total investido atualmente em todso os ativos + aporte
        Double vat = totalinvestido + contribution;

        Log.d(TAG, "\n\nVAT (total investido atualmente em todso os ativos + aporte):" + vat +
                "\n\n");
        // ------------------------------------------------------------------******************

        List<InvestmentSuggestionDTO> balancedInvestments = new ArrayList<>();
        for (Goal goal : goals) {
            InvestmentSuggestionDTO suggestion = new InvestmentSuggestionDTO();
            suggestion.setAssetType(goal.getAssetTypeEnum());

            Double grossAmountFinal = 0D;
            for (GrossValueDTO grossValue : grossValues) {
                if (grossValue.getAssetType().equals(goal.getAssetTypeEnum())) {
                    grossAmountFinal = grossValue.getAmount();
                }
            }

            double balancedValue = (vat * (goal.getPercent() / 100) - grossAmountFinal);
            suggestion.setSuggestion((long) balancedValue);
            suggestion.setTotal(grossAmountFinal);
            balancedInvestments.add(suggestion);

            //TODO apagar LOG
            AssetTypeEnum assetTypeEnum = AssetTypeEnum.getById(goal.getAssetTypeEnum());
            Log.d(TAG, "Valor balanceado: " + balancedValue +
                    " para o AssetEnum: " + assetTypeEnum.getTitle());
        }

        return balancedInvestments;
    }

}

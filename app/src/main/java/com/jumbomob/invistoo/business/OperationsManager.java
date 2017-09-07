package com.jumbomob.invistoo.business;

import com.jumbomob.invistoo.model.dto.InvestmentSuggestionDTO;
import com.jumbomob.invistoo.model.entity.Balance;
import com.jumbomob.invistoo.model.entity.Goal;
import com.jumbomob.invistoo.model.persistence.BalanceDAO;
import com.jumbomob.invistoo.model.persistence.GoalDAO;
import com.jumbomob.invistoo.util.InvistooApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maiko.trindade
 * @since 28/08/2016
 */
public class OperationsManager {

    private final static String TAG = OperationsManager.class.getSimpleName();

    public List<InvestmentSuggestionDTO> calculateBalance(Double aporte) {
        final GoalDAO goalDAO = GoalDAO.getInstance();

        //1. busca porcentagem de cada meta
        final String userUid = InvistooApplication.getLoggedUser().getUid();
        final List<Goal> goals = goalDAO.findAll(userUid);

        //2. busca quantidade investida em cada assetType
        final List<InvestmentSuggestionDTO> oldInvestmentsData = new ArrayList<>();
        final String userId = InvistooApplication.getLoggedUser().getUid();
        final List<Balance> balances = BalanceDAO.getInstance().getBalances(userId);
        Double totalInvestido = 0D;
        for (Balance balance : balances) {
            totalInvestido += balance.getTotal();
            InvestmentSuggestionDTO suggestionDTO = new InvestmentSuggestionDTO();
            suggestionDTO.setAssetType(balance.getAsset());
            suggestionDTO.setTotal(balance.getTotal());
            oldInvestmentsData.add(suggestionDTO);
        }

        //3. VAT - total investido atualmente em todos os ativos + aporte
        final Double vat = totalInvestido + aporte;

        //4. Retira a porcentagem de cada assetType do VAT para sabe qual a porcentagem correta que deveria ficar o balanceamento
        final List<InvestmentSuggestionDTO> balancedInvestments = new ArrayList<>();
        if (!oldInvestmentsData.isEmpty()) {
            for (InvestmentSuggestionDTO oldInvestmentData : oldInvestmentsData) {
                final InvestmentSuggestionDTO suggestion = new InvestmentSuggestionDTO();
                suggestion.setAssetType(oldInvestmentData.getAssetType());

                Goal goal = null;
                for (Goal goalSearch : goals) {
                    if (oldInvestmentData.getAssetType().equals(goalSearch.getAssetTypeEnum())) {
                        goal = goalSearch;
                        break;
                    }
                }

                //5. Faz a diferença de como deveria e como está o balanceamento para achar o valor da divisão do aporte
                final double valorFuturo = vat * (goal.getPercent() / 100);
                final double valorAtual = oldInvestmentData.getTotal();
                suggestion.setSuggestion(valorFuturo - valorAtual);
                suggestion.setTotal(valorAtual);
                balancedInvestments.add(suggestion);
            }
        } else {
            for (Goal goal : goals) {
                final InvestmentSuggestionDTO suggestion = new InvestmentSuggestionDTO();
                suggestion.setAssetType(goal.getAssetTypeEnum());
                //5. Faz a diferença de como deveria e como está o balanceamento para achar o valor da divisão do aporte
                suggestion.setSuggestion(vat * (goal.getPercent() / 100));
                suggestion.setTotal(new Double(0));
                balancedInvestments.add(suggestion);
            }
        }

        return isValidSuggestion(balancedInvestments, aporte) ? balancedInvestments : null;
    }

    private boolean isValidSuggestion(List<InvestmentSuggestionDTO> suggestions, Double aporte) {
        Double suggestionsSum = new Double(0);
        for (InvestmentSuggestionDTO suggestion : suggestions) {
            if (suggestion.getTotal() < 0) {
                return false;
            }
            suggestionsSum += suggestion.getTotal();
        }
        if (suggestionsSum > aporte) {
            return false;
        }
        return true;
    }

//    private double calculateIncomeTax(Investment investment) {
//        final AssetDAO assetDAO = AssetDAO.getInstance();
//        final AssetTypeEnum assetTypeEnum = AssetTypeEnum.getById(investment.getAssetType());
//        final Asset asset = assetDAO.findAssetLastById(assetTypeEnum.getId());
//        double amount = 0;
//        if (asset != null) {
//            amount = (getValidDouble(investment.getPrice()));// * investment.getQuantity());
//            final Date assetDueDate = DateUtil.stringToDate(asset.getDueDate(), DateUtil.SIMPLE_DATE_FORMAT);
//            final DateTime dueDate = new DateTime(assetDueDate);
//            final DateTime date = new DateTime(investment.getCreationDate());
//
//            final Duration timeDifference = new Duration(date, dueDate);
//            final long differenceInDays = timeDifference.getStandardDays();
//            if (differenceInDays < Tax.IncomeTax.INCOME_LESS_THAN_180.getDays()) {
//                amount *= (1 - Tax.IncomeTax.INCOME_LESS_THAN_180.getRate());
//            } else if (differenceInDays > Tax.IncomeTax.INCOME_LESS_THAN_180.getDays() && differenceInDays < Tax.IncomeTax.INCOME_LESS_THAN_360.getDays()) {
//                amount *= (1 - Tax.IncomeTax.INCOME_LESS_THAN_360.getRate());
//            } else if (differenceInDays > Tax.IncomeTax.INCOME_LESS_THAN_360.getDays() && differenceInDays < Tax.IncomeTax.INCOME_LESS_THAN_720.getDays()) {
//                amount *= (1 - Tax.IncomeTax.INCOME_LESS_THAN_720.getRate());
//            } else { // Tax.IncomeTax.INCOME_MORE_THAN_720
//                amount *= (1 - Tax.IncomeTax.INCOME_MORE_THAN_720.getRate());
//            }
//        }
//        Log.d(TAG, "calculateIncomeTax:" + amount + "\n\n");
//        return amount;
//    }

}

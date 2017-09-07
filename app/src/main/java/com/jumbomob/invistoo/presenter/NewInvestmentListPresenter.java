package com.jumbomob.invistoo.presenter;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.business.OperationsManager;
import com.jumbomob.invistoo.model.dto.InvestmentSuggestionDTO;
import com.jumbomob.invistoo.model.entity.AssetStatusEnum;
import com.jumbomob.invistoo.model.entity.AssetTypeEnum;
import com.jumbomob.invistoo.model.entity.Balance;
import com.jumbomob.invistoo.model.entity.Goal;
import com.jumbomob.invistoo.model.entity.Investment;
import com.jumbomob.invistoo.model.persistence.BalanceDAO;
import com.jumbomob.invistoo.model.persistence.GoalDAO;
import com.jumbomob.invistoo.model.persistence.InvestmentDAO;
import com.jumbomob.invistoo.util.InvistooApplication;
import com.jumbomob.invistoo.view.NewInvestmentListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public class NewInvestmentListPresenter implements BasePresenter<NewInvestmentListView> {

    private final static String TAG = NewInvestmentListPresenter.class.getSimpleName();

    private NewInvestmentListView mView;

    @Override
    public void attachView(NewInvestmentListView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public NewInvestmentListPresenter(NewInvestmentListView view) {
        attachView(view);
    }

    public void saveInvestments(List<InvestmentSuggestionDTO> suggestions) {
        List<Investment> investments = new ArrayList<>();
        for (InvestmentSuggestionDTO suggestion : suggestions) {
            Investment investment = new Investment();
            investment.setCreationDate(new Date());
            investment.setPrice(suggestion.getSuggestion().toString());
            investment.setQuantity(1);
            investment.setUpdateDate(new Date());

            final AssetTypeEnum assetTypeEnum = AssetTypeEnum.getById(suggestion.getAssetType());
            investment.setName(assetTypeEnum.getTitle());
            investment.setAssetType(assetTypeEnum);
            investment.setAssetStatus(AssetStatusEnum.BUY);
            investment.setActive(true);
            investment.setUserId(InvistooApplication.getLoggedUser().getUid());

            final OperationsManager operationsManager = new OperationsManager();
            //final double quantity = operationsManager.getQuantityBaseOnAmount(NumericUtil.getValidDouble(investment.getPrice()), investment.getAssetType());
            //TODO fix this flow
            investment.setQuantity(1);
            investments.add(investment);
        }
        InvestmentDAO.getInstance().insert(investments, InvistooApplication.getLoggedUser().getUid());

        mView.showMessage(R.string.msg_save_investment);
        mView.navigateToInvestmentList();
    }

    public void calculateBalance(Double contribution) {
        final GoalDAO goalDAO = GoalDAO.getInstance();

        //1. busca porcentagem de cada meta
        final String userUid = InvistooApplication.getLoggedUser().getUid();
        final List<Goal> goals = goalDAO.findAll(userUid);

        //2. busca quantidade investida em cada assetType
        final List<InvestmentSuggestionDTO> oldInvestmentsData = new ArrayList<>();
        final String userId = InvistooApplication.getLoggedUser().getUid();
        final List<Balance> balances = BalanceDAO.getInstance().getBalances(userId);
        Double totalInvested = 0D;
        for (Balance balance : balances) {
            totalInvested += balance.getTotal();
            InvestmentSuggestionDTO suggestionDTO = new InvestmentSuggestionDTO();
            suggestionDTO.setAssetType(balance.getAsset());
            suggestionDTO.setTotal(balance.getTotal());
            oldInvestmentsData.add(suggestionDTO);
        }

        //3. VAT - total investido atualmente em todos os ativos + contribution
        final Double totalWithContribution = totalInvested + contribution;

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

                //5. Faz a diferença de como deveria e como está o balanceamento para achar o valor da divisão do contribution
                final double newValue = totalWithContribution * (goal.getPercent() / 100);
                final double actualValue = oldInvestmentData.getTotal();
                suggestion.setSuggestion(newValue - actualValue);
                suggestion.setTotal(actualValue);
                balancedInvestments.add(suggestion);
            }
        } else {
            for (Goal goal : goals) {
                final InvestmentSuggestionDTO suggestion = new InvestmentSuggestionDTO();
                suggestion.setAssetType(goal.getAssetTypeEnum());
                //5. Faz o cálculo da diferença de como deveria e como está o balanceamento para achar o valor da divisão do contribution
                suggestion.setSuggestion(totalWithContribution * (goal.getPercent() / 100));
                suggestion.setTotal(new Double(0));
                balancedInvestments.add(suggestion);
            }
        }

        //Validate suggestions - negative suggestion and wrong amount
        Double suggestionsSum = new Double(0);
        for (InvestmentSuggestionDTO suggestion : balancedInvestments) {
            if (suggestion.getTotal() < 0) {
                mView.onNoSuggestionMade();
                return;
            }
            suggestionsSum += suggestion.getTotal();
        }
        if (suggestionsSum > contribution) {
            mView.onNoSuggestionMade();
            return;
        }

        // update screen with the valid suggestions
        mView.configureRecyclerView(balancedInvestments);
    }
}
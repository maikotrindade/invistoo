package com.jumbomob.invistoo.presenter;

import android.util.Log;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.dto.InvestmentSuggestionDTO;
import com.jumbomob.invistoo.model.entity.AssetStatusEnum;
import com.jumbomob.invistoo.model.entity.AssetTypeEnum;
import com.jumbomob.invistoo.model.entity.Goal;
import com.jumbomob.invistoo.model.entity.Investment;
import com.jumbomob.invistoo.model.persistence.GoalDAO;
import com.jumbomob.invistoo.model.persistence.InvestmentDAO;
import com.jumbomob.invistoo.util.NumericUtil;
import com.jumbomob.invistoo.view.BalancedInvestmentListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public class NewInvestmentListPresenter implements BasePresenter<BalancedInvestmentListView> {

    private final static String TAG = NewInvestmentListPresenter.class.getSimpleName();

    private BalancedInvestmentListView mView;

    @Override
    public void attachView(BalancedInvestmentListView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public NewInvestmentListPresenter(BalancedInvestmentListView view) {
        attachView(view);
    }

    public List<InvestmentSuggestionDTO> calculateBalance(Double aporte) {
        Log.d(TAG, "Aporte: " + aporte);

        final GoalDAO goalDAO = GoalDAO.getInstance();
        final InvestmentDAO investmentDAO = InvestmentDAO.getInstance();

        //encontrar porcentagem de cada meta
        final List<Goal> goals = goalDAO.findAll();

        List<InvestmentSuggestionDTO> auxInvestmentList = new ArrayList<>();

        for (Goal goal : goals) {
            InvestmentSuggestionDTO invesTest = new InvestmentSuggestionDTO();
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
                    .getAssetTypeEnum());
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

    public void saveInvestments(List<InvestmentSuggestionDTO> suggestions) {
        InvestmentDAO dao = InvestmentDAO.getInstance();

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
            dao.insert(investment);
        }

        mView.showMessage(R.string.msg_save_investment);
    }
}
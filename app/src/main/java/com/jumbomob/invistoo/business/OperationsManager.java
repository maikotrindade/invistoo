package com.jumbomob.invistoo.business;

import android.util.Log;

import com.jumbomob.invistoo.model.dto.GrossValueDTO;
import com.jumbomob.invistoo.model.dto.InvestmentSuggestionDTO;
import com.jumbomob.invistoo.model.entity.Asset;
import com.jumbomob.invistoo.model.entity.AssetTypeEnum;
import com.jumbomob.invistoo.model.entity.Goal;
import com.jumbomob.invistoo.model.entity.Investment;
import com.jumbomob.invistoo.model.entity.Tax;
import com.jumbomob.invistoo.model.network.AssetInterface;
import com.jumbomob.invistoo.model.network.BaseNetworkConfig;
import com.jumbomob.invistoo.model.persistence.AssetDAO;
import com.jumbomob.invistoo.model.persistence.GoalDAO;
import com.jumbomob.invistoo.model.persistence.InvestmentDAO;
import com.jumbomob.invistoo.util.ConstantsUtil;
import com.jumbomob.invistoo.util.DateUtil;
import com.jumbomob.invistoo.util.InvistooApplication;
import com.jumbomob.invistoo.util.NumericUtil;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Call;

import static com.jumbomob.invistoo.util.NumericUtil.getValidDouble;

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
                sum += calculateIncomeTax(investment);
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
            Double taxSum = 0D;
            for (Investment investment : byAssetType) {
                taxSum += calculateIncomeTax(investment);
            }

            double balancedValue = (vat * (goal.getPercent() / 100) - taxSum);
            suggestion.setSuggestion((long) balancedValue);
            suggestion.setTotal(taxSum);
            balancedInvestments.add(suggestion);

            AssetTypeEnum assetTypeEnum = AssetTypeEnum.getById(goal.getAssetTypeEnum());
            Log.d(TAG, "Valor balanceado: " + balancedValue +
                    " para o AssetEnum: " + assetTypeEnum.getTitle());
        }

        return balancedInvestments;
    }

    private double calculateIncomeTax(Investment investment) {
        final AssetDAO assetDAO = AssetDAO.getInstance();
        final AssetTypeEnum assetTypeEnum = AssetTypeEnum.getById(investment.getAssetType());
        final Asset asset = assetDAO.findAssetLastById(assetTypeEnum.getId());
        double amount = (getValidDouble(investment.getPrice()));// * investment.getQuantity());
        final Date assetDueDate = DateUtil.stringToDate(asset.getDueDate(), DateUtil.SIMPLE_DATE_FORMAT);
        final DateTime dueDate = new DateTime(assetDueDate);
        final DateTime date = new DateTime(investment.getCreationDate());

        final Duration timeDifference = new Duration(date, dueDate);
        final long differenceInDays = timeDifference.getStandardDays();
        if (differenceInDays < Tax.IncomeTax.INCOME_LESS_THAN_180.getDays()) {
            amount *= (1 - Tax.IncomeTax.INCOME_LESS_THAN_180.getRate());
        } else if (differenceInDays > Tax.IncomeTax.INCOME_LESS_THAN_180.getDays() && differenceInDays < Tax.IncomeTax.INCOME_LESS_THAN_360.getDays()) {
            amount *= (1 - Tax.IncomeTax.INCOME_LESS_THAN_360.getRate());
        } else if (differenceInDays > Tax.IncomeTax.INCOME_LESS_THAN_360.getDays() && differenceInDays < Tax.IncomeTax.INCOME_LESS_THAN_720.getDays()) {
            amount *= (1 - Tax.IncomeTax.INCOME_LESS_THAN_720.getRate());
        } else { // Tax.IncomeTax.INCOME_MORE_THAN_720
            amount *= (1 - Tax.IncomeTax.INCOME_MORE_THAN_720.getRate());
        }

        Log.d(TAG, "calculateIncomeTax:" + amount + "\n\n");
        return amount;
    }

    public List<InvestmentSuggestionDTO> calculateBalance(Double contribution, List<GrossValueDTO> grossValues) {
        Log.d(TAG, "Aporte: " + contribution);

        final GoalDAO goalDAO = GoalDAO.getInstance();
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
                    break;
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

    public static List<InvestmentSuggestionDTO> calculateBalanceWithTaxes(Double aporte) {
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
                sum += (getValidDouble(investment.getPrice())
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
                sum += (getValidDouble(investment.getPrice())
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

    public double getQuantityBaseOnAmount(double amount, long assetId) {
        final AssetDAO assetDAO = AssetDAO.getInstance();
        final Asset asset = assetDAO.findAssetLastById(assetId);
        Double sellPrice;
        if (asset != null) {
            sellPrice = NumericUtil.getValidDouble(asset.getSellPrice());
        } else {
            //se o asset nunca foi baixado, tenta baixar e completar o flow
            sellPrice = getAssetAndQuantity(assetId);
        }
        //TODO trata nullPointerException
        return amount / sellPrice;
    }

    public Double getAssetAndQuantity(final long assetId) {
        final AssetInterface service = BaseNetworkConfig.createService(AssetInterface.class,
                ConstantsUtil.BASE_URL);
        final Call<List<Asset>> call = service.getAssets();
        try {
            List<Asset> assets = call.execute().body();
            for (Asset asset : assets) {
                if (asset.getIndex() == assetId) {
                    return getValidDouble(asset.getSellPrice());
                }
            }
        } catch (IOException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return null;
    }

}

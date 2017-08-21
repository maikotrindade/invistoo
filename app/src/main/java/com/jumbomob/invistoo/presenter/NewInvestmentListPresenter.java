package com.jumbomob.invistoo.presenter;

import android.util.Log;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.business.OperationsManager;
import com.jumbomob.invistoo.model.dto.InvestmentSuggestionDTO;
import com.jumbomob.invistoo.model.entity.Asset;
import com.jumbomob.invistoo.model.entity.AssetStatusEnum;
import com.jumbomob.invistoo.model.entity.AssetTypeEnum;
import com.jumbomob.invistoo.model.entity.Investment;
import com.jumbomob.invistoo.model.network.AssetInterface;
import com.jumbomob.invistoo.model.network.BaseNetworkConfig;
import com.jumbomob.invistoo.model.persistence.AssetDAO;
import com.jumbomob.invistoo.model.persistence.InvestmentDAO;
import com.jumbomob.invistoo.util.ConstantsUtil;
import com.jumbomob.invistoo.util.InvistooApplication;
import com.jumbomob.invistoo.view.NewInvestmentListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;

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

    public List<InvestmentSuggestionDTO> calculateBalance(Double contribution) {
        final OperationsManager operationsManager = new OperationsManager();
        return operationsManager.calculateBalance(contribution);
    }

    public boolean areDownloadedAssets() {
        final AssetDAO assetDAO = AssetDAO.getInstance();
        return !assetDAO.isEmpty();
    }

    public void downloadAssets(final Double contribution) {
        mView.showProgressDialog(R.string.loading);
        final AssetInterface service = BaseNetworkConfig.createService(AssetInterface.class,
                ConstantsUtil.BASE_URL);

        final Call<List<Asset>> call = service.getAssets();
        call.enqueue(new Callback<List<Asset>>() {
            @Override
            public void onResponse(retrofit.Response<List<Asset>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    final AssetDAO assetDAO = AssetDAO.getInstance();
                    final List<Asset> assetsResult = response.body();
                    for (Asset asset : assetsResult) {
                        assetDAO.insert(asset);
                    }
                    final List<InvestmentSuggestionDTO> suggestions = calculateBalance(contribution);
                    mView.configureRecyclerView(suggestions);
                    mView.hideProgressDialog();
                } else {
                    mView.onDownloadAssetsError();
                    Log.e(TAG, response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                mView.onDownloadAssetsError();
                Log.e(TAG, throwable.getMessage());
            }
        });
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
}
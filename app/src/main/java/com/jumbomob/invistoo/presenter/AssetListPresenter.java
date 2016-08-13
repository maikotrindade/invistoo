package com.jumbomob.invistoo.presenter;

import android.util.Log;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Asset;
import com.jumbomob.invistoo.model.network.AssetInterface;
import com.jumbomob.invistoo.model.network.BaseNetworkConfig;
import com.jumbomob.invistoo.model.persistence.AssetDAO;
import com.jumbomob.invistoo.ui.adapter.AssetListAdapter;
import com.jumbomob.invistoo.view.AssetListView;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public class AssetListPresenter implements BasePresenter<AssetListView> {

    private static final String TAG = AssetListPresenter.class.getSimpleName();

    private AssetListView mView;

    public AssetListPresenter(AssetListView view) {
        attachView(view);
    }

    @Override
    public void attachView(AssetListView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void downloadAssets() {

        mView.showProgressDialog(R.string.loading_assets);

        final AssetInterface service = BaseNetworkConfig.createService(AssetInterface.class,
                BaseNetworkConfig.BASE_URL);

        final Call<List<Asset>> call = service.getAssets();
        call.enqueue(new Callback<List<Asset>>() {
            @Override
            public void onResponse(retrofit.Response<List<Asset>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    final AssetDAO assetDAO = AssetDAO.getInstance();
                    final List<Asset> assetsResult = response.body();
                    mView.updateAssetList(assetsResult);
                    for (Asset asset : assetsResult) {
                        assetDAO.insert(asset);
                    }
                    mView.onDownloadSuccess();
                } else {
                    mView.onDownloadError();
                    Log.e(TAG, response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mView.onDownloadError();
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
    }

    public List<Asset> getAssets() {
        final AssetDAO assetDAO = AssetDAO.getInstance();
        return assetDAO.findLast();
    }

    public void searchByText(final AssetListAdapter adapter, final String name) {
        final List<Asset> filteredAssets = adapter.filterByName(name);
        mView.updateAssetList(filteredAssets);
    }

}
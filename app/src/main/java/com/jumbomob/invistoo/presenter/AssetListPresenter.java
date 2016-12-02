package com.jumbomob.invistoo.presenter;

import android.util.Log;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Asset;
import com.jumbomob.invistoo.model.network.AssetInterface;
import com.jumbomob.invistoo.model.network.BaseNetworkConfig;
import com.jumbomob.invistoo.model.persistence.AssetDAO;
import com.jumbomob.invistoo.ui.adapter.AssetListAdapter;
import com.jumbomob.invistoo.util.ConstantsUtil;
import com.jumbomob.invistoo.util.DateUtil;
import com.jumbomob.invistoo.view.AssetListView;

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
                ConstantsUtil.BASE_URL);

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

    public void getAssetsFromDatabase() {
        final AssetDAO assetDAO = AssetDAO.getInstance();
        final List<Asset> lastFromDatabase = assetDAO.findLastFromDatabase();
        if (!lastFromDatabase.isEmpty()) {
            mView.updateAssetList(lastFromDatabase);
            mView.setLastUpdateTitle();
        }
    }

    public void searchByText(final AssetListAdapter adapter, final String name) {
        final List<Asset> assets = adapter.getAssets();
        if (assets.isEmpty()) {
            final AssetDAO assetDAO = AssetDAO.getInstance();
            final List<Asset> filteredAssets = filterByName(name, assetDAO.findLastFromDatabase());
            mView.updateAssetList(filteredAssets);
        } else {
            final List<Asset> filteredAssets = filterByName(name, adapter.getAssets());
            mView.updateAssetList(filteredAssets);
        }
    }

    public String getLastUpdate() {
        final AssetDAO assetDAO = AssetDAO.getInstance();
        final Date lastUpdate = assetDAO.findLastUpdate();
        if (lastUpdate != null) {
            return DateUtil.formatDateUX(lastUpdate);
        } else {
            return "";
        }
    }

    public List<Asset> filterByName(final String name, final List<Asset> assets) {
        List<Asset> filteredAssets = new ArrayList<>();
        final String filter = name.toLowerCase();
        Log.e("Adapter", "Texto para filtro: " + name);
        Log.e("Adapter", "O adapter tinha #: " + assets.size());
        for (Asset asset : assets) {
            final String assetName = asset.getName().toString().toLowerCase();
            if ((assetName).contains(filter)) {
                filteredAssets.add(asset);
            }
        }
        Log.e("Adapter", "O filtro tem #: " + filteredAssets.size());
        return filteredAssets;
    }

}
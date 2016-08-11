package com.jumbomob.invistoo.view;

import com.jumbomob.invistoo.model.entity.Asset;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public interface AssetListView {

    void onDownloadError();

    void onDownloadSuccess();

    void updateAssetList(List<Asset> assets);

    void showProgressDialog(final int resourceId);

}

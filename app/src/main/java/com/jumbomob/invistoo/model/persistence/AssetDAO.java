package com.jumbomob.invistoo.model.persistence;

import com.jumbomob.invistoo.model.entity.Asset;
import com.jumbomob.invistoo.util.InvistooApplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Data Access Object for {@link Asset} domain
 *
 * @author maiko.trindade
 * @since 09/02/2016
 */
public class AssetDAO {

    private static AssetDAO sDaoInstance;

    public static synchronized AssetDAO getInstance() {
        if (sDaoInstance == null) {
            sDaoInstance = new AssetDAO();
        }
        return sDaoInstance;
    }

    public void insert(final Asset asset) {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(asset);
        realm.commitTransaction();
    }

    public List<Asset> findLastFromDatabase() {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        RealmQuery<Asset> query = realm.where(Asset.class);
        RealmResults<Asset> allAssets = query.findAll().sort("lastUpdate", Sort.DESCENDING);
        List<Asset> assets = new ArrayList<>();
        for (int index = 0; index < allAssets.size(); index++) {
            assets.add(allAssets.get(index));
        }

        return assets;
    }

//    public List<Asset> findHistoric(final String assetName) {
//
//        final List<Asset> last = findLastFromDatabase();
//        Log.d("ASSETDAO -> LASTES", last.get(0).toString());
//
//        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
//        RealmQuery<Asset> query = realm.where(Asset.class);
//
//        query.equalTo("name", "John");
//        RealmResults<Asset> assets = query.findAll();
//        return assets;
//    }

    private Asset findLastAssetAdded() {
        Asset lastAsset = null;
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        RealmQuery<Asset> query = realm.where(Asset.class);
        RealmResults<Asset> assets = query.findAll();
        if (!assets.isEmpty()) {
            lastAsset = assets.get(assets.size() - 1);
        }

        return lastAsset;
    }

    //TODO
    private Asset findAssetById(final Long assetId) {
        Asset lastAsset = null;
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        RealmQuery<Asset> query = realm.where(Asset.class);
        RealmResults<Asset> assets = query.findAll();
        if (!assets.isEmpty()) {
            for (Asset asset : assets) {

            }
            lastAsset = assets.get(assets.size() - 1);
        }

        return lastAsset;
    }

    public Date findLastUpdate() {
        Date lastUpdate = null;
        final Asset lastAssetAdded = findLastAssetAdded();
        if (lastAssetAdded != null) {
            lastUpdate = lastAssetAdded.getLastUpdate();
        }
        return lastUpdate;
    }

}

package com.jumbomob.invistoo.model.persistence;

import android.util.Log;

import com.jumbomob.invistoo.model.entity.Asset;
import com.jumbomob.invistoo.util.InvistooApplication;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Data Access Object for {@link Asset} domain
 *
 * @author maiko.trindade
 * @since 09/02/2016
 */
public class AssetDAO{

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

    public Asset findLastByIndex(final int index) {
//        String query = "SELECT " + columns +
//                " FROM " + Tables.ASSET +
//                " WHERE " + Tables.ASSET + "." + AssetTable.COLUMN_INDEX + " = ? " +
//                " ORDER BY " + AssetTable.COLUMN_LAST_UPDATE + " ASC " +
//                " LIMIT 1 ";
        return new Asset();
    }

    public List<Asset> findLast() {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        RealmQuery<Asset> query = realm.where(Asset.class);
        RealmResults<Asset> assets = query.findAll();
        return assets;
    }

    public List<Asset> findHistoric(final String assetName) {

        final List<Asset> last = findLast();
        Log.d("ASSETDAO -> LASTES", last.get(0).toString());

        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        RealmQuery<Asset> query = realm.where(Asset.class);

        query.equalTo("name", "John");
        RealmResults<Asset> assets = query.findAll();
        return assets;
    }

}

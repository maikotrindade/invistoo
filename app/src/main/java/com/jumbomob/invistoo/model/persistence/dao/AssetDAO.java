package com.jumbomob.invistoo.model.persistence.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.jumbomob.invistoo.model.entity.Asset;
import com.jumbomob.invistoo.model.persistence.DatabaseContract.AssetTable;
import com.jumbomob.invistoo.model.persistence.DatabaseContract.Tables;
import com.jumbomob.invistoo.util.DatabaseUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for {@link Asset} domain
 *
 * @author maiko.trindade
 * @since 09/02/2016
 */
public class AssetDAO extends BaseDAO {

    public long insert(final Asset asset) {
        long assetId = 0;

        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(AssetTable.COLUMN_NAME, asset.getName());
            values.put(AssetTable.COLUMN_BUY_PRICE, asset.getBuyPrice());
            values.put(AssetTable.COLUMN_BUY_TAX, asset.getBuyTax());
            values.put(AssetTable.COLUMN_DUE_DATE, asset.getDueDate());
            values.put(AssetTable.COLUMN_SELL_PRICE, asset.getSellPrice());
            values.put(AssetTable.COLUMN_SELL_TAX, asset.getSellTax());
            values.put(AssetTable.COLUMN_LAST_UPDATE, asset.getLastUpdate());
            assetId = database.insertOrThrow(Tables.ASSET, null, values);

            database.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            database.endTransaction();
        }
        return assetId;
    }

    public List<Asset> findAll() {
        final String columns = DatabaseUtil.joinStrings(AssetTable.projection);
        String query =
                "SELECT " + columns + " FROM " + Tables.ASSET;

        List<Asset> assets = new ArrayList<>();
        final Cursor cursor = database.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                final Asset asset = cursorToAsset(cursor);
                assets.add(asset);
                cursor.moveToNext();
            }
        }
        cursor.close();

        return assets;
    }

    public Asset findById(final String id) {

        final String columns = DatabaseUtil.joinStrings(AssetTable.projection);
        String query = "SELECT " + columns +
                " FROM " + Tables.ASSET +
                " WHERE " + Tables.ASSET + "." + AssetTable.COLUMN_ID + " = ? ";

        Asset asset = null;
        final String[] whereClauses = {id};
        final Cursor cursor = database.rawQuery(query, whereClauses);
        if (cursor != null && cursor.moveToFirst()) {
            asset = cursorToAsset(cursor);
            cursor.close();
        }
        return asset;
    }

    private Asset cursorToAsset(Cursor cursor) {
        Asset asset = new Asset();
        asset.setId(cursor.getString(0));
        asset.setName(cursor.getString(1));
        asset.setDueDate(cursor.getString(2));
        asset.setBuyTax(cursor.getString(3));
        asset.setSellTax(cursor.getString(4));
        asset.setBuyPrice(cursor.getString(5));
        asset.setSellPrice(cursor.getString(6));
        asset.setLastUpdate(cursor.getString(7));
        return asset;
    }

}

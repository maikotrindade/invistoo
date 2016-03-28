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

    private static AssetDAO sDaoInstance;

    public static synchronized AssetDAO getInstance() {
        if (sDaoInstance == null) {
            sDaoInstance = new AssetDAO();
        }
        return sDaoInstance;
    }

    public long insert(final Asset asset) {
        long assetId = 0;

        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(AssetTable.COLUMN_TITLE, asset.getTitle());
            values.put(AssetTable.COLUMN_NAME, asset.getName());
            values.put(AssetTable.COLUMN_BUY_PRICE, asset.getBuyPrice());
            values.put(AssetTable.COLUMN_INDEXER, asset.getIndexer());
            values.put(AssetTable.COLUMN_DUE_DATE, asset.getDueDate());
            values.put(AssetTable.COLUMN_SELL_PRICE, asset.getSellPrice());
            values.put(AssetTable.COLUMN_LAST_30_DAYS_PROFITS, asset.getLast30daysProfitability());
            values.put(AssetTable.COLUMN_LAST_MONTH_PROFITS, asset.getLastMonthProfitability());
            values.put(AssetTable.COLUMN_YEAR_PROFITS, asset.getYearProfitability());
            values.put(AssetTable.COLUMN_LAST_YEAR_PROFITS, asset.getLastYearProfitability());
            values.put(AssetTable.COLUMN_LAST_UPDATE, asset.getLastUpdate());
            values.put(AssetTable.COLUMN_INDEX, asset.getIndex());
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

    public Asset findLastByName(final String name) {

        final String columns = DatabaseUtil.joinStrings(AssetTable.projection);
        String query = "SELECT " + columns +
                " FROM " + Tables.ASSET +
                " WHERE " + Tables.ASSET + "." + AssetTable.COLUMN_NAME + " = ? " +
                " ORDER BY " + AssetTable.COLUMN_LAST_UPDATE + " ASC " +
                " LIMIT 1 ";

        Asset asset = null;
        final String[] whereClauses = {name};
        final Cursor cursor = database.rawQuery(query, whereClauses);
        if (cursor != null && cursor.moveToFirst()) {
            asset = cursorToAsset(cursor);
            cursor.close();
        }
        return asset;
    }

    public List<Asset> findLast() {
        final String columns = DatabaseUtil.joinStrings(AssetTable.projection);

        String query = " SELECT " + columns + " FROM " + Tables.ASSET +
                " ORDER BY " + AssetTable.COLUMN_LAST_UPDATE +
                " LIMIT 25";

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

    public List<String> findNames() {
        String query =
                " SELECT DISTINCT " + AssetTable.COLUMN_NAME + " FROM " + Tables.ASSET;
        List<String> assetNames = new ArrayList<>();
        final Cursor cursor = database.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                assetNames.add(cursor.getString(0));
                cursor.moveToNext();
            }
        }
        cursor.close();

        return assetNames;
    }

    private Asset cursorToAsset(Cursor cursor) {

        Asset asset = new Asset();
        asset.setId(cursor.getString(0));
        asset.setTitle(cursor.getString(1));
        asset.setName(cursor.getString(2));
        asset.setDueDate(cursor.getString(3));
        asset.setIndexer(cursor.getString(4));
        asset.setBuyPrice(cursor.getString(5));
        asset.setSellPrice(cursor.getString(6));
        asset.setLast30daysProfitability(cursor.getString(7));
        asset.setLastMonthProfitability(cursor.getString(8));
        asset.setYearProfitability(cursor.getString(9));
        asset.setLastYearProfitability(cursor.getString(10));
        asset.setLastUpdate(cursor.getString(11));
        asset.setIndex(cursor.getString(12));

        return asset;
    }

}

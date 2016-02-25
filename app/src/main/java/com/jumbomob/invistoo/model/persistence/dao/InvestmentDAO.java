package com.jumbomob.invistoo.model.persistence.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.jumbomob.invistoo.model.entity.Investment;
import com.jumbomob.invistoo.model.persistence.DatabaseContract.InvestmentTable;
import com.jumbomob.invistoo.model.persistence.DatabaseContract.Tables;
import com.jumbomob.invistoo.util.DatabaseUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

;

/**
 * @author maiko.trindade
 * @since 14/02/2016
 */
public class InvestmentDAO extends BaseDAO {

    private static InvestmentDAO sDaoInstance;

    public static synchronized InvestmentDAO getInstance() {
        if (sDaoInstance == null) {
            sDaoInstance = new InvestmentDAO();
        }
        return sDaoInstance;
    }

    public long insert(final Investment investment) {
        long assetId = 0;
        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(InvestmentTable.COLUMN_NAME, investment.getName());
            values.put(InvestmentTable.COLUMN_PRICE, investment.getPrice().toPlainString());
            values.put(InvestmentTable.COLUMN_QUANTITY, investment.getQuantity());
            values.put(InvestmentTable.COLUMN_CREATION_DATE, investment.getCreationDate());
            values.put(InvestmentTable.COLUMN_UPDATE_DATE, investment.getUpdateDate());
            values.put(InvestmentTable.COLUMN_REMOVED_DATE, investment.getRemovedDate());

            assetId = database.insertOrThrow(Tables.INVESTMENT, null, values);
            database.setTransactionSuccessful();

        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            database.endTransaction();
        }
        return assetId;
    }

    public List<Investment> findAll() {
        final String columns = DatabaseUtil.joinStrings(InvestmentTable.projection);
        String query =
                "SELECT " + columns + " FROM " + Tables.INVESTMENT;

        List<Investment> investments = new ArrayList<>();
        final Cursor cursor = database.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                final Investment investment = cursorToInvestiment(cursor);
                investments.add(investment);
                cursor.moveToNext();
            }
        }
        cursor.close();

        return investments;
    }

    private Investment cursorToInvestiment(Cursor cursor) {
        Investment investment = new Investment();
        investment.setId(cursor.getLong(0));
        investment.setName(cursor.getString(1));
        investment.setQuantity(cursor.getInt(2));
        investment.setPrice(new BigDecimal(cursor.getString(3)));
        investment.setCreationDate(cursor.getString(4));
        investment.setUpdateDate(cursor.getString(5));
        investment.setRemovedDate(cursor.getString(6));
        return investment;
    }
}

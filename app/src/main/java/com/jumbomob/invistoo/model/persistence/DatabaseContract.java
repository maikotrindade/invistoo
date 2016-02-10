package com.jumbomob.invistoo.model.persistence;

/**
 * Contract class for e-crew database and its dependencies.
 *
 * @author maiko.trindade
 * @since 22/12/2015
 */
public class DatabaseContract {

    public interface Tables {
        String ASSET = "asset_tb";
    }

    public interface AssetTable {
        String COLUMN_ID = "asset_id";
        String COLUMN_NAME = "asset_name";
        String COLUMN_DUE_DATE = "asset_due_date";
        String COLUMN_BUY_TAX = "asset_buy_tax";
        String COLUMN_SELL_TAX = "asset_sell_tax";
        String COLUMN_BUY_PRICE = "asset_buy_price";
        String COLUMN_SELL_PRICE = "asset_sell_price";
        String COLUMN_LAST_UPDATE = "asset_last_update";

        String[] projection = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_DUE_DATE,
                COLUMN_BUY_TAX,
                COLUMN_SELL_TAX,
                COLUMN_BUY_PRICE,
                COLUMN_SELL_PRICE,
                COLUMN_LAST_UPDATE};
    }
}

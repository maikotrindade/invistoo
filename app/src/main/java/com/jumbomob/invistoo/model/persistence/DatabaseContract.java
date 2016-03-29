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
        String INVESTMENT = "investment_tb";
        String QUESTION = "question_tb";
        String USER = "user_tb";
    }

    public interface AssetTable {
        String COLUMN_ID = "asset_id";
        String COLUMN_TITLE = "asset_title";
        String COLUMN_NAME = "asset_name";
        String COLUMN_DUE_DATE = "asset_due_date";
        String COLUMN_INDEXER = "asset_indexer";
        String COLUMN_BUY_PRICE = "asset_buy_price";
        String COLUMN_SELL_PRICE = "asset_sell_price";
        String COLUMN_LAST_30_DAYS_PROFITS = "asset_buy_last30daysProfits";
        String COLUMN_LAST_MONTH_PROFITS = "asset_sell_lastMonthProfits";
        String COLUMN_YEAR_PROFITS = "asset_buy_yearProfits";
        String COLUMN_LAST_YEAR_PROFITS = "asset_sell_lastYearProfits";
        String COLUMN_LAST_UPDATE = "asset_last_update";
        String COLUMN_INDEX = "asset_index";

        String[] projection = {
                COLUMN_ID,
                COLUMN_TITLE,
                COLUMN_NAME,
                COLUMN_DUE_DATE,
                COLUMN_INDEXER,
                COLUMN_BUY_PRICE,
                COLUMN_SELL_PRICE,
                COLUMN_LAST_30_DAYS_PROFITS,
                COLUMN_LAST_MONTH_PROFITS,
                COLUMN_YEAR_PROFITS,
                COLUMN_LAST_YEAR_PROFITS,
                COLUMN_LAST_UPDATE,
                COLUMN_INDEX};
    }

    public interface InvestmentTable {
        String COLUMN_ID = "investment_id";
        String COLUMN_NAME = "investment_name";
        String COLUMN_QUANTITY = "investment_quantity";
        String COLUMN_PRICE = "investment_price";
        String COLUMN_CREATION_DATE = "investment_creation_date";
        String COLUMN_UPDATE_DATE = "investment_update_date";
        String COLUMN_REMOVED_DATE = "investment_removed_date";

        String[] projection = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_QUANTITY,
                COLUMN_PRICE,
                COLUMN_CREATION_DATE,
                COLUMN_UPDATE_DATE,
                COLUMN_REMOVED_DATE};
    }

    public interface QuestionTable {
        String COLUMN_ID = "question_id";
        String COLUMN_QUESTION = "question_question";
        String COLUMN_ANSWER = "question_answer";
        String COLUMN_GROUP = "question_group";

        String[] projection = {
                COLUMN_ID,
                COLUMN_QUESTION,
                COLUMN_ANSWER,
                COLUMN_GROUP};
    }
}

package com.jumbomob.invistoo.model.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jumbomob.invistoo.util.InvistooApplication;
import com.jumbomob.invistoo.model.persistence.DatabaseContract.*;

/**
 * Helper class for managing {@link SQLiteDatabase}.
 *
 * @author maiko.trindade
 * @since 09/02/2015
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static DatabaseHelper sInstance;
    private Context mContext;

    private static final String DATABASE_NAME = "invistoo.db";
    private static final int DATABASE_VERSION = 1;

    public static synchronized DatabaseHelper getInstance() {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(InvistooApplication.getInstance());
        }
        return sInstance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    String CREATE_ASSET_TB = "CREATE TABLE " + Tables.ASSET + "("
            + AssetTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + AssetTable.COLUMN_TITLE + " TEXT NOT NULL COLLATE NOCASE,"
            + AssetTable.COLUMN_NAME + " TEXT NOT NULL COLLATE NOCASE,"
            + AssetTable.COLUMN_DUE_DATE + " TEXT NOT NULL,"
            + AssetTable.COLUMN_INDEXER + " TEXT NOT NULL,"
            + AssetTable.COLUMN_BUY_PRICE + " TEXT NOT NULL,"
            + AssetTable.COLUMN_SELL_PRICE + " TEXT NOT NULL,"
            + AssetTable.COLUMN_LAST_30_DAYS_PROFITS + " TEXT NOT NULL,"
            + AssetTable.COLUMN_LAST_MONTH_PROFITS + " TEXT NOT NULL,"
            + AssetTable.COLUMN_YEAR_PROFITS + " TEXT NOT NULL,"
            + AssetTable.COLUMN_LAST_YEAR_PROFITS + " TEXT NOT NULL,"
            + AssetTable.COLUMN_LAST_UPDATE + " TEXT NULL,"
            + AssetTable.COLUMN_INDEX + " TEXT NULL)";

    String CREATE_INVESTMENT_TB = "CREATE TABLE " + Tables.INVESTMENT + "("
            + InvestmentTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + InvestmentTable.COLUMN_NAME + " TEXT NOT NULL COLLATE NOCASE,"
            + InvestmentTable.COLUMN_QUANTITY + " INTEGER NOT NULL,"
            + InvestmentTable.COLUMN_PRICE + " TEXT NOT NULL,"
            + InvestmentTable.COLUMN_CREATION_DATE + " TEXT NOT NULL,"
            + InvestmentTable.COLUMN_UPDATE_DATE + " TEXT NOT NULL,"
            + InvestmentTable.COLUMN_REMOVED_DATE + " TEXT NULL)";

    String CREATE_QUESTION_TB = "CREATE TABLE " + Tables.QUESTION + "("
            + QuestionTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + QuestionTable.COLUMN_QUESTION + " TEXT NOT NULL,"
            + QuestionTable.COLUMN_ANSWER + " TEXT NOT NULL,"
            + QuestionTable.COLUMN_GROUP + " TEXT NOT NULL)";

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_ASSET_TB);
        database.execSQL(CREATE_INVESTMENT_TB);
        database.execSQL(CREATE_QUESTION_TB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }
}
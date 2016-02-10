package com.jumbomob.invistoo.model.persistence.dao;

import android.database.sqlite.SQLiteDatabase;

import com.vistajet.aea.model.persistence.DatabaseHelper;
import com.vistajet.aea.util.AeaApplication;

/**
 * Base Data Access Object responsible for separate low level data accessing API or operations
 * from high level business services.
 *
 * @author maiko.trindade
 * @since 04/01/2016
 */
public class BaseDAO {

    final static String TAG = BaseDAO.class.getSimpleName();
    protected SQLiteDatabase database;

    public BaseDAO() {
        DatabaseHelper databaseHelper = AeaApplication.getInstance().getDatabaseHelper();
        if (database == null) {
            database = databaseHelper.getWritableDatabase();
        }
    }

}

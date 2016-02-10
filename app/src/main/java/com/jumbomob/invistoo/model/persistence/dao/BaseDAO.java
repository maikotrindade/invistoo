package com.jumbomob.invistoo.model.persistence.dao;

import android.database.sqlite.SQLiteDatabase;

import com.jumbomob.invistoo.model.persistence.DatabaseHelper;
import com.jumbomob.invistoo.util.InvistooApplication;

/**
 * Base Data Access Object responsible for separate low level data accessing API or operations
 * from high level business services.
 *
 * @author maiko.trindade
 * @since 09/02/2016
 */
public class BaseDAO {

    final static String TAG = BaseDAO.class.getSimpleName();
    protected SQLiteDatabase database;

    public BaseDAO() {
        DatabaseHelper databaseHelper = InvistooApplication.getInstance().getDatabaseHelper();
        if (database == null) {
            database = databaseHelper.getWritableDatabase();
        }
    }
}

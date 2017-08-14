package com.jumbomob.invistoo.model.persistence;

import android.util.Log;

import com.jumbomob.invistoo.model.entity.Asset;
import com.jumbomob.invistoo.model.entity.Balance;
import com.jumbomob.invistoo.model.entity.Investment;
import com.jumbomob.invistoo.util.InvistooApplication;
import com.jumbomob.invistoo.util.NumericUtil;

import org.joda.time.DateTime;

import java.util.List;

import io.realm.Realm;

/**
 * Data Access Object for {@link Asset} domain
 *
 * @author maiko.trindade
 * @since 14/08/2017
 */
public class BalanceDAO {

    private final String TAG = BalanceDAO.class.getSimpleName();

    private static BalanceDAO sDaoInstance;

    public static synchronized BalanceDAO getInstance() {
        if (sDaoInstance == null) {
            sDaoInstance = new BalanceDAO();
        }
        return sDaoInstance;
    }

    public void insert(final Balance balance) {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(balance);
        realm.commitTransaction();
    }

    public void insertOrUpdate(final List<Investment> investments, final String userId) {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        realm.beginTransaction();
        for (Investment investment : investments) {
            insertOrUpdate(realm, investment, userId);
        }
        realm.commitTransaction();
    }

    public void insertOrUpdate(final Investment investment, final String userId) {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        insertOrUpdate(realm, investment, userId);
    }

    public void insertOrUpdate(final Realm realm, final Investment investment, final String userId) {
        final Balance balanceFromDB = realm
                .where(Balance.class)
                .equalTo("asset", investment.getAssetType())
                .findFirst();
        Balance balance = new Balance();
        balance.setAsset(investment.getAssetType());
        balance.setUpdateDate(DateTime.now().toString());
        balance.setUserId(userId);
        if (balanceFromDB != null) {
            Log.d(TAG, "Saldo não existe -> insert balance...");
            balance.setId(balanceFromDB.getId());
            balance.setTotal(balance.getTotal() + NumericUtil.getValidDouble(investment.getPrice()));
        } else {
            Log.d(TAG, "Saldo existe! -> update balance...");
            balance.setId(RealmAutoIncrement.getInstance().getNextIdFromModel(Balance.class));
            balance.setTotal(NumericUtil.getValidDouble(investment.getPrice()));
        }
        realm.insertOrUpdate(balance);
    }

    public List<Balance> getBalance(final String userId) {
        return InvistooApplication.getInstance().getDatabaseInstance()
                .where(Balance.class)
                .equalTo("userId", userId)
                .findAll();
    }

}

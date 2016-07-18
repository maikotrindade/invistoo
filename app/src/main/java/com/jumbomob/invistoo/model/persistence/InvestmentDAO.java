package com.jumbomob.invistoo.model.persistence;

import com.jumbomob.invistoo.model.entity.Investment;
import com.jumbomob.invistoo.util.InvistooApplication;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

;

/**
 * @author maiko.trindade
 * @since 14/02/2016
 */
public class InvestmentDAO {

    private static InvestmentDAO sDaoInstance;

    public static synchronized InvestmentDAO getInstance() {
        if (sDaoInstance == null) {
            sDaoInstance = new InvestmentDAO();
        }
        return sDaoInstance;
    }

    public void insert(final Investment investment) {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(investment);
        realm.commitTransaction();
    }

    public List<Investment> findAll() {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        RealmQuery<Investment> query = realm.where(Investment.class);
        RealmResults<Investment> investments = query.findAll();
        return investments;
    }
}

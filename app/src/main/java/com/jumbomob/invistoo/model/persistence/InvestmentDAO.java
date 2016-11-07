package com.jumbomob.invistoo.model.persistence;

import com.jumbomob.invistoo.model.entity.AssetStatusEnum;
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
        realm.insert(investment);
        realm.commitTransaction();
    }

    /**
     * Insert or update a list of {@link Investment}, in case there is a Investment without id,
     * the Investment will be inserted otherwise will be updated
     *
     * @param investments a list of {@link Investment} to be inserted of updated
     */
    public void insertOrUpdate(final List<Investment> investments) {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        realm.beginTransaction();
        for (Investment investment : investments) {
            if (investment.getId() == null) {
                investment.setId(
                        RealmAutoIncrement.getInstance().getNextIdFromModel(Investment.class));
                realm.insert(investment);
            } else {
                realm.insertOrUpdate(investment);
            }
        }
        realm.commitTransaction();
    }

    public List<Investment> findAll() {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        RealmQuery<Investment> query = realm.where(Investment.class);
        RealmResults<Investment> investments = query.findAll();
        return investments;
    }

    public List<Investment> findByAssetType(Long assetType) {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        return realm.where(Investment.class)
                .equalTo("assetType", assetType)
                .findAll();
    }

    public void updateSold(final Investment investment) {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        realm.beginTransaction();
        investment.setAssetStatus(AssetStatusEnum.SELL);
        realm.insertOrUpdate(investment);
        realm.commitTransaction();
    }
}

package com.jumbomob.invistoo.model.persistence;

import com.jumbomob.invistoo.model.entity.AssetStatusEnum;
import com.jumbomob.invistoo.model.entity.Investment;
import com.jumbomob.invistoo.util.InvistooApplication;
import com.jumbomob.invistoo.util.NumericUtil;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

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

    public void insert(final List<Investment> investments, final String userId) {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        realm.beginTransaction();
        realm.insert(investments);
        realm.commitTransaction();
        BalanceDAO.getInstance().insertOrUpdate(investments, userId);
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

    public List<Investment> findAll(String userId) {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        RealmQuery<Investment> query = realm.where(Investment.class)
                .equalTo("isActive", true)
                .equalTo("userId", userId);
        RealmResults<Investment> investments = query.findAll();
        return investments;
    }

    public List<Investment> findAllOrderedByDate(boolean sortAsc, String userId) {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        RealmQuery<Investment> query = realm.where(Investment.class)
                .equalTo("isActive", true)
                .equalTo("userId", userId);

        if (sortAsc) {
            return query.findAll().sort("creationDate", Sort.ASCENDING);
        } else {
            return query.findAll().sort("creationDate", Sort.DESCENDING);
        }
    }

    public List<Investment> findByAssetType(Long assetType, String userId) {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        return realm.where(Investment.class)
                .equalTo("assetType", assetType)
                .equalTo("assetStatus", AssetStatusEnum.BUY.getId())
                .equalTo("isActive", true)
                .equalTo("userId", userId)
                .findAll();
    }

    public void updateSold(final Investment investment, AssetStatusEnum status) {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        realm.beginTransaction();
        investment.setAssetStatus(status);
        realm.commitTransaction();
        final String userId = InvistooApplication.getLoggedUser().getUid();
        final Double value = NumericUtil.getValidDouble(investment.getPrice());
        BalanceDAO.getInstance().update(investment.getAssetType(), value, status, userId);
    }

    public void updateActive(final Investment investment) {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        realm.beginTransaction();
        investment.setActive(false);
        realm.commitTransaction();
        final String userId = InvistooApplication.getLoggedUser().getUid();
        final Double value = NumericUtil.getValidDouble(investment.getPrice());
        BalanceDAO.getInstance().update(investment.getAssetType(), value, AssetStatusEnum.SELL, userId);
    }

    public List<Investment> findSoldInvestments(String userId) {
        Realm realm = InvistooApplication.getInstance().getDatabaseInstance();
        RealmQuery<Investment> query = realm.where(Investment.class)
                .equalTo("isActive", true)
                .equalTo("assetStatus", AssetStatusEnum.SELL.getId())
                .equalTo("userId", userId);
        RealmResults<Investment> investments = query.findAll();
        return investments;
    }
}

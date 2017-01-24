package com.jumbomob.invistoo.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.jumbomob.invistoo.model.entity.AssetTypeEnum;
import com.jumbomob.invistoo.model.entity.Investment;
import com.jumbomob.invistoo.model.persistence.InvestmentDAO;
import com.jumbomob.invistoo.ui.adapter.InvestmentSectionItem;
import com.jumbomob.invistoo.ui.adapter.InvestmentHeaderItem;
import com.jumbomob.invistoo.ui.adapter.InvestmentListItem;
import com.jumbomob.invistoo.util.NumericUtil;
import com.jumbomob.invistoo.util.SharedPrefsUtil;
import com.jumbomob.invistoo.view.InvestmentListView;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public class InvestmentListPresenter implements BasePresenter<InvestmentListView> {

    private InvestmentListView mView;

    @Override
    public void attachView(InvestmentListView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public InvestmentListPresenter(InvestmentListView view) {
        attachView(view);
    }

    public boolean isContributionValid(String contribution) {
        return !TextUtils.isEmpty(contribution) && NumericUtil.isValidDouble(contribution);
    }

    public List<Investment> findInvestments() {
        final InvestmentDAO dao = InvestmentDAO.getInstance();
        return dao.findAll();
    }

    public void redirectUserNewInvestment(final Context context) {
        final boolean userHasGoals = SharedPrefsUtil.userHasGoals(context);
        if (userHasGoals) {
            mView.showContributionDialog();
        } else {
            mView.showGoalsDialog();
        }
    }

    public void orderListByDate(boolean sortAsc) {
        final InvestmentDAO dao = InvestmentDAO.getInstance();
        List<Investment> investments = dao.findAllOrderedByDate(sortAsc);
        if (!investments.isEmpty()) {
            mView.updateList(investments);
            mView.setSortedDescByDate(sortAsc ? false : true);
        }
    }

    public void switchInvestmentListLayout(final List<Investment> investments, boolean mIsGroupByAssetType) {
        if (!investments.isEmpty()) {
            mView.setGroupByAssetType(!mIsGroupByAssetType);
            if (mIsGroupByAssetType) {
                mView.configureInvestmentList();
            } else {
                //group by assetType
                TreeMap<AssetTypeEnum, List<Investment>> investmentsMapped = new TreeMap<>();
                for (Investment investment : investments) {
                    final AssetTypeEnum assetType = AssetTypeEnum.getById(investment.getAssetType());
                    if (!investmentsMapped.containsKey(assetType)) {
                        List<Investment> investmentList = new ArrayList<>();
                        investmentList.add(investment);
                        investmentsMapped.put(assetType, investmentList);
                    } else {
                        investmentsMapped.get(assetType).add(investment);
                    }
                }

                //preparing a list for Investment Group List Adapter
                List<InvestmentSectionItem> investmentSectionItems = new ArrayList<>();
                for (AssetTypeEnum assetType : investmentsMapped.keySet()) {
                    InvestmentHeaderItem header = new InvestmentHeaderItem();
                    header.setAssetType(assetType);
                    investmentSectionItems.add(header);
                    for (Investment investment : investmentsMapped.get(assetType)) {
                        InvestmentListItem item = new InvestmentListItem();
                        item.setInvestment(investment);
                        investmentSectionItems.add(item);
                    }
                }

                mView.configureInvestmentListGroup(investmentSectionItems);
            }
        }
    }
}
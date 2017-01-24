package com.jumbomob.invistoo.view;

import com.jumbomob.invistoo.model.entity.Investment;
import com.jumbomob.invistoo.ui.adapter.InvestmentSectionItem;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public interface InvestmentListView {

    void updateList(List<Investment> investments);

    void showContributionDialog();

    void showGoalsDialog();

    void setSortedDescByDate(boolean sortedDescByDate);

    void setGroupByAssetType(boolean groupedByAssetType);

    void configureInvestmentList();

    void configureInvestmentListGroup(List<InvestmentSectionItem> investmentSectionItems);
}

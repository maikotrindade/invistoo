package com.jumbomob.invistoo.view;

import com.jumbomob.invistoo.model.entity.Investment;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public interface InvestmentListView {

    void updateList(List<Investment> investments);

    void showContributionDialog();

    void showGoalsDialog();

    void setIsSortedDescByDate(boolean sortedDescByDate);

}

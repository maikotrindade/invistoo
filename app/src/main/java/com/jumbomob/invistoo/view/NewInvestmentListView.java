package com.jumbomob.invistoo.view;

import com.jumbomob.invistoo.model.dto.InvestmentSuggestionDTO;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public interface NewInvestmentListView {

    void showMessage(int resourceId);

    void navigateToInvestmentList();

    void showProgressDialog(final int resourceId);

    void onDownloadAssetsError();

    void hideProgressDialog();

    void configureRecyclerView(final List<InvestmentSuggestionDTO> suggestions);
}

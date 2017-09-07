package com.jumbomob.invistoo.view;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public interface NewInvestmentListView {

    void showMessage(int resourceId);

    void showMessage(String message);

    void navigateToInvestmentList();

    void showProgressDialog(final int resourceId);

    void hideProgressDialog();
}

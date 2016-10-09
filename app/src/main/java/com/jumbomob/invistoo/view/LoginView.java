package com.jumbomob.invistoo.view;

/**
 * @author maiko.trindade
 * @since 09/10/2016
 */
public interface LoginView {

    void onLoginSuccess();

    void onCreateUserSuccess();

    void showProgressDialog(final int resourceId);

    void hideProgressDialog();

}

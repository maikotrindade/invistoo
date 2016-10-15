package com.jumbomob.invistoo.view;

/**
 * @author maiko.trindade
 * @since 09/10/2016
 */
public interface LoginView {

    void onLoginSuccess(boolean isRememberUser);

    void onCreateUserSuccess();

    void showProgressDialog(int resourceId);

    void hideProgressDialog();

}

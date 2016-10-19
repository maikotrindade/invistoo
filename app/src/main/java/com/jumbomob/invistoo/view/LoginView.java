package com.jumbomob.invistoo.view;

/**
 * @author maiko.trindade
 * @since 09/10/2016
 */
public interface LoginView {

    void onLoginSuccess(String email, boolean isRememberUser);

    void onLoginSuccess();

    void onCreateUserSuccess(String email);

    void showProgressDialog(int resourceId);

    void hideProgressDialog();

}

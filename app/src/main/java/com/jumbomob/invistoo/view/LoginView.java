package com.jumbomob.invistoo.view;

import android.widget.EditText;

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

    void setErrorMessage(final int messageResourceId, final EditText editText);

    void showMessage(final int messageResourceId, final int length);

}

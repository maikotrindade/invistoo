package com.jumbomob.invistoo.view;

/**
 * @author maiko.trindade
 * @since 16/10/2016
 */
public interface MyAccountView {

    void showMessage(int messageId);

    void showProgressDialog(int messageId);

    void invalidatePasswords();

    void invalidateEmail();

    void reloadNavigationHeader();
}

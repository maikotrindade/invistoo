package com.jumbomob.invistoo.view;

/**
 * @author maiko.trindade
 * @since 22/07/2016
 */
public interface SettingsView {

    void showMessage(int resourceId);

    void showDialog(int titleResourceId, int messageResourceId);

    void errorDecorate();

}
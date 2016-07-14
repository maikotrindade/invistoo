package com.jumbomob.invistoo.presenter;

/**
 * Architecture class that determines the common behavior of presenters
 *
 * @author maiko.trindade
 * @since 13/07/2016
 */
public interface BasePresenter<V> {

    void attachView(V view);

    void detachView();

}
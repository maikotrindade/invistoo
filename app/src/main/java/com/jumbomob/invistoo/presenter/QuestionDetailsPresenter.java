package com.jumbomob.invistoo.presenter;

import com.jumbomob.invistoo.view.QuestionDetailsView;

/**
 * @author maiko.trindade
 * @since 24/11/2016
 */
public class QuestionDetailsPresenter implements BasePresenter<QuestionDetailsView> {

    private static final String TAG = QuestionListPresenter.class.getSimpleName();

    private QuestionDetailsView mView;

    @Override
    public void attachView(QuestionDetailsView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public QuestionDetailsPresenter(QuestionDetailsView view) {
        attachView(view);
    }
}
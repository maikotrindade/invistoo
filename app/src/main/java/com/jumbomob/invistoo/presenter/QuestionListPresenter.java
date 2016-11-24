package com.jumbomob.invistoo.presenter;

import android.util.Log;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Question;
import com.jumbomob.invistoo.model.network.BaseNetworkConfig;
import com.jumbomob.invistoo.model.network.QuestionInterface;
import com.jumbomob.invistoo.model.persistence.QuestionDAO;
import com.jumbomob.invistoo.util.ConstantsUtil;
import com.jumbomob.invistoo.view.QuestionListView;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;

/**
 * @author maiko.trindade
 * @since 24/11/2016
 */
public class QuestionListPresenter implements BasePresenter<QuestionListView> {

    private static final String TAG =  QuestionListPresenter.class.getSimpleName();

    private QuestionListView mView;

    @Override
    public void attachView(QuestionListView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public QuestionListPresenter(QuestionListView view) {
        attachView(view);
    }

    public List<Question> getQuestions() {
        final QuestionDAO dao = QuestionDAO.getInstance();
        return dao.findAll();
    }

    public void downloadQuestions() {

        mView.showProgressDialog(R.string.loading_questions);

        final QuestionInterface service = BaseNetworkConfig.createService(QuestionInterface.class,
                ConstantsUtil.BASE_URL);

        final Call<List<Question>> call = service.getQuestions();
        call.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(retrofit.Response<List<Question>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    final QuestionDAO questionDAO = QuestionDAO.getInstance();
                    List<Question> questionsResult = response.body();
                    for (Question question : questionsResult) {
                        questionDAO.insert(question);
                    }
                    mView.updateRecycler(questionsResult);
                    mView.onDownloadSuccess();
                } else {
                    mView.onDownloadError();
                    Log.e(TAG, response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mView.onDownloadError();
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
    }
}
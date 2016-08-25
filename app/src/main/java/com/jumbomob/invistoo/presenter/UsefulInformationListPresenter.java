package com.jumbomob.invistoo.presenter;

import android.util.Log;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Question;
import com.jumbomob.invistoo.model.network.BaseNetworkConfig;
import com.jumbomob.invistoo.model.network.QuestionInterface;
import com.jumbomob.invistoo.model.persistence.QuestionDAO;
import com.jumbomob.invistoo.view.UsefulInformationListView;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public class UsefulInformationListPresenter implements BasePresenter<UsefulInformationListView> {

    private static final String TAG =  UsefulInformationListPresenter.class.getSimpleName();

    private UsefulInformationListView mView;

    @Override
    public void attachView(UsefulInformationListView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public UsefulInformationListPresenter(UsefulInformationListView view) {
        attachView(view);
    }

    public List<Question> getQuestions() {
        final QuestionDAO dao = QuestionDAO.getInstance();
        return dao.findAll();
    }

    public void downloadQuestions() {

        mView.showProgressDialog(R.string.loading_questions);

        final QuestionInterface service = BaseNetworkConfig.createService(QuestionInterface.class,
                BaseNetworkConfig.BASE_URL);

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
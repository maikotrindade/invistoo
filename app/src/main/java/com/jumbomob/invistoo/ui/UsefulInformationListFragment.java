package com.jumbomob.invistoo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Question;
import com.jumbomob.invistoo.model.network.BaseNetworkConfig;
import com.jumbomob.invistoo.model.network.QuestionInterface;
import com.jumbomob.invistoo.model.persistence.QuestionDAO;
import com.jumbomob.invistoo.ui.adapter.QuestionListAdapter;
import com.jumbomob.invistoo.ui.component.DividerItemDecorator;
import com.jumbomob.invistoo.util.InvistooUtil;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;

/**
 * @author maiko.trindade
 * @since 28/03/2016
 */
public class UsefulInformationListFragment extends Fragment {

    private static final String TAG = UsefulInformationListFragment.class.getSimpleName();

    private View mRootView;
    private SwipeRefreshLayout mSwipelayout;
    private QuestionListAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public static UsefulInformationListFragment newInstance() {
        UsefulInformationListFragment fragment = new UsefulInformationListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_useful_info_list, container, false);

        configureRecyclerView();
        configureSwipe();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.title_useful_information);
    }

    private void configureRecyclerView() {
        final QuestionDAO dao = QuestionDAO.getInstance();
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id
                .questions_recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecorator(getActivity(), DividerItemDecorator
                .VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext()));

        mAdapter = new QuestionListAdapter(dao.findAll());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void configureSwipe() {
        mSwipelayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.question_swipe_layout);
        mSwipelayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downloadQuestions();
                mSwipelayout.setRefreshing(false);
            }
        });
    }

    private void downloadQuestions() {
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
                    updateRecycler(questionsResult);
                    InvistooUtil.makeSnackBar(getView(), getString(R.string
                            .msg_asset_questions_success), Snackbar.LENGTH_LONG).show();
                } else {
                    onDownloadError();
                    Log.e(TAG, response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                onDownloadError();
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
    }

    private void onDownloadError() {
        InvistooUtil.makeSnackBar(mRootView, getActivity().getString(R.string
                .error_download_questions), Snackbar
                .LENGTH_LONG).show();
    }

    private void updateRecycler(final List<Question> questions) {
        mAdapter.setItens(questions);
        mAdapter.notifyDataSetChanged();
    }

}

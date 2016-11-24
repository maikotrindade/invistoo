package com.jumbomob.invistoo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Question;
import com.jumbomob.invistoo.presenter.QuestionListPresenter;
import com.jumbomob.invistoo.ui.adapter.QuestionListAdapter;
import com.jumbomob.invistoo.util.InvistooUtil;
import com.jumbomob.invistoo.view.QuestionListView;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 28/03/2016
 */
public class QuestionListFragment extends BaseFragment implements QuestionListView {

    private View mRootView;
    private QuestionListPresenter mPresenter;
    private SwipeRefreshLayout mSwipeLayout;
    private QuestionListAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public static QuestionListFragment newInstance() {
        QuestionListFragment fragment = new QuestionListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_question_list, container, false);
        mPresenter = new QuestionListPresenter(this);

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
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.questions_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mRootView.getContext(), 2));

        final List<Question> questions = mPresenter.getQuestions();
        mAdapter = new QuestionListAdapter(questions, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        if (questions.isEmpty()) {
            mPresenter.downloadQuestions();
        }
    }

    @Override
    public void showProgressDialog(final int resourceId) {
        super.showProgressDialog(resourceId);
    }

    @Override
    public void hideProgressDialog() {
        super.hideProgressDialog();
    }

    private void configureSwipe() {
        mSwipeLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.question_swipe_layout);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.downloadQuestions();
                mSwipeLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onDownloadError() {
        hideProgressDialog();
        InvistooUtil.makeSnackBar(getActivity(), getActivity().getString(R.string
                .error_download_questions), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDownloadSuccess() {
        hideProgressDialog();
        InvistooUtil.makeSnackBar(getActivity(), getString(R.string.msg_asset_questions_success),
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void updateRecycler(final List<Question> questions) {
        mAdapter.setItens(questions);
        mAdapter.notifyDataSetChanged();
    }

}

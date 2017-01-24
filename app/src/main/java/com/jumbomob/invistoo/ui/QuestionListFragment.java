package com.jumbomob.invistoo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Question;
import com.jumbomob.invistoo.model.entity.QuestionGroupEnum;
import com.jumbomob.invistoo.presenter.QuestionListPresenter;
import com.jumbomob.invistoo.ui.adapter.QuestionGroupListAdapter;
import com.jumbomob.invistoo.ui.adapter.QuestionHeaderItem;
import com.jumbomob.invistoo.ui.adapter.QuestionListItem;
import com.jumbomob.invistoo.ui.adapter.QuestionSectionItem;
import com.jumbomob.invistoo.util.InvistooUtil;
import com.jumbomob.invistoo.view.QuestionListView;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * @author maiko.trindade
 * @since 28/03/2016
 */
public class QuestionListFragment extends BaseFragment implements QuestionListView {

    private View mRootView;
    private QuestionListPresenter mPresenter;
    private SwipeRefreshLayout mSwipeLayout;
    private QuestionGroupListAdapter mAdapter;
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext()));
        //mRecyclerView.setLayoutManager(new GridLayoutManager(mRootView.getContext(), 2));

        final List<Question> questions = mPresenter.getQuestions();
        if (questions.isEmpty()) {
            mPresenter.downloadQuestions();
        } else {
            final List<QuestionSectionItem> questionSectionItems = prepareQuestionGroupList(questions);
            mAdapter = new QuestionGroupListAdapter(questionSectionItems, getActivity());
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    private List<QuestionSectionItem> prepareQuestionGroupList(List<Question> questions) {
        //group by questionGroup
        TreeMap<QuestionGroupEnum, List<Question>> questionsMapped = new TreeMap<>();
        for (Question question : questions) {
            final QuestionGroupEnum groupType = QuestionGroupEnum.getById(Long.parseLong(question.getGroup()));
            if (!questionsMapped.containsKey(groupType)) {
                List<Question> questionList = new ArrayList<>();
                questionList.add(question);
                questionsMapped.put(groupType, questionList);
            } else {
                questionsMapped.get(groupType).add(question);
            }
        }

        //preparing a list for Question Group List Adapter
        List<QuestionSectionItem> groupSectionItems = new ArrayList<>();
        for (QuestionGroupEnum groupEnum : questionsMapped.keySet()) {
            QuestionHeaderItem header = new QuestionHeaderItem();
            header.setQuestionGroupEnum(groupEnum);
            header.setNumberOfElements(questionsMapped.get(groupEnum).size());
            groupSectionItems.add(header);
            for (Question question : questionsMapped.get(groupEnum)) {
                QuestionListItem item = new QuestionListItem();
                item.setQuestion(question);
                groupSectionItems.add(item);
            }
        }

        return groupSectionItems;
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
        final List<QuestionSectionItem> questionSectionItems = prepareQuestionGroupList(questions);
        if (mAdapter == null) {
            mAdapter = new QuestionGroupListAdapter(questionSectionItems, getActivity());
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItens(questionSectionItems);
            mAdapter.notifyDataSetChanged();
        }


    }

}

package com.jumbomob.invistoo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Question;
import com.jumbomob.invistoo.model.entity.QuestionGroupEnum;
import com.jumbomob.invistoo.util.ConstantsUtil;

public class QuestionDetailsFragment extends Fragment {

    private View mRootView;

    public static QuestionDetailsFragment newInstance() {
        QuestionDetailsFragment fragment = new QuestionDetailsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_question_details, container, false);

        configureElements();
        return mRootView;
    }

    private void configureElements() {
        final Bundle arguments = getArguments();
        final Question question = arguments.getParcelable(ConstantsUtil.QUESTION_DETAILS_BUNDLE);

        final Long groupId = Long.valueOf(question.getGroup());
        final String groupName = QuestionGroupEnum.getById(groupId).getTitle();
        getActivity().setTitle(groupName);

        final TextView questionTxtView = (TextView) mRootView.findViewById(R.id.question_text_view);
        questionTxtView.setText(question.getQuestion());

        final TextView groupTxtView = (TextView) mRootView.findViewById(R.id.group_text_view);
        groupTxtView.setText(groupName);

        final TextView answerTxtView = (TextView) mRootView.findViewById(R.id.answer_text_view);
        answerTxtView.setText(question.getAnswer());
    }

}

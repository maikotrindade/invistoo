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
        final Question question = arguments.getParcelable("DETAILS");

        final TextView questionTxtView = (TextView) mRootView.findViewById(R.id.question_text_view);
        questionTxtView.setText(question.getQuestion());

    }

}

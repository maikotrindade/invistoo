package com.jumbomob.invistoo.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Question;
import com.jumbomob.invistoo.model.entity.QuestionGroupEnum;
import com.jumbomob.invistoo.util.ConstantsUtil;
import com.jumbomob.invistoo.util.DateUtil;

import java.util.Date;

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

        final TextView questionTxtView = (TextView) mRootView.findViewById(R.id.question_text_view);
        questionTxtView.setText(question.getQuestion());

        final TextView groupTxtView = (TextView) mRootView.findViewById(R.id.group_text_view);
        groupTxtView.setText(groupName);

        final TextView answerTxtView = (TextView) mRootView.findViewById(R.id.answer_text_view);
        answerTxtView.setText(question.getAnswer());

        final TextView datetimeTxtView = (TextView) mRootView.findViewById(R.id.datetime_text_view);
        final Date date = DateUtil.stringToDate(question.getDatetime(), DateUtil.ISO_FORMAT);
        if (date != null) {
            datetimeTxtView.setVisibility(View.VISIBLE);
            datetimeTxtView.setText(DateUtil.formatDate(date, DateUtil.MATERIAL_DATE_FORMAT));
        } else {
            datetimeTxtView.setVisibility(View.GONE);
        }

        final TextView authorTxtView = (TextView) mRootView.findViewById(R.id.author_text_view);
        final String author = question.getAuthor();
        if (!TextUtils.isEmpty(author)) {
            authorTxtView.setVisibility(View.VISIBLE);
            authorTxtView.setText(getString(R.string.written_by, author));
        } else {
            authorTxtView.setVisibility(View.GONE);
        }

        final Button readMoreButton = (Button) mRootView.findViewById(R.id.read_more_button);
        final String referenceUrl = question.getReferenceUrl();
        if (!TextUtils.isEmpty(referenceUrl)) {
            readMoreButton.setVisibility(View.VISIBLE);
            readMoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(referenceUrl)));
                }
            });
        } else {
            readMoreButton.setVisibility(View.GONE);
        }
    }
}

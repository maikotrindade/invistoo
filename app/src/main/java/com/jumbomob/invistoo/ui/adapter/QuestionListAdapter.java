package com.jumbomob.invistoo.ui.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Question;
import com.jumbomob.invistoo.ui.BaseActivity;
import com.jumbomob.invistoo.ui.QuestionDetailsFragment;
import com.jumbomob.invistoo.ui.callback.onSearchResultListener;
import com.jumbomob.invistoo.util.ConstantsUtil;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 29/03/2016
 */
public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.ViewHolder> {

    private List<Question> mQuestions;
    private int mPosition;
    private Activity mActivity;
    private onSearchResultListener mSearchListener;

    public QuestionListAdapter(List<Question> Questions, Activity activity) {
        mQuestions = Questions;
        mActivity = activity;
    }

    public void setItens(List<Question> Questions) {
        this.mQuestions = Questions;
    }

    public Question getSelectedItem() {
        return mQuestions.get(mPosition);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.item_question_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Question question = mQuestions.get(position);
        holder.questionTxtView.setText(question.getQuestion());
        holder.questionContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final QuestionDetailsFragment questionDetailsFragment = QuestionDetailsFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putParcelable(ConstantsUtil.QUESTION_DETAILS_BUNDLE, question);
                questionDetailsFragment.setArguments(bundle);
                ((BaseActivity) mActivity).setFragment(questionDetailsFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView questionTxtView;
        private View questionContainer;

        public ViewHolder(View view) {
            super(view);
            questionTxtView = (TextView) view.findViewById(R.id.question_text_view);
            questionContainer = view.findViewById(R.id.question_adapter);
        }
    }

}
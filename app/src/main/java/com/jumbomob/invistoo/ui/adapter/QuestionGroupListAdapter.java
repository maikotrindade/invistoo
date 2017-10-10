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
import com.jumbomob.invistoo.util.ConstantsUtil;
import com.jumbomob.invistoo.util.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * @author maiko.trindade
 * @since 29/03/2016
 */

public class QuestionGroupListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<QuestionSectionItem> mItems;
    private Activity mActivity;

    public QuestionGroupListAdapter(List<QuestionSectionItem> QuestionSectionItems, Activity activity) {
        mItems = QuestionSectionItems;
        mActivity = activity;
    }

    public void setItems(List<QuestionSectionItem> Questions) {
        this.mItems = Questions;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == QuestionSectionItem.QUESTION_SECTION_HEADER) {
            View itemView = layoutInflater.inflate(R.layout.item_question_list_header, parent, false);
            return new HeaderViewHolder(itemView);
        } else {
            View itemView = layoutInflater.inflate(R.layout.item_question_list_content, parent, false);
            return new ListViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        int type = getItemViewType(position);
        if (type == QuestionSectionItem.QUESTION_SECTION_HEADER) {
            QuestionHeaderItem header = (QuestionHeaderItem) mItems.get(position);
            HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
            holder.groupTxtView.setText(header.getQuestionGroupEnum().getTitle());
            String numberOfItemsString;
            if (header.getNumberOfElements() > 1) {
                numberOfItemsString = mActivity.getString(R.string.numberOfItems, header.getNumberOfElements());
            } else {
                numberOfItemsString = mActivity.getString(R.string.numberOfItem, header.getNumberOfElements());
            }
            holder.numberElementsTxtView.setText(String.valueOf(numberOfItemsString));
        } else {
            QuestionListItem listItem = (QuestionListItem) mItems.get(position);
            final Question question = listItem.getQuestion();
            ListViewHolder holder = (ListViewHolder) viewHolder;
            holder.questionTxtView.setText(question.getQuestion());
            final Date date = DateUtil.stringToDate(question.getDatetime(), DateUtil.ISO_FORMAT);
            if (date != null) {
                holder.dateTxtView.setVisibility(View.VISIBLE);
                holder.dateTxtView.setText(DateUtil.formatDate(date, DateUtil.MATERIAL_DATE_FORMAT));
            } else {
                holder.dateTxtView.setVisibility(View.GONE);
            }

            holder.questionContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final QuestionDetailsFragment questionDetailsFragment = QuestionDetailsFragment.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(ConstantsUtil.QUESTION_DETAILS_BUNDLE, question);
                    questionDetailsFragment.setArguments(bundle);
                    ((BaseActivity) mActivity).setFragmentWithStack(questionDetailsFragment, R.string.app_name);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getSection();
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView groupTxtView;
        private TextView numberElementsTxtView;

        public HeaderViewHolder(View view) {
            super(view);
            groupTxtView = (TextView) view.findViewById(R.id.group_text_view);
            numberElementsTxtView = (TextView) view.findViewById(R.id.number_elements_text_view);
        }
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private View questionContainer;
        private TextView questionTxtView;
        private TextView dateTxtView;

        public ListViewHolder(View view) {
            super(view);
            questionContainer = view.findViewById(R.id.question_container);
            questionTxtView = (TextView) view.findViewById(R.id.question_text_view);
            dateTxtView = (TextView) view.findViewById(R.id.date_text_view);
        }
    }
}
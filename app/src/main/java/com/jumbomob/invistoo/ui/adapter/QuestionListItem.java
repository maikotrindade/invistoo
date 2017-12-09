package com.jumbomob.invistoo.ui.adapter;

import com.jumbomob.invistoo.model.entity.Question;

/**
 * Created by trindade on 1/21/17.
 */

public class QuestionListItem extends QuestionSectionItem {
    private Question question;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public int getSection() {
        return QUESTION_SECTION_LIST;
    }
}

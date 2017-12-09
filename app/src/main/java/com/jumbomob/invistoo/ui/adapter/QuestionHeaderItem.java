package com.jumbomob.invistoo.ui.adapter;

import com.jumbomob.invistoo.model.entity.QuestionGroupEnum;

/**
 * Created by trindade on 1/21/17.
 */

public class QuestionHeaderItem extends QuestionSectionItem {
    private QuestionGroupEnum questionGroupEnum;
    private int numberOfElements;

    public QuestionGroupEnum getQuestionGroupEnum() {
        return questionGroupEnum;
    }

    public void setQuestionGroupEnum(QuestionGroupEnum questionGroupEnum) {
        this.questionGroupEnum = questionGroupEnum;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    @Override
    public int getSection() {
        return QUESTION_SECTION_HEADER;
    }

}

package com.jumbomob.invistoo.ui.adapter;

/**
 * Created by trindade on 1/21/17.
 */

public abstract class QuestionSectionItem {

    public static final int QUESTION_SECTION_HEADER = 0;
    public static final int QUESTION_SECTION_LIST = 1;

    abstract public int getSection();
}
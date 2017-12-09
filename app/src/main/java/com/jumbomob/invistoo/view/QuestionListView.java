package com.jumbomob.invistoo.view;

import com.jumbomob.invistoo.model.entity.Question;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 24/11/2016
 */
public interface QuestionListView {

    void onDownloadError();

    void onDownloadSuccess();

    void updateRecycler(final List<Question> questions);

    void showProgressDialog(final int resourceId);

}

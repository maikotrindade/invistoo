package com.jumbomob.invistoo.view;

import com.jumbomob.invistoo.model.entity.Question;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 23/07/2016
 */
public interface UsefulInformationListView {

    void onDownloadError();

    void onDownloadSuccess();

    void updateRecycler(final List<Question> questions);

    void showProgressDialog(final int resourceId);

}

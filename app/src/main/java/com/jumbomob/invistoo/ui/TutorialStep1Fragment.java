package com.jumbomob.invistoo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jumbomob.invistoo.R;

/**
 * @author maiko.trindade<mt@ubby.io>
 * @since 9/10/17
 */

public class TutorialStep1Fragment extends BaseFragment {

    private View mRootView;

    public static TutorialStep1Fragment newInstance() {
        TutorialStep1Fragment fragment = new TutorialStep1Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_tutorial_step1, container, false);
        return mRootView;
    }

}

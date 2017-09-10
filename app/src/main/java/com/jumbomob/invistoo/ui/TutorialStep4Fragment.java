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

public class TutorialStep4Fragment extends BaseFragment {

    private View mRootView;

    public static TutorialStep4Fragment newInstance() {
        TutorialStep4Fragment fragment = new TutorialStep4Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_tutorial_step4, container, false);
        return mRootView;
    }

}

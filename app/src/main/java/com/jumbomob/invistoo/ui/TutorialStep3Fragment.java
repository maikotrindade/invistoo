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

public class TutorialStep3Fragment extends BaseFragment {

    private View mRootView;

    public static TutorialStep3Fragment newInstance() {
        TutorialStep3Fragment fragment = new TutorialStep3Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_tutorial_step3, container, false);
        return mRootView;
    }

}

package com.jumbomob.invistoo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jumbomob.invistoo.R;

/**
 * @author maiko.trindade
 * @since 14/02/2016
 */
public class MyWalletFragment extends Fragment {

    private View mRootView;

    public static MyWalletFragment newInstance() {
        MyWalletFragment fragment = new MyWalletFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_my_wallet, container, false);

        configureFab();
        return mRootView;
    }

    private void configureFab() {

        FloatingActionButton myFab = (FloatingActionButton) mRootView.findViewById(R.id.add_fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Activity activity = getActivity();
                ((BaseActivity) activity).setFragment(NewInvestmentFragment.newInstance(), activity.getString(R
                        .string.title_new_investiment));
            }
        });

    }

}

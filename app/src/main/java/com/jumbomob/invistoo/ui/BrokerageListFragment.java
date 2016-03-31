package com.jumbomob.invistoo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jumbomob.invistoo.R;

/**
 * @author maiko.trindade
 * @since 30/03/2016
 */
public class BrokerageListFragment extends Fragment {

    private static final String TAG = BrokerageListFragment.class.getSimpleName();

    private View mRootView;

    public static BrokerageListFragment newInstance() {
        BrokerageListFragment fragment = new BrokerageListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mRootView = inflater.inflate(R.layout.fragment_asset_list, container, false);

        return mRootView;
    }
}
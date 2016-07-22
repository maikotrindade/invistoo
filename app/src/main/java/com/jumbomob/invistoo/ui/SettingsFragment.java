package com.jumbomob.invistoo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.AssetTypeEnum;
import com.jumbomob.invistoo.presenter.SettingsPresenter;
import com.jumbomob.invistoo.ui.adapter.SettingsAdapter;
import com.jumbomob.invistoo.ui.component.DividerItemDecorator;
import com.jumbomob.invistoo.util.InvistooUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author maiko.trindade
 * @since 30/03/2016
 */
public class SettingsFragment extends Fragment {

    private static final String TAG = SettingsFragment.class.getSimpleName();

    private View mRootView;
    private SettingsPresenter mPresenter;
    private List<AssetTypeEnum> mAssetTypes;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mRootView = inflater.inflate(R.layout.fragment_settings, container, false);

        mPresenter = new SettingsPresenter();
        configureElements();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.title_settings);
    }

    private void configureElements() {
        mAssetTypes = Arrays.asList(AssetTypeEnum.values());
        configureFab();
        configureRecyclerView();
    }

    private void configureFab() {
        FloatingActionButton newSettingsFab = (FloatingActionButton)
                mRootView.findViewById(R.id.new_asset_settings);
        newSettingsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InvistooUtil.makeSnackBar(getView(), getString(R.string.todo),
                        Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void configureRecyclerView() {
        RecyclerView recyclerView = (RecyclerView)
                mRootView.findViewById(R.id.settings_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecorator(getActivity(),
                DividerItemDecorator.VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext()));

        List<String> names = new ArrayList<>();
        for (AssetTypeEnum assetTypeEnum : mAssetTypes) {
            names.add(assetTypeEnum.getTitle());
        }

        final SettingsAdapter adapter = new SettingsAdapter(names);
        recyclerView.setAdapter(adapter);
    }
}
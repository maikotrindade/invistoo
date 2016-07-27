package com.jumbomob.invistoo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Goal;
import com.jumbomob.invistoo.model.persistence.GoalDAO;
import com.jumbomob.invistoo.presenter.SettingsPresenter;
import com.jumbomob.invistoo.ui.adapter.SettingsAdapter;
import com.jumbomob.invistoo.util.InvistooUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maiko.trindade
 * @since 30/03/2016
 */
public class SettingsFragment extends Fragment {

    private static final String TAG = SettingsFragment.class.getSimpleName();

    private View mRootView;
    private SettingsPresenter mPresenter;
    private SettingsAdapter mAdapter;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_settings, container, false);
        setHasOptionsMenu(true);
        mPresenter = new SettingsPresenter();
        configureElements();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.title_settings);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.new_settings_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveGoals();
                break;
        }
        return false;
    }

    private void saveGoals() {
        final List<Goal> goals = mAdapter.getItens();
        final GoalDAO goalDAO = GoalDAO.getInstance();
        goalDAO.insert(goals);
        InvistooUtil.makeSnackBar(getView(), getString(R.string.msg_asset_updated_success),
                Snackbar.LENGTH_LONG).show();
    }

    private void configureElements() {
        configureRecyclerView();
        configureFab();
    }

    private void configureFab() {
        FloatingActionButton newSettingsFab = (FloatingActionButton)
                mRootView.findViewById(R.id.new_asset_settings);
        newSettingsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.add(mAdapter.getItemCount());
            }
        });
    }

    private void configureRecyclerView() {
        RecyclerView recyclerView = (RecyclerView)
                mRootView.findViewById(R.id.settings_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext()));

        mAdapter = new SettingsAdapter(mRootView.getContext(), new ArrayList<Goal>());
        recyclerView.setAdapter(mAdapter);
    }
}
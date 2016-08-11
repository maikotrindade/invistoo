package com.jumbomob.invistoo.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.github.clans.fab.FloatingActionButton;
import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.AssetTypeAdapter;
import com.jumbomob.invistoo.model.entity.AssetTypeEnum;
import com.jumbomob.invistoo.model.entity.Goal;
import com.jumbomob.invistoo.model.persistence.GoalDAO;
import com.jumbomob.invistoo.presenter.SettingsPresenter;
import com.jumbomob.invistoo.ui.adapter.SpinnerAssetAdapter;
import com.jumbomob.invistoo.util.InvistooUtil;
import com.jumbomob.invistoo.util.NumericUtil;

import io.realm.RealmList;

/**
 * @author maiko.trindade
 * @since 30/03/2016
 */
public class SettingsFragment extends BaseFragment {

    private static final String TAG = SettingsFragment.class.getSimpleName();

    private View mRootView;
    private SettingsPresenter mPresenter;
    private LinearLayout mRowContainer;
    private RealmList<Goal> mGoals;

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
        mGoals = new RealmList<>();
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
        Log.d(TAG, mGoals.toString());
        final GoalDAO goalDAO = GoalDAO.getInstance();
        goalDAO.insert(mGoals);
        InvistooUtil.makeSnackBar(getActivity(), getString(R.string.msg_asset_updated_success),
                Snackbar.LENGTH_LONG).show();
    }

    private void configureElements() {
        mRowContainer = (LinearLayout) mRootView.findViewById(R.id.rows_container);
        loadGoals();
        configureFab();
    }

    private void loadGoals() {
        final GoalDAO goalDAO = GoalDAO.getInstance();
        mGoals.addAll(goalDAO.findAll());
        Log.d(TAG, mGoals.toString());

        for (Goal goal : mGoals) {
            loadGoals(goal);
            Log.d(TAG, "######### Loaded Goal : " + goal.toString());
        }
    }

    private void configureFab() {
        FloatingActionButton newSettingsFab = (FloatingActionButton)
                mRootView.findViewById(R.id.new_asset_settings);
        newSettingsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewGoal();
            }
        });
    }

    private void addNewGoal() {
        final Goal goal = new Goal();
        goal.setPercent(new Double(0));

        LayoutInflater layoutInflater =
                (LayoutInflater) getContext().getSystemService(Context
                        .LAYOUT_INFLATER_SERVICE);
        final View newGoalView = layoutInflater.inflate(R.layout.item_settings_list, null);
        mRowContainer.addView(newGoalView);

        final EditText percentageEditText = (EditText)
                newGoalView.findViewById(R.id.percentage_edit_text);

        percentageEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    final String percentage = ((EditText) v).getText().toString();
                    if (NumericUtil.isValidDouble(percentage)) {
                        goal.setPercent(Double.valueOf(percentage));
                    }
                }
            }
        });

        final SpinnerAssetAdapter dataAdapter = new SpinnerAssetAdapter
                (getContext(), android.R.layout.simple_spinner_item, AssetTypeEnum
                        .values());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner assetSpinner = (Spinner) newGoalView.findViewById(R.id
                .assets_spinner);
        assetSpinner.setAdapter(dataAdapter);
        assetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos,
                                       long id) {
                goal.setAssetTypeEnum(dataAdapter.getItem(pos).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        AssetTypeAdapter assetAdapter = new AssetTypeAdapter();
        goal.setAssetTypeEnum(((AssetTypeEnum) assetSpinner.getSelectedItem()).getId());
        mGoals.add(goal);
    }

    private void loadGoals(final Goal goal) {

        LayoutInflater layoutInflater =
                (LayoutInflater) getContext().getSystemService(Context
                        .LAYOUT_INFLATER_SERVICE);
        final View newGoalView = layoutInflater.inflate(R.layout.item_settings_list, null);
        mRowContainer.addView(newGoalView);

        final EditText percentageEditText = (EditText)
                newGoalView.findViewById(R.id.percentage_edit_text);

        final Double percent = goal.getPercent();
        if (percent != null) {
            percentageEditText.setText(String.valueOf(percent));
        }

        percentageEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    goal.setPercent(Double.valueOf(((EditText) v).getText().toString()));
                }
            }
        });

        final SpinnerAssetAdapter dataAdapter = new SpinnerAssetAdapter
                (getContext(), android.R.layout.simple_spinner_item, AssetTypeEnum
                        .values());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner assetSpinner = (Spinner) newGoalView.findViewById(R.id.assets_spinner);
        assetSpinner.setAdapter(dataAdapter);
        assetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                goal.setAssetTypeEnum(dataAdapter.getItem(pos).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        goal.setAssetTypeEnum(((AssetTypeEnum) assetSpinner.getSelectedItem()).getId());
        mGoals.add(goal);
    }
}
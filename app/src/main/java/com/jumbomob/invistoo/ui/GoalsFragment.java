package com.jumbomob.invistoo.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.jumbomob.invistoo.model.entity.AssetTypeEnum;
import com.jumbomob.invistoo.model.entity.Goal;
import com.jumbomob.invistoo.presenter.GoalsPresenter;
import com.jumbomob.invistoo.ui.adapter.SpinnerAssetAdapter;
import com.jumbomob.invistoo.util.NumericUtil;
import com.jumbomob.invistoo.view.GoalsView;

import io.realm.RealmList;

/**
 * @author maiko.trindade
 * @since 30/03/2016
 */
public class GoalsFragment extends BaseFragment implements GoalsView {

    private static final String TAG = GoalsFragment.class.getSimpleName();

    private View mRootView;
    private GoalsPresenter mPresenter;
    private LinearLayout mRowContainer;
    private RealmList<Goal> mGoals;

    public static GoalsFragment newInstance() {
        GoalsFragment fragment = new GoalsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_settings, container, false);
        setHasOptionsMenu(true);
        mGoals = new RealmList<>();
        mPresenter = new GoalsPresenter(this);
        configureElements();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.title_goals);
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
                mPresenter.saveGoals(mGoals);
                break;
        }
        return false;
    }

    @Override
    public void showDialog(int titleResourceId, int messageResourceId) {
        super.showDialog(titleResourceId, messageResourceId);
    }

    @Override
    public void errorDecorate() {
        int childCount = mRowContainer.getChildCount();
        for (int index = 0; index < childCount; index++) {
            LinearLayout layoutContainer = (LinearLayout) mRowContainer.getChildAt(index);
            final EditText percentageEditText = (EditText)
                    layoutContainer.findViewById(R.id.percentage_edit_text);
            percentageEditText.getBackground().mutate().setColorFilter(
                    getResources().getColor(R.color.material_red_800), PorterDuff.Mode.SRC_ATOP);
        }
    }

    @Override
    public void showMessage(final int resourceId) {
        super.showMessage(resourceId);
    }

    private void configureElements() {
        mRowContainer = (LinearLayout) mRootView.findViewById(R.id.rows_container);
        loadGoals();
        configureFab();
    }

    private void loadGoals() {
        mGoals.addAll(mPresenter.getGoals());
        for (Goal goal : mGoals) {
            loadGoals(goal);
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
        final View newGoalView = layoutInflater.inflate(R.layout.item_goals_list, null);
        mRowContainer.addView(newGoalView);

        //Remove goal
        final LinearLayout removeGoalContainer = (LinearLayout)
                newGoalView.findViewById(R.id.remove_goal_container);
        if (mGoals.size() > 1) {
            removeGoalContainer.setVisibility(View.VISIBLE);
            removeGoalContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e(TAG, "remove_goal_container CLIKED");
                    //TODO
                    newGoalView.setVisibility(View.GONE);
                }
            });
        } else {
            removeGoalContainer.setVisibility(View.GONE);
        }

            //Percentage EditText
            final EditText percentageEditText = (EditText)
                    newGoalView.findViewById(R.id.percentage_edit_text);
            percentageEditText.addTextChangedListener(new TextWatcher() {
                public void onTextChanged(CharSequence textChanged, int start, int before, int
                        count) {
                    final String percentage = textChanged.toString();
                    if (!TextUtils.isEmpty(percentage)) {
                        if (NumericUtil.isValidDouble(percentage)) {
                            goal.setPercent(Double.valueOf(percentage));
                        }
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void afterTextChanged(Editable s) {
                }
            });

            //Spinner Widget
            final SpinnerAssetAdapter dataAdapter = new SpinnerAssetAdapter
                    (getContext(), android.R.layout.simple_spinner_item, AssetTypeEnum.values());
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            final Spinner assetSpinner = (Spinner) newGoalView.findViewById(R.id
                    .assets_spinner);
            assetSpinner.setAdapter(dataAdapter);
            assetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long
                        id) {
                    goal.setAssetTypeEnum(dataAdapter.getItem(pos).getId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            goal.setAssetTypeEnum(((AssetTypeEnum) assetSpinner.getSelectedItem()).getId());
            mGoals.add(goal);
        }

    private void loadGoals(final Goal goal) {
        LayoutInflater layoutInflater =
                (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View newGoalView = layoutInflater.inflate(R.layout.item_goals_list, null);
        mRowContainer.addView(newGoalView);

        final EditText percentageEditText = (EditText)
                newGoalView.findViewById(R.id.percentage_edit_text);

        final Double percent = goal.getPercent();
        if (percent != null) {
            percentageEditText.setText(String.valueOf(percent));
        }

        percentageEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence textChanged, int start, int before, int
                    count) {
                final String percentage = textChanged.toString();
                if (!TextUtils.isEmpty(percentage)) {
                    mPresenter.updatePercentage(goal, Double.valueOf(percentage));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        final SpinnerAssetAdapter dataAdapter = new SpinnerAssetAdapter
                (getContext(), android.R.layout.simple_spinner_item, AssetTypeEnum.values());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner assetSpinner = (Spinner) newGoalView.findViewById(R.id.assets_spinner);
        assetSpinner.setAdapter(dataAdapter);

        final int position = dataAdapter.getPosition(goal.getAssetTypeEnum());
        assetSpinner.setSelection(position);

        assetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long
                    id) {
                final long assetTypeId = dataAdapter.getItem(pos).getId();
                mPresenter.updateAssetType(goal, assetTypeId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


}
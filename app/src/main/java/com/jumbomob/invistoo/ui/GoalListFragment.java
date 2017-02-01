package com.jumbomob.invistoo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.clans.fab.FloatingActionButton;
import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Goal;
import com.jumbomob.invistoo.presenter.GoalsPresenter;
import com.jumbomob.invistoo.ui.adapter.GoalListAdapter;
import com.jumbomob.invistoo.util.InvistooUtil;
import com.jumbomob.invistoo.view.GoalsView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maiko.trindade
 * @since 30/03/2016
 */
public class GoalListFragment extends BaseFragment implements GoalsView {

    private static final String TAG = GoalListFragment.class.getSimpleName();

    private View mRootView;
    private GoalsPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private GoalListAdapter mAdapter;
    private LinearLayout mNoGoalsLayout;

    public static GoalListFragment newInstance() {
        GoalListFragment fragment = new GoalListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_goals, container, false);
        setHasOptionsMenu(true);
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
        inflater.inflate(R.menu.goal_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                InvistooUtil.hideKeyboard(getActivity());
                mPresenter.saveGoals(mAdapter.getItems());
                return true;
            case R.id.action_info:
                showDialog(R.string.title_goals, R.string.goals_more_info_message);
                return true;
        }
        return false;
    }

    @Override
    public void showDialog(int titleResourceId, int messageResourceId) {
        super.showDialog(titleResourceId, messageResourceId);
    }

    @Override
    public void showMessage(final int resourceId) {
        super.showMessage(resourceId);
    }

    private void configureElements() {
        mNoGoalsLayout = (LinearLayout) mRootView.findViewById(R.id.no_goals_layout);
        configureRecyclerView();
        configureFab();
    }

    private void configureRecyclerView() {
        final List<Goal> goals = mPresenter.getGoals();

//        if (!goals.isEmpty()) {
        //mNoGoalsLayout.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.goals_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext()));
        mAdapter = new GoalListAdapter(getActivity(), goals);
        mRecyclerView.setAdapter(mAdapter);
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                mAdapter.removeAt(viewHolder.getAdapterPosition());
            }

        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }


    public void updateGoalList(final List<Goal> goals) {
        if (mAdapter == null) {
            mAdapter = new GoalListAdapter(getActivity(), goals);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(goals);
            mRecyclerView.scrollToPosition(goals.size() - 1);
        }
    }

    private void configureFab() {
        FloatingActionButton newSettingsFab = (FloatingActionButton)
                mRootView.findViewById(R.id.new_asset_settings);
        newSettingsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InvistooUtil.hideKeyboard(getActivity());
                List<Goal> goals = mAdapter.getItems();
                if (goals == null) {
                    goals = new ArrayList<>();
                }
                addNewGoal(goals);
            }
        });
    }

    private void addNewGoal(final List<Goal> goals) {
        final Goal goal = new Goal();
        goal.setPercent(mPresenter.getPercentLeft(goals));
        goals.add(goal);
        updateGoalList(goals);
    }


}
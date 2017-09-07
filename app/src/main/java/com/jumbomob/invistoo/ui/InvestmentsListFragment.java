package com.jumbomob.invistoo.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.github.clans.fab.FloatingActionButton;
import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.dto.InvestmentSuggestionDTO;
import com.jumbomob.invistoo.model.entity.Investment;
import com.jumbomob.invistoo.presenter.InvestmentListPresenter;
import com.jumbomob.invistoo.ui.adapter.InvestmentGroupListAdapter;
import com.jumbomob.invistoo.ui.adapter.InvestmentListAdapter;
import com.jumbomob.invistoo.ui.adapter.InvestmentSectionItem;
import com.jumbomob.invistoo.util.ConstantsUtil;
import com.jumbomob.invistoo.util.DialogUtil;
import com.jumbomob.invistoo.util.NumericUtil;
import com.jumbomob.invistoo.view.InvestmentListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maiko.trindade
 * @since 14/02/2016
 */
public class InvestmentsListFragment extends BaseFragment implements InvestmentListView {

    private View mRootView;
    private InvestmentListPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private InvestmentListAdapter mAdapter;
    private Menu mMenu;
    private boolean mIsSortedDescByDate;
    private boolean mIsGroupByAssetType;

    public static InvestmentsListFragment newInstance() {
        InvestmentsListFragment fragment = new InvestmentsListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mRootView = inflater.inflate(R.layout.fragment_investments_list, container, false);

        mPresenter = new InvestmentListPresenter(this);
        configureInvestmentList();
        configureFab();

        return mRootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        mMenu = menu;
        inflater.inflate(R.menu.investment_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_order_by_date:
                mPresenter.orderListByDate(mIsSortedDescByDate);
                return true;
            case R.id.action_group:
                if (mAdapter != null) {
                    mPresenter.switchInvestmentListLayout(mAdapter.getItens(), mIsGroupByAssetType);
                }
                return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.my_investments);
    }

    @Override
    public void configureInvestmentList() {
        LinearLayout investmentsContainer = (LinearLayout) mRootView.findViewById(R.id.no_investments_container);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.investments_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext()));

        if (!mPresenter.findInvestments().isEmpty()) {
            mRecyclerView.setVisibility(View.VISIBLE);
            investmentsContainer.setVisibility(View.GONE);
            mAdapter = new InvestmentListAdapter(mPresenter.findInvestments(), this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            investmentsContainer.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void configureInvestmentListGroup(List<InvestmentSectionItem> investmentSectionItems) {

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext()));
        InvestmentGroupListAdapter adapter = new InvestmentGroupListAdapter(investmentSectionItems, this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoSuggestionMade() {
        showMessage(getString(R.string.wrong_value_contribution));
    }

    @Override
    public void onSuggestionsMade(ArrayList<InvestmentSuggestionDTO> suggestions) {
        Fragment fragment = new NewInvestmentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ConstantsUtil.SUGGESTIONS_BUNDLE, suggestions);
        fragment.setArguments(bundle);
        ((BaseActivity) getActivity()).setFragment(fragment, getActivity().getString(R.string.title_new_investment));
    }

    private void configureFab() {
        FloatingActionButton newBalancedInvestFab = (FloatingActionButton)
                mRootView.findViewById(R.id.new_balanced_investment_fab);
        newBalancedInvestFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.newInvestment();
            }
        });
    }

    @Override
    public void showContributionDialog() {
        final Dialog dialog = new Dialog(mRootView.getContext());
        dialog.setContentView(R.layout.contribution_dialog);
        dialog.setTitle(getString(R.string.new_contribution_title));

        final EditText contributionEdtText = (EditText)
                dialog.findViewById(R.id.contribution_edit_text);

        final Button confirmButton = (Button) dialog.findViewById(R.id.confirm_contribution_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPresenter.isContributionValid(contributionEdtText.getText().toString())) {
                    final Double contribution = NumericUtil.getValidDouble(
                            contributionEdtText.getText().toString());
                    mPresenter.calculateBalance(contribution);
                    dialog.dismiss();
                } else {
                    contributionEdtText.setError(getString(R.string.vaild_contribution));
                }
            }
        });

        dialog.show();
    }

    @Override
    public void updateList(List<Investment> investments) {
        mAdapter.setItens(investments);
        mAdapter.notifyDataSetChanged();
        updateMenuItems(investments.size());
    }

    private void updateMenuItems(int investmentListSize) {
        if (investmentListSize > 0) {
            handleItemMenu(true, R.id.action_order_by_date);
            handleItemMenu(true, R.id.action_group);
        } else {
            handleItemMenu(false, R.id.action_order_by_date);
            handleItemMenu(false, R.id.action_group);
            ;
        }
    }

    private void handleItemMenu(boolean isVisible, int menuActionId) {
        if (mMenu != null) {
            mMenu.findItem(menuActionId).setVisible(isVisible);
        }
    }

    @Override
    public void showGoalsDialog() {
        DialogUtil.getInstance(getContext()).show(getContext(), R.string.new_contribution_title,
                R.string.register_goals_message, R.string.OK,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(ConstantsUtil.NEW_INVESTMENTS_FROM_GOALS_FLOW, true);
                        final GoalListFragment goalListFragment = GoalListFragment.newInstance();
                        goalListFragment.setArguments(bundle);
                        ((BaseActivity) getActivity()).setFragment(goalListFragment, R.id.nav_goals,
                                getString(R.string.title_goals));
                    }
                }, null);
    }

    @Override
    public void setGroupByAssetType(boolean groupedByAssetType) {
        mIsGroupByAssetType = groupedByAssetType;
    }

    @Override
    public void setSortedDescByDate(boolean sortedDescByDate) {
        mIsSortedDescByDate = sortedDescByDate;
    }

}



package com.jumbomob.invistoo.ui;

import android.app.Activity;
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
import com.jumbomob.invistoo.model.entity.Investment;
import com.jumbomob.invistoo.presenter.InvestmentListPresenter;
import com.jumbomob.invistoo.ui.adapter.InvestmentListAdapter;
import com.jumbomob.invistoo.ui.component.DividerItemDecorator;
import com.jumbomob.invistoo.util.ConstantsUtil;
import com.jumbomob.invistoo.util.DialogUtil;
import com.jumbomob.invistoo.util.NumericUtil;
import com.jumbomob.invistoo.view.InvestmentListView;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 14/02/2016
 */
public class InvestmentsListFragment extends BaseFragment implements InvestmentListView {

    private View mRootView;
    private InvestmentListPresenter mPresenter;
    private InvestmentListAdapter mAdapter;
    private boolean mIsSortedDescByDate;

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
        configureRecyclerView();
        configureFab();

        return mRootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.investment_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_order_by_date:
                mPresenter.orderListByDate(mIsSortedDescByDate);
                mIsSortedDescByDate = mIsSortedDescByDate ? false : true;
                return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.my_investments);
    }

    private void configureRecyclerView() {
        LinearLayout investmentsContainer = (LinearLayout) mRootView.findViewById(R.id.no_investments_container);
        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id.investments_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecorator(getActivity(), DividerItemDecorator.VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext()));

        if (!mPresenter.findInvestments().isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            investmentsContainer.setVisibility(View.GONE);
            mAdapter = new InvestmentListAdapter(mPresenter.findInvestments(), this);
            recyclerView.setAdapter(mAdapter);
        } else {
            investmentsContainer.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void configureFab() {
        FloatingActionButton newBalancedInvestFab = (FloatingActionButton)
                mRootView.findViewById(R.id.new_balanced_investment_fab);
        newBalancedInvestFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.redirectUserNewInvestment(getContext());
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

        Button confirmButton = (Button) dialog.findViewById(R.id.confirm_contribution_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPresenter.isContributionValid(contributionEdtText.getText().toString())) {
                    final Double contribution = NumericUtil.getValidDouble(
                            contributionEdtText.getText().toString());
                    final Activity activity = getActivity();
                    final Fragment fragment = new NewInvestmentFragment();
                    Bundle bundle = new Bundle();
                    bundle.putDouble(ConstantsUtil.CONTRIBUTION_BUNDLE, contribution);
                    fragment.setArguments(bundle);
                    ((BaseActivity) activity).setFragment(fragment, activity.getString(R.string.title_new_investment));

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
    }

    @Override
    public void showGoalsDialog() {
        DialogUtil.getInstance(getContext()).show(getContext(), R.string.new_contribution_title,
                R.string.register_goals_message, R.string.OK,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        ((BaseActivity) getActivity()).setFragment(GoalsListFragment.newInstance(), R.id.nav_goals,
                                getString(R.string.title_goals));
                    }
                }, null);
    }
}



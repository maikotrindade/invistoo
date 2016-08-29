package com.jumbomob.invistoo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.dto.InvestmentSuggestion;
import com.jumbomob.invistoo.presenter.BalancedInvestmentListPresenter;
import com.jumbomob.invistoo.ui.adapter.BalancedInvestmentListAdapter;
import com.jumbomob.invistoo.ui.component.DividerItemDecorator;
import com.jumbomob.invistoo.view.BalancedInvestmentListView;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 14/07/2016
 */
public class BalancedInvestmentListFragment extends BaseFragment
        implements BalancedInvestmentListView {

    private View mRootView;
    private BalancedInvestmentListPresenter mPresenter;

    public static BalancedInvestmentListFragment newInstance() {
        BalancedInvestmentListFragment fragment = new BalancedInvestmentListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mRootView = inflater.inflate(R.layout.fragment_balanced_investment_list, container, false);
        mPresenter = new BalancedInvestmentListPresenter(this);
        configureElements();
        return mRootView;
    }

    private void configureElements() {
        final Bundle arguments = getArguments();
        final double contribution = arguments.getDouble("contribution", 0);
        final List<InvestmentSuggestion> suggestions = mPresenter.calculateBalance(contribution);
        configureRecyclerView(suggestions);
    }

    private void configureRecyclerView(final List<InvestmentSuggestion> suggestions) {
        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id
                .balanced_investments_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecorator(getActivity(), DividerItemDecorator
                .VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext()));

        final BalancedInvestmentListAdapter adapter = new
                BalancedInvestmentListAdapter(suggestions);
        recyclerView.setAdapter(adapter);
    }
}

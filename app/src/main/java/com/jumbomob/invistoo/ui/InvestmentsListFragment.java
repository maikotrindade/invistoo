package com.jumbomob.invistoo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.persistence.InvestmentDAO;
import com.jumbomob.invistoo.ui.adapter.InvestmentListAdapter;
import com.jumbomob.invistoo.ui.component.DividerItemDecorator;

/**
 * @author maiko.trindade
 * @since 14/02/2016
 */
public class InvestmentsListFragment extends BaseFragment {

    private View mRootView;
    private FloatingActionMenu menuRed;

    public static InvestmentsListFragment newInstance() {
        InvestmentsListFragment fragment = new InvestmentsListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle
            savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_investments_list, container, false);

        configureRecyclerView();
        configureFab();

        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.my_investments);
    }

    private void configureRecyclerView() {

        final InvestmentDAO dao = InvestmentDAO.getInstance();
        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id
                .investments_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecorator(getActivity(), DividerItemDecorator
                .VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext()));

        final InvestmentListAdapter adapter = new InvestmentListAdapter(dao.findAll());
        recyclerView.setAdapter(adapter);
    }

    private void configureFab() {
        menuRed = (FloatingActionMenu) mRootView.findViewById(R.id.new_investment_fab_menu);
        menuRed.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuRed.toggle(true);
            }
        });

        FloatingActionButton newInvestFab = (FloatingActionButton)
                mRootView.findViewById(R.id.new_investment_fab);
        newInvestFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Activity activity = getActivity();
                ((BaseActivity) activity).setFragment(NewInvestmentFragment.newInstance(),
                        activity.getString(R.string.title_new_investment));
            }
        });

        FloatingActionButton newBalancedInvestFab = (FloatingActionButton)
                mRootView.findViewById(R.id.new_balanced_investment_fab);
        newBalancedInvestFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Activity activity = getActivity();
                ((BaseActivity) activity).setFragment(NewBalancedInvestmentFragment.newInstance(),
                        activity.getString(R.string.title_new_investment));
            }
        });
    }
}



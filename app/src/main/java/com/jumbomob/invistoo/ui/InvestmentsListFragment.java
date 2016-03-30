package com.jumbomob.invistoo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.component.DividerItemDecorator;
import com.jumbomob.invistoo.model.persistence.dao.InvestmentDAO;
import com.jumbomob.invistoo.ui.adapter.InvestmentListAdapter;

/**
 * @author maiko.trindade
 * @since 14/02/2016
 */
public class InvestmentsListFragment extends Fragment {

    private View mRootView;

    public static InvestmentsListFragment newInstance() {
        InvestmentsListFragment fragment = new InvestmentsListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle
            savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_my_wallet, container, false);

        configureRecyclerView();
        configureFab();

        return mRootView;
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

        FloatingActionButton myFab = (FloatingActionButton) mRootView.findViewById(R.id.add_fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Activity activity = getActivity();
                ((BaseActivity) activity).setFragment(NewInvestmentFragment.newInstance(),
                        activity.getString(R
                                .string.title_new_investiment));
            }
        });

    }

}

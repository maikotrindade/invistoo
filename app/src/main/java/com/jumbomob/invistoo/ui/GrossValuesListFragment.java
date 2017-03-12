package com.jumbomob.invistoo.ui;

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

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.dto.GrossValueDTO;
import com.jumbomob.invistoo.model.dto.InvestmentSuggestionDTO;
import com.jumbomob.invistoo.presenter.GrossValuesListPresenter;
import com.jumbomob.invistoo.ui.adapter.GrossValuesListAdapter;
import com.jumbomob.invistoo.ui.component.DividerItemDecorator;
import com.jumbomob.invistoo.util.ConstantsUtil;
import com.jumbomob.invistoo.view.GrossValuesListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maiko.trindade
 * @since 14/07/2016
 */
public class GrossValuesListFragment extends BaseFragment implements GrossValuesListView {

    private View mRootView;
    private GrossValuesListPresenter mPresenter;
    private GrossValuesListAdapter mAdapter;
    private double mContribution;

    public static GrossValuesListFragment newInstance() {
        GrossValuesListFragment fragment = new GrossValuesListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mRootView = inflater.inflate(R.layout.fragment_gross_values_list, container, false);
        mPresenter = new GrossValuesListPresenter(this);
        configureElements();
        final Bundle arguments = getArguments();
        mContribution = arguments.getDouble(ConstantsUtil.CONTRIBUTION_BUNDLE, 0);
        return mRootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.new_investment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                //TODO calular aqui
                final List<InvestmentSuggestionDTO> suggestions = mPresenter.calculateBalance(mContribution, mAdapter.getItens());
                final Fragment fragment = new NewInvestmentFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(ConstantsUtil.SUGGESTIONS_BUNDLE, (ArrayList) suggestions);
                fragment.setArguments(bundle);
                ((BaseActivity) getActivity()).setFragment(fragment, getActivity().getString(R.string.title_new_investment));
                break;
        }
        return false;
    }

    private void configureElements() {
        configureRecyclerView();
    }

    private void configureRecyclerView() {
        final List<GrossValueDTO> grossValues = mPresenter.getGrossValues();

        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id
                .gross_values_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecorator(getActivity(), DividerItemDecorator
                .VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext()));

        mAdapter = new GrossValuesListAdapter(grossValues);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showMessage(final int resourceId) {
        super.showMessage(resourceId);
    }

    @Override
    public void navigateToInvestmentList() {
        ((BaseActivity) getActivity()).goBackFragment();
    }
}

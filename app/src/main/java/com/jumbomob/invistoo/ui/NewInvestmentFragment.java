package com.jumbomob.invistoo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.dto.InvestmentSuggestionDTO;
import com.jumbomob.invistoo.presenter.NewInvestmentListPresenter;
import com.jumbomob.invistoo.ui.adapter.BalancedInvestmentListAdapter;
import com.jumbomob.invistoo.ui.component.DividerItemDecorator;
import com.jumbomob.invistoo.util.ConstantsUtil;
import com.jumbomob.invistoo.view.NewInvestmentListView;

import java.util.List;

import static com.jumbomob.invistoo.util.ConstantsUtil.GROSS_VALUES_NEW_INVESTMENT_FLOW;

/**
 * @author maiko.trindade
 * @since 14/07/2016
 */
public class NewInvestmentFragment extends BaseFragment implements NewInvestmentListView {

    private View mRootView;
    private NewInvestmentListPresenter mPresenter;
    private BalancedInvestmentListAdapter mAdapter;

    public static NewInvestmentFragment newInstance() {
        NewInvestmentFragment fragment = new NewInvestmentFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mRootView = inflater.inflate(R.layout.fragment_balanced_investment_list, container, false);
        mPresenter = new NewInvestmentListPresenter(this);
        configureElements();
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
                mPresenter.saveInvestments(mAdapter.getItens());
                break;
        }
        return false;
    }

    private void configureElements() {
        final Bundle arguments = getArguments();
        final boolean isGrossValuesFlow = arguments.getBoolean(GROSS_VALUES_NEW_INVESTMENT_FLOW, false);

        List<InvestmentSuggestionDTO> suggestions;
        if (isGrossValuesFlow) {
            suggestions = arguments.getParcelableArrayList(ConstantsUtil.SUGGESTIONS_BUNDLE);
            configureRecyclerView(suggestions);
        } else {
            final double contribution = arguments.getDouble(ConstantsUtil.CONTRIBUTION_BUNDLE, 0);
            if (mPresenter.areDownloadedAssets()) {
                suggestions = mPresenter.calculateBalance(contribution);
                configureRecyclerView(suggestions);
            } else {
                mPresenter.downloadAssets(contribution);
            }
        }
    }

    @Override
    public void configureRecyclerView(final List<InvestmentSuggestionDTO> suggestions) {
        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id
                .balanced_investments_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecorator(getActivity(), DividerItemDecorator
                .VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext()));

        mAdapter = new BalancedInvestmentListAdapter(suggestions);
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

    @Override
    public void showProgressDialog(final int resourceId) {
        super.showProgressDialog(resourceId);
    }

    @Override
    public void hideProgressDialog() {
        super.hideProgressDialog();
    }

    @Override
    public void onDownloadAssetsError() {
        hideProgressDialog();
        showMessage(R.string.error_general);
    }
}

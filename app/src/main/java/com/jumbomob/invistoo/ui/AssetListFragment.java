package com.jumbomob.invistoo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Asset;
import com.jumbomob.invistoo.presenter.AssetListPresenter;
import com.jumbomob.invistoo.ui.adapter.AssetListAdapter;
import com.jumbomob.invistoo.util.InvistooUtil;
import com.jumbomob.invistoo.view.AssetListView;

import java.util.List;

public class AssetListFragment extends BaseFragment implements AssetListView {

    private View mRootView;
    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private AssetListAdapter mAdapter;
    private SwipeRefreshLayout mSwipeLayout;
    private AssetListPresenter mPresenter;
    private BaseActivity mBaseActivity;
    private boolean mIsShowingBuyable;
    private Menu mMenu;

    public static AssetListFragment newInstance() {
        AssetListFragment fragment = new AssetListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mIsShowingBuyable = true;
        mBaseActivity = (BaseActivity) getActivity();
        mRootView = inflater.inflate(R.layout.fragment_asset_list, container, false);
        mPresenter = new AssetListPresenter(this);
        configureSwipe();
        configureRecyclerView();
        return mRootView;
    }

    private void configureRecyclerView() {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.assets_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext()));
        mPresenter.downloadAssets(mIsShowingBuyable);
    }

    @Override
    public void onResume() {
        super.onResume();
        setLastUpdateTitle();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBaseActivity.setDefaultToolbar();
    }

    @Override
    public void showProgressDialog(final int resourceId) {
        super.showProgressDialog(resourceId);
    }

    @Override
    public void hideProgressDialog() {
        super.hideProgressDialog();
    }

    private void configureSwipe() {
        mSwipeLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.content_swipe_layout);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeLayout.setRefreshing(false);
                mPresenter.downloadAssets(mIsShowingBuyable);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        mMenu = menu;
        inflater.inflate(R.menu.search_asset_list_menu, menu);
        bindSearchView(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_show_hide_buyable:
                mPresenter.showHideAssets(mIsShowingBuyable);
                return true;
        }
        return false;
    }

    @Override
    public void onDownloadError() {
        hideProgressDialog();
        List<Asset> lastFromDatabase = mPresenter.getAssetsFromDatabase();
        if (!lastFromDatabase.isEmpty()) {
            updateAssetList(mPresenter.filterAssetsByBuyable(lastFromDatabase, true));
            setLastUpdateTitle();
        }
        InvistooUtil.makeSnackBar(getActivity(), getActivity()
                .getString(R.string.error_download_assets), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDownloadSuccess() {
        hideProgressDialog();
        setLastUpdateTitle();
        InvistooUtil.makeSnackBar(getActivity(), getString(R.string.msg_asset_updated_success),
                Snackbar.LENGTH_LONG).show();
    }

    private void bindSearchView(final Menu menu) {
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setIconifiedByDefault(true);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.searchByText(mAdapter, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mPresenter.searchByText(mAdapter, newText);
                return false;
            }
        });
    }


    @Override
    public void updateAssetList(final List<Asset> assets) {
        if (mAdapter == null) {
            mAdapter = new AssetListAdapter(getActivity(), assets);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(assets);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setLastUpdateTitle() {
        mBaseActivity.setCustomToolbar(R.string.nav_indexes, mPresenter.getLastUpdate());
    }

    @Override
    public void setShowingBuyable(boolean mIsShowingBuyable) {
        this.mIsShowingBuyable = mIsShowingBuyable;
    }

    @Override
    public void changeMenuIcon(boolean isShowingBuyable) {
        int iconResourceId;
        if (isShowingBuyable) {
            iconResourceId = R.drawable.ic_action_attach_money;
        } else {
            iconResourceId = R.drawable.ic_action_money_off;
        }
        mMenu.findItem(R.id.action_show_hide_buyable).setIcon(getResources().getDrawable(iconResourceId));
    }
}
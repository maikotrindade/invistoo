package com.jumbomob.invistoo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Asset;
import com.jumbomob.invistoo.presenter.AssetListPresenter;
import com.jumbomob.invistoo.ui.adapter.AssetListAdapter;
import com.jumbomob.invistoo.ui.component.DividerItemDecorator;
import com.jumbomob.invistoo.util.InvistooUtil;
import com.jumbomob.invistoo.view.AssetListView;

import java.util.List;

public class AssetListFragment extends Fragment implements AssetListView {

    private View mRootView;
    private SearchView mSearchView;
    private AssetListAdapter mAdapter;
    private SwipeRefreshLayout mSwipeLayout;
    private AssetListPresenter mPresenter;

    public static AssetListFragment newInstance() {
        AssetListFragment fragment = new AssetListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mRootView = inflater.inflate(R.layout.fragment_asset_list, container, false);
        mPresenter = new AssetListPresenter();
        configureRecyclerView();
        configureSwipe();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.title_indexes);
    }

    private void configureRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id
                .assets_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecorator(getActivity(), DividerItemDecorator
                .VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext()));

        final List<Asset> assetList = mPresenter.getAssets();
        mAdapter = new AssetListAdapter(getActivity(), assetList);
        recyclerView.setAdapter(mAdapter);

        if (assetList.isEmpty()) {
            mPresenter.downloadAssets();
        }
    }

    private void configureSwipe() {
        mSwipeLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.content_swipe_layout);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.downloadAssets();
                mSwipeLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.search_asset_list_menu, menu);
        bindSearchView(menu);
    }

    @Override
    public void onDownloadError() {
        InvistooUtil.makeSnackBar(getActivity(), getActivity()
                .getString(R.string.error_download_assets), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDownloadSuccess() {
        InvistooUtil.makeSnackBar(getActivity(), getString(R.string.msg_asset_updated_success),
                Snackbar.LENGTH_LONG).show();
    }

    private void bindSearchView(final Menu menu) {
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setIconifiedByDefault(true);

        //TODO
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("onQueryTextSubmit ", "Query: " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("onQueryTextChange ", "Query: " + newText);
                return false;
            }
        });
    }

    @Override
    public void updateAssetList(final List<Asset> assets) {
        mAdapter.setItens(assets);
        mAdapter.notifyDataSetChanged();
    }

}
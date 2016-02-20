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
import com.jumbomob.invistoo.component.DividerItemDecorator;
import com.jumbomob.invistoo.model.entity.Asset;
import com.jumbomob.invistoo.model.persistence.dao.AssetDAO;
import com.jumbomob.invistoo.model.webservice.AssetInterface;
import com.jumbomob.invistoo.model.webservice.BaseServiceConfiguration;
import com.jumbomob.invistoo.ui.adapter.AssetListAdapter;
import com.jumbomob.invistoo.util.InvistooUtil;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class AssetListFragment extends Fragment {

    private static final String TAG = AssetListFragment.class.getSimpleName();

    private View mRootView;
    private SearchView mSearchView;
    private AssetListAdapter mAdapter;
    private SwipeRefreshLayout mSwipelayout;

    public static AssetListFragment newInstance() {
        AssetListFragment fragment = new AssetListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mRootView = inflater.inflate(R.layout.fragment_asset_list, container, false);

        configureRecyclerView();
        configureSwipe();
        return mRootView;
    }

    private void configureRecyclerView() {

        final AssetDAO assetDAO = AssetDAO.getInstance();
        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id.assets_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecorator(getActivity(), DividerItemDecorator
                .VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext()));

        mAdapter = new AssetListAdapter(assetDAO.findLast());
        recyclerView.setAdapter(mAdapter);
    }

    private void configureSwipe() {
        mSwipelayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.content_swipe_layout);
        mSwipelayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downloadAssets();
                mSwipelayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.search_asset_list_menu, menu);
        bindSearchView(menu);
    }

    private void downloadAssets() {
        OkHttpClient okClient = new OkHttpClient();
        okClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                return response;
            }
        });

        Retrofit client = new Retrofit.Builder()
                .baseUrl(BaseServiceConfiguration.BASE_URL)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AssetInterface service = client.create(AssetInterface.class);
        Call<List<Asset>> call = service.getAssets();
        call.enqueue(new Callback<List<Asset>>() {
            @Override
            public void onResponse(retrofit.Response<List<Asset>> response) {
                if (response.isSuccess()) {
                    final AssetDAO assetDAO = AssetDAO.getInstance();
                    List<Asset> assetsResult = response.body();
                    for (Asset asset : assetsResult) {
                        assetDAO.insert(asset);
                    }
                } else {
                    onDownloadError();
                    Log.e(TAG, response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                onDownloadError();
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
    }

    private void onDownloadError() {
        InvistooUtil.makeSnackBar(mRootView, getActivity().getString(R.string.error_download_assets), Snackbar
                .LENGTH_LONG).show();
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


}
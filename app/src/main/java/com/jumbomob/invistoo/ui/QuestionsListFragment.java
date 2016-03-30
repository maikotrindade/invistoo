package com.jumbomob.invistoo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.component.DividerItemDecorator;
import com.jumbomob.invistoo.model.entity.Asset;
import com.jumbomob.invistoo.model.network.AssetInterface;
import com.jumbomob.invistoo.model.network.BaseNetworkConfig;
import com.jumbomob.invistoo.model.persistence.dao.AssetDAO;
import com.jumbomob.invistoo.util.InvistooUtil;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;

/**
 * @author maiko.trindade
 * @since 28/03/2016
 */
public class QuestionsListFragment extends Fragment {

    private static final String TAG = QuestionsListFragment.class.getSimpleName();

    private View mRootView;
    private SwipeRefreshLayout mSwipelayout;

    public static QuestionsListFragment newInstance() {
        QuestionsListFragment fragment = new QuestionsListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_questions_list, container, false);

        configureRecyclerView();
        configureSwipe();
        return mRootView;
    }

    private void configureRecyclerView() {

        //final InvestmentDAO dao = InvestmentDAO.getInstance();
        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id
                .questions_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecorator(getActivity(), DividerItemDecorator
                .VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mRootView.getContext()));

//        InvestmentListAdapter mAdapter = new InvestmentListAdapter(dao.findAll());
//        recyclerView.setAdapter(mAdapter);
    }

    private void configureSwipe() {
        mSwipelayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.question_swipe_layout);
        mSwipelayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downloadQuestions();
                mSwipelayout.setRefreshing(false);
            }
        });
    }

    private void downloadQuestions() {
        final AssetInterface service = BaseNetworkConfig.createService(AssetInterface.class,
                BaseNetworkConfig.BASE_URL);

        final Call<List<Asset>> call = service.getAssets();
        call.enqueue(new Callback<List<Asset>>() {
            @Override
            public void onResponse(retrofit.Response<List<Asset>> response, Retrofit retrofit) {
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
        InvistooUtil.makeSnackBar(mRootView, getActivity().getString(R.string
                .error_download_assets), Snackbar
                .LENGTH_LONG).show();
    }


}

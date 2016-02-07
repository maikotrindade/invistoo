package com.jumbomob.invistoo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Asset;
import com.jumbomob.invistoo.model.webservice.AssetInterface;
import com.jumbomob.invistoo.model.webservice.BaseServiceConfiguration;
import com.jumbomob.invistoo.ui.adapter.AssetListAdapter;
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

    public static AssetListFragment newInstance() {
        AssetListFragment fragment = new AssetListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View rootView = inflater.inflate(R.layout.activity_asset_list, container, false);

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
                    // request successful (status code 200, 201)
                    List<Asset> result = response.body();

                    RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.assets_recycler_view);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
                    AssetListAdapter adapter = new AssetListAdapter(result);
                    recyclerView.setAdapter(adapter);

                } else {
                    Toast.makeText(rootView.getContext(), "" + response.raw().message(), Toast.LENGTH_SHORT).show();
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("retrofit-invistoo", t.getMessage());
                Log.e("retrofit-invistoo", t.getLocalizedMessage());
                Log.e("retrofit-invistoo", t.toString());
            }
        });

        return rootView;
    }
}
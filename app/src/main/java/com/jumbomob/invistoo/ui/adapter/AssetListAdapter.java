package com.jumbomob.invistoo.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Asset;
import com.jumbomob.invistoo.ui.AssetChartFragment;
import com.jumbomob.invistoo.ui.BaseActivity;
import com.jumbomob.invistoo.ui.callback.onSearchResultListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maiko.trindade
 * @since 06/02/2016
 */
public class AssetListAdapter extends RecyclerView.Adapter<AssetListAdapter.ViewHolder> implements
        onSearchResultListener {

    private List<Asset> mAssets;
    private int mPosition;
    private Activity mActivity;
    private onSearchResultListener mSearchListener;

    public AssetListAdapter(Activity activity, List<Asset> assets) {
        mActivity = activity;
        mAssets = assets;
    }

    public void setItens(List<Asset> assets) {
        this.mAssets = assets;
    }

    public Asset getSelectedItem() {
        return mAssets.get(mPosition);
    }

    public List<Asset> filterByName(final String name) {
        List<Asset> filteredAssets = new ArrayList<>();
        final String filter = name.toLowerCase();
        Log.e("Adapter", "Texto para filtro: " + name);
        Log.e("Adapter", "O adapter tinha #: " + mAssets.size());
        for (Asset asset : mAssets) {
            final String assetName = asset.getName().toString();
            if ((assetName).contains(filter)) {
                filteredAssets.add(asset);
            }
        }
        Log.e("Adapter", "O filtro tem #: " + filteredAssets.size());
        return filteredAssets;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.item_asset_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Asset asset = mAssets.get(position);
        holder.titleTxtView.setText(asset.getTitle());
        holder.dueDateTxtView.setText(asset.getDueDate());

        final String buyPrice = asset.getBuyPrice();
        if (TextUtils.isEmpty(buyPrice) || buyPrice.equals(" ")) {
            holder.buyPriceTxtView.setText("-");
        } else {
            holder.sellPriceTxtView.setText("R$" + buyPrice);
        }

        final String sellPrice = asset.getSellPrice();
        if (TextUtils.isEmpty(sellPrice) || sellPrice.equals(" ")) {
            holder.sellPriceTxtView.setText("-");
        } else {
            holder.sellPriceTxtView.setText("R$" + sellPrice);
        }

        holder.containerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity) mActivity).setFragment(AssetChartFragment.newInstance(), "Chart");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAssets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout containerView;
        private TextView titleTxtView;
        private TextView dueDateTxtView;
        private TextView buyPriceTxtView;
        private TextView sellPriceTxtView;

        public ViewHolder(View view) {
            super(view);
            containerView = (LinearLayout) view.findViewById(R.id.container_view);
            titleTxtView = (TextView) view.findViewById(R.id.title_text_view);
            dueDateTxtView = (TextView) view.findViewById(R.id.due_date_text_view);
            buyPriceTxtView = (TextView) view.findViewById(R.id.buy_price_text_view);
            sellPriceTxtView = (TextView) view.findViewById(R.id.sell_price_text_view);
        }
    }

    @Override
    public void updateSearchResult() {
        if (mSearchListener != null) {
            mSearchListener.updateSearchResult();
        }
    }

    public void setSearchListener(onSearchResultListener listener) {
        mSearchListener = listener;
    }
}

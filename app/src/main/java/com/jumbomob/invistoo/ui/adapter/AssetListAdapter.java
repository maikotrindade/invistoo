package com.jumbomob.invistoo.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Asset;
import com.jumbomob.invistoo.ui.callback.onSearchResultListener;
import com.jumbomob.invistoo.util.NumericUtil;

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

    public void setItems(List<Asset> assets) {
        this.mAssets = assets;
    }

    public List<Asset> getAssets() {
        return mAssets;
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
            holder.buyPriceTxtView.setVisibility(View.GONE);
            holder.buyPriceLabel.setVisibility(View.GONE);
        } else {
            holder.buyPriceTxtView.setVisibility(View.VISIBLE);
            holder.buyPriceLabel.setVisibility(View.VISIBLE);
            holder.buyPriceTxtView.setText(NumericUtil.formatCurrency(NumericUtil.getValidDouble((buyPrice))));
        }

        final String sellPrice = asset.getSellPrice();
        if (TextUtils.isEmpty(sellPrice) || sellPrice.equals(" ")) {
            holder.sellPriceLabel.setVisibility(View.GONE);
            holder.sellPriceTxtView.setVisibility(View.GONE);
        } else {
            holder.sellPriceLabel.setVisibility(View.VISIBLE);
            holder.sellPriceTxtView.setVisibility(View.VISIBLE);
            holder.sellPriceTxtView.setText(NumericUtil.formatCurrency(NumericUtil.getValidDouble((sellPrice))));
        }
    }

    @Override
    public int getItemCount() {
        return mAssets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTxtView;
        private TextView dueDateTxtView;
        private TextView buyPriceLabel;
        private TextView buyPriceTxtView;
        private TextView sellPriceLabel;
        private TextView sellPriceTxtView;

        public ViewHolder(View view) {
            super(view);
            titleTxtView = (TextView) view.findViewById(R.id.title_text_view);
            dueDateTxtView = (TextView) view.findViewById(R.id.due_date_text_view);
            buyPriceLabel = (TextView) view.findViewById(R.id.buy_price_label);
            buyPriceTxtView = (TextView) view.findViewById(R.id.buy_price_text_view);
            sellPriceLabel = (TextView) view.findViewById(R.id.sell_price_label);
            sellPriceTxtView = (TextView) view.findViewById(R.id.sell_price_text_view);
        }
    }

    @Override
    public void updateSearchResult() {
        if (mSearchListener != null) {
            mSearchListener.updateSearchResult();
        }
    }

}

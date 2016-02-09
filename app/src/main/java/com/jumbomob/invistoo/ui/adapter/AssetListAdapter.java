package com.jumbomob.invistoo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Asset;
import com.jumbomob.invistoo.ui.callback.onSearchResultListener;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 06/02/2016
 */
public class AssetListAdapter extends RecyclerView.Adapter<AssetListAdapter.ViewHolder> implements
        onSearchResultListener {

    private List<Asset> mAssets;
    private int mPosition;
    private onSearchResultListener mSearchListener;

    public AssetListAdapter(List<Asset> assets) {
        mAssets = assets;
    }

    public void setItens(List<Asset> assets) {
        this.mAssets = assets;
    }

    public Asset getSelectedItem() {
        return mAssets.get(mPosition);
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
        holder.titleTxtView.setText(asset.getName());
        holder.dueDateTxtView.setText(asset.getDueDate());
        holder.buyTaxTxtView.setText(asset.getBuyTax());
        holder.sellTaxTxtView.setText(asset.getSellTax());
    }

    @Override
    public int getItemCount() {
        return mAssets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTxtView;
        private TextView dueDateTxtView;
        private TextView buyTaxTxtView;
        private TextView sellTaxTxtView;

        public ViewHolder(View view) {
            super(view);
            titleTxtView = (TextView) view.findViewById(R.id.title_text_view);
            dueDateTxtView = (TextView) view.findViewById(R.id.due_date_text_view);
            buyTaxTxtView = (TextView) view.findViewById(R.id.buy_tax_text_view);
            sellTaxTxtView = (TextView) view.findViewById(R.id.sell_tax_text_view);
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

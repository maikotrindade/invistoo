package com.jumbomob.invistoo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.AssetTypeEnum;
import com.jumbomob.invistoo.model.entity.Balance;
import com.jumbomob.invistoo.ui.callback.BalanceAssetCallback;
import com.jumbomob.invistoo.util.NumericUtil;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 14/08/2017
 */
public class BalanceAssetAdapter extends RecyclerView.Adapter<BalanceAssetAdapter.ViewHolder> {

    private List<Balance> mItems;
    private BalanceAssetCallback mCallback;

    public BalanceAssetAdapter(List<Balance> balances, BalanceAssetCallback callback) {
        mItems = balances;
        mCallback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.item_balance_asset_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Balance balance = mItems.get(position);
        final AssetTypeEnum assetType = AssetTypeEnum.getById(balance.getAsset());
        holder.assetTitle.setText(assetType.getTitle());
        holder.assetTotal.setText(NumericUtil.formatCurrency(balance.getTotal()));

        holder.editContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onEditBalance(balance.getAsset(), balance.getTotal(), holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView assetTitle;
        private TextView assetTotal;
        private LinearLayout editContainer;

        public ViewHolder(View view) {
            super(view);
            assetTitle = (TextView) view.findViewById(R.id.asset_title);
            assetTotal = (TextView) view.findViewById(R.id.asset_total);
            editContainer = (LinearLayout) view.findViewById(R.id.edit_container);
        }
    }

}

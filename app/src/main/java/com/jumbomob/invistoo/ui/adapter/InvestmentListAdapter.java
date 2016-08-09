package com.jumbomob.invistoo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Investment;
import com.jumbomob.invistoo.ui.callback.onSearchResultListener;
import com.jumbomob.invistoo.util.NumericUtil;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 24/02/2016
 */
public class InvestmentListAdapter extends RecyclerView.Adapter<InvestmentListAdapter.ViewHolder> {

    private List<Investment> mInvestments;
    private int mPosition;
    private onSearchResultListener mSearchListener;

    public InvestmentListAdapter(List<Investment> investments) {
        mInvestments = investments;
    }

    public void setItens(List<Investment> investments) {
        this.mInvestments = investments;
    }

    public Investment getSelectedItem() {
        return mInvestments.get(mPosition);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.item_investment_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Investment investment = mInvestments.get(position);
        holder.nameTxtView.setText(investment.getName());
        holder.quantityTxtView.setText(NumericUtil.formatDouble(investment.getQuantity()));
        holder.priceTxtView
                .setText("R$" + NumericUtil.formatDouble(Double.valueOf(investment.getPrice())));
        holder.lastUpdateTxtView.setText(investment.getUpdateDate());
    }

    @Override
    public int getItemCount() {
        return mInvestments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTxtView;
        private TextView quantityTxtView;
        private TextView priceTxtView;
        private TextView lastUpdateTxtView;

        public ViewHolder(View view) {
            super(view);
            nameTxtView = (TextView) view.findViewById(R.id.name_text_view);
            quantityTxtView = (TextView) view.findViewById(R.id.quantity_text_view);
            priceTxtView = (TextView) view.findViewById(R.id.price_text_view);
            lastUpdateTxtView = (TextView) view.findViewById(R.id.last_update_text_view);
        }
    }

}
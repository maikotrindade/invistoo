package com.jumbomob.invistoo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.dto.InvestmentSuggestionDTO;
import com.jumbomob.invistoo.model.entity.AssetTypeEnum;
import com.jumbomob.invistoo.ui.callback.onSearchResultListener;
import com.jumbomob.invistoo.util.NumericUtil;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 24/02/2016
 */
public class BalancedInvestmentListAdapter extends RecyclerView
        .Adapter<BalancedInvestmentListAdapter.ViewHolder> {

    private List<InvestmentSuggestionDTO> mInvestments;
    private int mPosition;
    private onSearchResultListener mSearchListener;

    public BalancedInvestmentListAdapter(List<InvestmentSuggestionDTO> investments) {
        mInvestments = investments;
    }

    public void setItens(List<InvestmentSuggestionDTO> investments) {
        this.mInvestments = investments;
    }

    public List<InvestmentSuggestionDTO> getItens() {
        return mInvestments;
    }

    public InvestmentSuggestionDTO getSelectedItem() {
        return mInvestments.get(mPosition);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.item_balanced_investment_list, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final InvestmentSuggestionDTO suggestion = mInvestments.get(position);
        AssetTypeEnum assetTypeEnum = AssetTypeEnum.getById(suggestion.getAssetType());
        holder.nameTxtView.setText(assetTypeEnum.getTitle());
        holder.investedTxtView.setText(NumericUtil.formatCurrency(suggestion.getTotal()));
        holder.suggestionEdtTxt.setText(NumericUtil.formatDouble(Double.valueOf(suggestion.getSuggestion())));
    }

    @Override
    public int getItemCount() {
        return mInvestments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTxtView;
        private TextView investedTxtView;
        private EditText suggestionEdtTxt;

        public ViewHolder(View view) {
            super(view);
            nameTxtView = (TextView) view.findViewById(R.id.name_text_view);
            investedTxtView = (TextView) view.findViewById(R.id.invested_text_view);
            suggestionEdtTxt = (EditText) view.findViewById(R.id.suggestion_edit_text);
        }
    }

}
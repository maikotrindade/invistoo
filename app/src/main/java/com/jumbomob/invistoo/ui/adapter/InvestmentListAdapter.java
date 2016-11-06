package com.jumbomob.invistoo.ui.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.AssetTypeEnum;
import com.jumbomob.invistoo.model.entity.Investment;
import com.jumbomob.invistoo.ui.callback.onSearchResultListener;
import com.jumbomob.invistoo.util.DateUtil;
import com.jumbomob.invistoo.util.NumericUtil;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 24/02/2016
 */
public class InvestmentListAdapter extends RecyclerView.Adapter<InvestmentListAdapter.ViewHolder> {

    private List<Investment> mInvestments;
    private int mPosition;
    private Context mContext;
    private onSearchResultListener mSearchListener;

    public InvestmentListAdapter(List<Investment> investments, Context context) {
        mInvestments = investments;
        mContext = context;
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
        holder.valueTxtView.setText("R$" + NumericUtil.formatDouble(Double.valueOf(investment.getPrice())));
        holder.lastUpdateTxtView.setText(DateUtil.formatDate(investment.getUpdateDate()));

        final AssetTypeEnum typeEnum = AssetTypeEnum.getById(investment.getAssetType());

        Drawable background = holder.circleContainer.getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable) background).getPaint().setColor(ContextCompat.getColor(mContext, typeEnum.getColorResourceId()));
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable) background).setColor(ContextCompat.getColor(mContext, typeEnum.getColorResourceId()));
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable) background).setColor(ContextCompat.getColor(mContext, typeEnum.getColorResourceId()));
        }

        holder.abbreviationTxtView.setText(typeEnum.getAbbreviation());
        holder.yearTxtView.setText(String.valueOf(typeEnum.getYear()));
    }

    @Override
    public int getItemCount() {
        return mInvestments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout circleContainer;
        private TextView abbreviationTxtView;
        private TextView yearTxtView;
        private TextView nameTxtView;
        private TextView valueTxtView;
        private TextView lastUpdateTxtView;

        public ViewHolder(View view) {
            super(view);
            circleContainer = (LinearLayout) view.findViewById(R.id.circle_container);
            abbreviationTxtView = (TextView) view.findViewById(R.id.abbreviation_text_view);
            yearTxtView = (TextView) view.findViewById(R.id.year_text_view);
            nameTxtView = (TextView) view.findViewById(R.id.name_text_view);
            valueTxtView = (TextView) view.findViewById(R.id.value_text_view);
            lastUpdateTxtView = (TextView) view.findViewById(R.id.last_update_text_view);
        }
    }

}
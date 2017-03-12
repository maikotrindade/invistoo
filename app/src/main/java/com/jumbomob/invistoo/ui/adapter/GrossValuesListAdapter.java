package com.jumbomob.invistoo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.dto.GrossValueDTO;
import com.jumbomob.invistoo.model.entity.AssetTypeEnum;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 24/02/2016
 */
public class GrossValuesListAdapter extends RecyclerView.Adapter<GrossValuesListAdapter.ViewHolder> {

    private List<GrossValueDTO> mGrossValues;
    private int mPosition;

    public GrossValuesListAdapter(List<GrossValueDTO> grossValues) {
        mGrossValues = grossValues;
    }

    public void setItens(List<GrossValueDTO> grossValues) {
        this.mGrossValues = grossValues;
    }

    public List<GrossValueDTO> getItens() {
        return mGrossValues;
    }

    public GrossValueDTO getSelectedItem() {
        return mGrossValues.get(mPosition);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.item_gross_value_list, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final GrossValueDTO grossValue = mGrossValues.get(position);
        AssetTypeEnum assetTypeEnum = AssetTypeEnum.getById(grossValue.getAssetType());
        holder.assetNameTxtView.setText(assetTypeEnum.getTitle());

        holder.grossValueEdtTxt.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence textChanged, int start, int before, int
                    count) {
                final String amount = textChanged.toString();
                if (!TextUtils.isEmpty(amount)) {
                    grossValue.setAmount(Double.valueOf(amount));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGrossValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView assetNameTxtView;
        private EditText grossValueEdtTxt;

        public ViewHolder(View view) {
            super(view);
            assetNameTxtView = (TextView) view.findViewById(R.id.asset_title);
            grossValueEdtTxt = (EditText) view.findViewById(R.id.asset_gross_value);
        }
    }

}
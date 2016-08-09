package com.jumbomob.invistoo.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jumbomob.invistoo.model.entity.AssetTypeEnum;

/**
 * @author maiko.trindade
 * @since 24/07/2016
 */

public class SpinnerAssetAdapter extends ArrayAdapter<AssetTypeEnum> {

    private Context context;
    private AssetTypeEnum[] values;

    public SpinnerAssetAdapter(Context context, int textViewResourceId, AssetTypeEnum[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount() {
        return values.length;
    }

    public AssetTypeEnum getItem(int position) {
        return values[position];
    }

    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values[position].getTitle());
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                .WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(250, 0, 0, 0);
        label.setLayoutParams(llp);
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values[position].getTitle());
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                .WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(250, 0, 0, 0);
        label.setLayoutParams(llp);
        return label;
    }
}
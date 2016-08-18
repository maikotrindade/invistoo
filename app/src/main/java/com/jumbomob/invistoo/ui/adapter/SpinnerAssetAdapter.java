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

    private Context mContext;
    private AssetTypeEnum[] mValues;

    public SpinnerAssetAdapter(Context context, int textViewResourceId, AssetTypeEnum[] values) {
        super(context, textViewResourceId, values);
        this.mContext = context;
        this.mValues = values;
    }

    public int getCount() {
        return mValues.length;
    }

    public AssetTypeEnum getItem(int position) {
        return mValues[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public int getPosition(long itemId) {
        for (int index = 0; index < mValues.length; index++) {
            if (mValues[index].getId() == itemId) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TextView label = new TextView(mContext);
        label.setTextColor(Color.BLACK);
        label.setText(mValues[position].getTitle());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 0, 0, 0);
        label.setLayoutParams(layoutParams);
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(mContext);
        label.setTextColor(Color.BLACK);
        label.setText(mValues[position].getTitle());
        label.setPaddingRelative(12, 6, 0, 6);
        return label;
    }
}
package com.jumbomob.invistoo.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
        // I created a dynamic TextView here, but you can reference your own  custom layout for
        // each spinner item
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current
        // position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(values[position].getTitle());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values[position].getTitle());

        return label;
    }
}
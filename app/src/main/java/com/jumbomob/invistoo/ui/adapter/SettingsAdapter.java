package com.jumbomob.invistoo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.ui.callback.onSearchResultListener;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 22/07/2016
 */
public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    private List<String> mSettings;
    private int mPosition;
    private onSearchResultListener mSearchListener;

    public SettingsAdapter(List<String> settings) {
        mSettings = settings;
    }

    public void setItens(List<String> settings) {
        this.mSettings = settings;
    }

    public String getSelectedItem() {
        return mSettings.get(mPosition);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.item_settings_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String settings = mSettings.get(position);
        holder.nameTxtView.setText(settings);
    }

    @Override
    public int getItemCount() {
        return mSettings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTxtView;

        public ViewHolder(View view) {
            super(view);
            nameTxtView = (TextView) view.findViewById(R.id.name_text_view);
        }
    }
}
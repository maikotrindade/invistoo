package com.jumbomob.invistoo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.AssetTypeEnum;
import com.jumbomob.invistoo.model.entity.Goal;
import com.jumbomob.invistoo.ui.callback.onSearchResultListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author maiko.trindade
 * @since 22/07/2016
 */
public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    private Context mContext;
    private List<Goal> mGoals;
    private List<AssetTypeEnum> mAssetsTypeEnum;
    private int mPosition;
    private onSearchResultListener mSearchListener;

    public SettingsAdapter(Context context, List<Goal> goals) {
        mContext = context;
        mGoals = goals;
        mAssetsTypeEnum = new ArrayList<>();
        mAssetsTypeEnum = Arrays.asList(AssetTypeEnum.values());
    }

    public void setItens(List<Goal> goals) {
        this.mGoals = goals;
    }

    public List<Goal> getItens() {
        return mGoals;
    }

    public Goal getSelectedItem() {
        return mGoals.get(mPosition);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.item_settings_list, parent, false);
        return new ViewHolder(view);
    }

    public void remove(int position) {
        mGoals.remove(position);
        notifyItemRemoved(position);
    }

    public void add(int position) {
//        for (AssetTypeEnum enumType : mAssetsTypeEnum) {
//            if (!mGoals.contains(enumType)) {
//                mGoals.add(enumType);
//                notifyItemInserted(position);
//                break;
//            }
//        }
        mGoals.add(new Goal());
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return mGoals.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Goal goal = mGoals.get(position);

        configureSpinner(holder);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private EditText quantityTxtView;
        private Spinner assetSpinner;

        public ViewHolder(View view) {
            super(view);
            quantityTxtView = (EditText) view.findViewById(R.id.percentage_edit_text);
            assetSpinner = (Spinner) view.findViewById(R.id.assets_spinner);
        }
    }

    private void configureSpinner(final ViewHolder holder) {

        final SpinnerAssetAdapter dataAdapter = new SpinnerAssetAdapter
                (mContext, android.R.layout.simple_spinner_item, AssetTypeEnum.values());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.assetSpinner.setAdapter(dataAdapter);
        holder.assetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

//    @NonNull
//    private AssetTypeEnum[] getAssetsFiltered() {
//        List<AssetTypeEnum> filtered = new ArrayList<>(mAssetsTypeEnum);
//        for (AssetTypeEnum assetSelected : mGoals) {
//            for (Iterator<AssetTypeEnum> iterator = filtered.iterator(); iterator.hasNext(); ) {
//                AssetTypeEnum asset = iterator.next();
//                if (asset.equals(assetSelected)) {
//                    iterator.remove();
//                }
//            }
//        }
//        return filtered.toArray(new AssetTypeEnum[filtered.size()]);
//    }
}
package com.jumbomob.invistoo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.dto.LicenseDTO;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 24/02/2016
 */
public class LicenseListAdapter extends RecyclerView.Adapter<LicenseListAdapter.ViewHolder> {

    private List<LicenseDTO> mLicenses;

    public LicenseListAdapter(List<LicenseDTO> licenses) {
        mLicenses = licenses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.item_license_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final LicenseDTO licenseDTO = mLicenses.get(position);
        StringBuilder libraryNameSB = new StringBuilder()
                .append(licenseDTO.getLibraryName())
                .append(" ")
                .append(licenseDTO.getLibraryVersion());
        holder.libraryNameTxtView.setText(libraryNameSB.toString());

        holder.licenseSummaryTxtView.setText(licenseDTO.getLicenseSummary());
    }

    @Override
    public int getItemCount() {
        return mLicenses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView libraryNameTxtView;
        private TextView licenseSummaryTxtView;

        public ViewHolder(View view) {
            super(view);
            libraryNameTxtView = (TextView) view.findViewById(R.id.library_name_text_view);
            licenseSummaryTxtView = (TextView) view.findViewById(R.id.license_summary_text_view);
        }
    }

}
package com.jumbomob.invistoo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Asset;
import com.jumbomob.invistoo.model.entity.Investment;
import com.jumbomob.invistoo.model.persistence.dao.AssetDAO;
import com.jumbomob.invistoo.util.DateUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class NewInvestmentFragment extends Fragment {

    private View mRootView;
    private TextView mAssetName;
    private TextView mAssetBuyTax;
    private TextView mAssetDueDate;
    private TextView mAssetPrice;
    private EditText mAssetQuantity;
    private List<String> mSpinnerItems;

    public static NewInvestmentFragment newInstance() {
        NewInvestmentFragment fragment = new NewInvestmentFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mRootView = inflater.inflate(R.layout.fragment_new_investment, container, false);

        configureAssetSpinner();
        bindElements();

        return mRootView;
    }

    private void configureAssetSpinner() {

        Spinner spinner = (Spinner) mRootView.findViewById(R.id.assets_spinner);

        final AssetDAO assetDAO = AssetDAO.getInstance();
        mSpinnerItems = assetDAO.findNames();

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<>
                (mRootView.getContext(), android.R.layout.simple_spinner_item, mSpinnerItems);
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new SpinnerListener());
    }

    private void bindElements() {
        mAssetName = (TextView) mRootView.findViewById(R.id.asset_name_text_view);
        mAssetBuyTax = (TextView) mRootView.findViewById(R.id.asset_buy_tax_text_view);
        mAssetDueDate = (TextView) mRootView.findViewById(R.id.asset_due_date_text_view);
        mAssetPrice = (TextView) mRootView.findViewById(R.id.asset_price_text_view);
        mAssetQuantity = (EditText) mRootView.findViewById(R.id.asset_quantity_edit_text);
    }

    public class SpinnerListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            final String assetName = mSpinnerItems.get(position);
            configureElements(assetName);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    private void configureElements(final String assetName) {
        AssetDAO dao = AssetDAO.getInstance();
        final Asset asset = dao.findLastByName(assetName);

        mAssetName.setText(asset.getName());
        mAssetBuyTax.setText(asset.getBuyPrice());
        mAssetDueDate.setText(asset.getDueDate());
        mAssetPrice.setText(asset.getBuyPrice());
    }

    private void saveInvestment() {
        Investment investment = new Investment();
        investment.setName(mAssetName.getText().toString());
        investment.setCreationDate(DateUtil.formatDate(new Date()));
        investment.setQuantity(Integer.valueOf(mAssetQuantity.getText().toString()));
        investment.setUpdateDate(DateUtil.formatDate(new Date()));
        investment.setPrice(new BigDecimal(mAssetPrice.getText().toString()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.new_investment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveInvestment();
                break;
        }
        return false;
    }
}

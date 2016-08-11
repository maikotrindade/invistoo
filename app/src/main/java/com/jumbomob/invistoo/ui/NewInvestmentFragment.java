package com.jumbomob.invistoo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Asset;
import com.jumbomob.invistoo.model.entity.AssetTypeEnum;
import com.jumbomob.invistoo.model.entity.Investment;
import com.jumbomob.invistoo.model.persistence.AssetDAO;
import com.jumbomob.invistoo.model.persistence.InvestmentDAO;
import com.jumbomob.invistoo.ui.adapter.SpinnerAssetAdapter;
import com.jumbomob.invistoo.util.DateUtil;
import com.jumbomob.invistoo.util.InvistooUtil;
import com.jumbomob.invistoo.util.NumericUtil;

import java.util.Date;

public class NewInvestmentFragment extends BaseFragment {

    private View mRootView;
    private TextView mAssetAnnualInterestTxtView;
    private TextView mAssetDueDateTxtView;
    private EditText mAssetPriceTxtView;
    private EditText mAssetQuantityTxtView;
    private Spinner mSpinner;

    public static NewInvestmentFragment newInstance() {
        NewInvestmentFragment fragment = new NewInvestmentFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mRootView = inflater.inflate(R.layout.fragment_new_investment, container, false);

        configureAssetSpinner();
        bindElements();

        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.title_new_investment);
    }

    private void configureAssetSpinner() {
        mSpinner = (Spinner) mRootView.findViewById(R.id.assets_spinner);
        final SpinnerAssetAdapter dataAdapter = new SpinnerAssetAdapter
                (getContext(), android.R.layout.simple_spinner_item, AssetTypeEnum.values());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(dataAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                configureElements(pos + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void bindElements() {
        mAssetAnnualInterestTxtView = (TextView) mRootView.findViewById(R.id
                .asset_buy_tax_text_view);
        mAssetDueDateTxtView = (TextView) mRootView.findViewById(R.id.asset_due_date_text_view);
        mAssetPriceTxtView = (EditText) mRootView.findViewById(R.id.asset_price_edit_text);
        mAssetQuantityTxtView = (EditText) mRootView.findViewById(R.id.asset_quantity_edit_text);
    }

    private void configureElements(final int assetIndex) {
        AssetDAO dao = AssetDAO.getInstance();
        final Asset asset = dao.findLastByIndex(assetIndex);
        if (asset != null) {
            mAssetAnnualInterestTxtView.setText(asset.getBuyPrice());
            mAssetDueDateTxtView.setText(asset.getDueDate());
            mAssetPriceTxtView.setText(asset.getBuyPrice());
        }
    }

    private void saveInvestment() {

        if (isValidFields()) {
            Investment investment = new Investment();
            final AssetTypeEnum selectedItem = (AssetTypeEnum) mSpinner.getSelectedItem();
            investment.setName(selectedItem.getTitle());
            investment.setCreationDate(DateUtil.formatDate(new Date()));
            investment.setQuantity(NumericUtil.getValidDouble(mAssetQuantityTxtView.getText()
                    .toString()));
            investment.setUpdateDate(DateUtil.formatDate(new Date()));
            investment.setPrice(mAssetPriceTxtView.getText().toString());
            investment.setAssetType((AssetTypeEnum) mSpinner.getSelectedItem());

            final InvestmentDAO investmentDao = InvestmentDAO.getInstance();
            investmentDao.insert(investment);
            InvistooUtil.makeSnackBar(getActivity(), getContext().getString(R.string
                    .msg_save_investment), Snackbar.LENGTH_LONG).show();

            ((MainActivity) getActivity()).goBackFragment();
        }
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

    private boolean isValidFields() {

        boolean isValid = true;
        final String price = mAssetPriceTxtView.getText().toString();
        if (price.isEmpty() || price.equals("")) {
            mAssetPriceTxtView.setError("");
            isValid = false;
        }

        final String quantity = mAssetQuantityTxtView.getText().toString();
        if (quantity.isEmpty() || quantity.equals("")) {
            mAssetQuantityTxtView.setError("");
            isValid = false;
        }

        final String assetTitle = mSpinner.getSelectedItem().toString();
        if (assetTitle.isEmpty() || assetTitle.equals("")) {
            isValid = false;
        }

        return isValid;
    }
}

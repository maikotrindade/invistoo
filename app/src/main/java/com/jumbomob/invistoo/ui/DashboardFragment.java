package com.jumbomob.invistoo.ui;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.Balance;
import com.jumbomob.invistoo.presenter.DashboardPresenter;
import com.jumbomob.invistoo.ui.adapter.BalanceAssetAdapter;
import com.jumbomob.invistoo.ui.callback.BalanceAssetCallback;
import com.jumbomob.invistoo.util.AnimationUtil;
import com.jumbomob.invistoo.util.NumericUtil;
import com.jumbomob.invistoo.view.DashboardView;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 30/03/2016
 */
public class DashboardFragment extends BaseFragment implements DashboardView {

    private static final String TAG = DashboardFragment.class.getSimpleName();
    private View mRootView;

    private PieChart mChart;
    private LinearLayout mChartContainer;
    private DashboardPresenter mPresenter;
    private RecyclerView mBalanceRecycler;
    private BalanceAssetAdapter mBalanceAdapter;
    private TextView mBalanceBoughtTextView;
    private TextView mBalanceSoldTextView;

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mPresenter = new DashboardPresenter(this);
        configureChart();
        configureBalance();
        configureAssetBalance();
        return mRootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.investment_list_context_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.title_dashboard);
    }

    private void configureBalance() {

        final Double balanceBought = mPresenter.getBalanceBought();
        final Double balanceSold = mPresenter.getBalanceSold();

        if (!balanceBought.equals(new Double(0)) || !balanceSold.equals(new Double(0))) {
            final LinearLayout balanceContainer = (LinearLayout) mRootView.findViewById(R.id.balance_container);
            balanceContainer.setVisibility(View.VISIBLE);
            balanceContainer.setLayoutAnimation(AnimationUtil.getFadeInAnimation());

            final LinearLayout balanceSubContainer = (LinearLayout) mRootView.findViewById(R.id.balance_sub_container);
            final ImageView hideBalanceImgView = (ImageView) mRootView.findViewById(R.id.minimize_image_view);
            mBalanceBoughtTextView = (TextView) mRootView.findViewById(R.id.balance_bought_text_view);
            mBalanceSoldTextView = (TextView) mRootView.findViewById(R.id.balance_sold_text_view);

            mBalanceBoughtTextView.setText(NumericUtil.formatCurrency(balanceBought));
            mBalanceSoldTextView.setText(NumericUtil.formatCurrency(balanceSold));

            hideBalanceImgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (balanceSubContainer.getVisibility() == View.VISIBLE) {
                        balanceSubContainer.setVisibility(View.GONE);
                        hideBalanceImgView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_maximize));
                    } else {
                        balanceSubContainer.setVisibility(View.VISIBLE);
                        hideBalanceImgView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_minimize));

                        balanceSubContainer.setLayoutAnimation(AnimationUtil.getFadeInAnimation());
                    }
                }
            });
        }
    }

    private void configureAssetBalance() {
        final List<Balance> balanceAssets = mPresenter.getBalanceAssets();
        if (!balanceAssets.isEmpty()) {
            final LinearLayout balanceAssetContainer = (LinearLayout) mRootView.findViewById(R.id.balance_assets_container);
            balanceAssetContainer.setLayoutAnimation(AnimationUtil.getFadeInAnimation());
            balanceAssetContainer.setVisibility(View.VISIBLE);
            final LinearLayout assetSubContainer = (LinearLayout) mRootView.findViewById(R.id.balance_assets_sub_container);
            final ImageView hideBalanceAssetImgView = (ImageView) mRootView.findViewById(R.id.minimize_assets_image_view);

            hideBalanceAssetImgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (assetSubContainer.getVisibility() == View.VISIBLE) {
                        assetSubContainer.setVisibility(View.GONE);
                        hideBalanceAssetImgView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_maximize));
                    } else {
                        assetSubContainer.setVisibility(View.VISIBLE);
                        hideBalanceAssetImgView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_minimize));

                        assetSubContainer.setLayoutAnimation(AnimationUtil.getFadeInAnimation());
                    }
                }
            });

            mBalanceRecycler = (RecyclerView) mRootView.findViewById(R.id.balance_recycler_view);
            mBalanceRecycler.setHasFixedSize(true);
            mBalanceRecycler.setLayoutManager(new LinearLayoutManager(mRootView.getContext()));
            mBalanceAdapter = new BalanceAssetAdapter(balanceAssets, new BalanceAssetCallback() {
                @Override
                public void onEditBalance(long assetId, Double oldValue, int position) {
                    showEditBalanceDialog(assetId, oldValue, position);
                }
            });
            mBalanceRecycler.setAdapter(mBalanceAdapter);
        }
    }

    private void showEditBalanceDialog(final long assetId, final Double oldValue, final int position) {
        final Dialog dialog = new Dialog(mRootView.getContext());
        dialog.setContentView(R.layout.edit_balance_dialog);
        dialog.setTitle(R.string.edit_total);

        final EditText balanceEdtText = (EditText) dialog.findViewById(R.id.balance_edit_text);
        balanceEdtText.setText(String.valueOf(oldValue));

        final Button confirmButton = (Button) dialog.findViewById(R.id.confirm_balance_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newValueString = balanceEdtText.getText().toString();
                if (!TextUtils.isEmpty(newValueString) && NumericUtil.isValidDouble(newValueString)) {
                    if (NumericUtil.getValidDouble(newValueString) > 0) {
                        final Double newValue = Double.valueOf(newValueString);
                        mPresenter.editBalance(assetId, newValue);
                        updateBalanceAssetList(position);
                        updateChart();
                        updateBalance();
                        showMessage(getString(R.string.balance_updated_successfully));
                        dialog.dismiss();
                    } else {
                        balanceEdtText.setError(getString(R.string.error_negative_balance));
                    }
                } else {
                    showMessage(getString(R.string.update_balance_error));
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void updateBalanceAssetList(int position) {
        if (mBalanceAdapter != null) {
            mBalanceAdapter.notifyItemChanged(position);
        }
    }

    private void updateBalance() {
        if (mBalanceBoughtTextView != null) {
            final Double balanceBought = mPresenter.getBalanceBought();
            mBalanceBoughtTextView.setText(NumericUtil.formatCurrency(balanceBought));
        }
        if (mBalanceSoldTextView != null) {
            final Double balanceSold = mPresenter.getBalanceSold();
            mBalanceSoldTextView.setText(NumericUtil.formatCurrency(balanceSold));
        }
    }


    private void configureChart() {
        mChart = (PieChart) mRootView.findViewById(R.id.investments_chart);
        mChartContainer = (LinearLayout) mRootView.findViewById(R.id.investments_chart_container);

        mChart.setUsePercentValues(true);
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(20f);
        mChart.setTransparentCircleRadius(22f);
        mChart.setDrawCenterText(true);
        mChart.setDescription("");
        mChart.setNoDataText(getString(R.string.no_data_chart));
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int dataSetIndex, Highlight highlight) {
                if (entry == null) {
                    return;
                }
                Log.i(TAG, "Value: " + entry.getVal() + ", xIndex: " + entry.getXIndex() + ", DataSet index: " + dataSetIndex);
            }

            @Override
            public void onNothingSelected() {
                Log.i(TAG, "nothing selected");
            }
        });

        mPresenter.getChartData(mChart);
    }

    @Override
    public void setChartData(final PieData data) {
        mChart.setData(data);
        mChart.highlightValues(null);
        mChart.invalidate();
    }

    @Override
    public void setNoChartData() {
        mChartContainer.setVisibility(View.GONE);
        final LinearLayout newInvestmentContainer = (LinearLayout) mRootView.findViewById(R.id.new_investment_container);
        newInvestmentContainer.setVisibility(View.VISIBLE);
        final LinearLayout newInvestmentLayout = (LinearLayout) mRootView.findViewById(R.id.new_investment_layout);
        newInvestmentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity) getActivity()).setFragment(InvestmentsListFragment.newInstance(), R.id.nav_investments,
                        getString(R.string.my_investments));
            }
        });
    }

    public void updateChart() {
        if (mChart != null) {
            mPresenter.getChartData(mChart);
        }
    }
}
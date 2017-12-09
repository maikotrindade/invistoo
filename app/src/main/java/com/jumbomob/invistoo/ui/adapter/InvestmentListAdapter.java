package com.jumbomob.invistoo.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.AssetStatusEnum;
import com.jumbomob.invistoo.model.entity.AssetTypeEnum;
import com.jumbomob.invistoo.model.entity.Investment;
import com.jumbomob.invistoo.model.persistence.InvestmentDAO;
import com.jumbomob.invistoo.util.DateUtil;
import com.jumbomob.invistoo.util.DialogUtil;
import com.jumbomob.invistoo.util.NumericUtil;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 24/02/2016
 */
public class InvestmentListAdapter extends RecyclerView.Adapter<InvestmentListAdapter.ViewHolder> {

    private List<Investment> mInvestments;
    private Context mContext;

    public InvestmentListAdapter(List<Investment> investments, Fragment fragment) {
        mInvestments = investments;
        mContext = fragment.getContext();
    }

    public void setItems(List<Investment> investments) {
        this.mInvestments = investments;
    }

    public List<Investment> getItems() {
        return mInvestments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.item_investment_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Investment investment = mInvestments.get(position);
        holder.nameTxtView.setText(investment.getName());
        holder.valueTxtView.setText(NumericUtil.formatCurrency(NumericUtil.getValidDouble((investment.getPrice()))));
        holder.lastUpdateTxtView.setText(DateUtil.formatDate(investment.getUpdateDate(), DateUtil.SIMPLE_DATETIME_FORMAT));

        final AssetStatusEnum statusEnum = AssetStatusEnum.getById(investment.getAssetStatus());
        holder.statusTextView.setText(statusEnum.getTitle());

        final AssetTypeEnum typeEnum = AssetTypeEnum.getById(investment.getAssetType());

        Drawable background = holder.circleContainer.getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable) background).getPaint().setColor(ContextCompat.getColor(mContext, typeEnum.getColorResourceId()));
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable) background).setColor(ContextCompat.getColor(mContext, typeEnum.getColorResourceId()));
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable) background).setColor(ContextCompat.getColor(mContext, typeEnum.getColorResourceId()));
        }

        holder.abbreviationTxtView.setText(typeEnum.getInitials());
        holder.yearTxtView.setText(String.valueOf(typeEnum.getYear()));

        holder.contextMenuImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popup = new PopupMenu(mContext, view);
                popup.inflate(R.menu.investment_list_context_menu);
                popup.show();

                final MenuItem statusItem = popup.getMenu().findItem(R.id.handle_status_action);
                if (statusEnum.equals(AssetStatusEnum.SELL)) {
                    statusItem.setTitle(mContext.getString(R.string.bought_action_menu));
                } else {
                    statusItem.setTitle(mContext.getString(R.string.sold_action_menu));
                }

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.handle_status_action:
                                handleInvestmentStatus(investment, position);
                                return true;
                            case R.id.more_info_action:
                                changeToMoreInfoScreen(investment);
                                return true;
                            case R.id.remove_action:
                                showDialogRemoveInvestment(investment);
                                return true;
                            default:
                                return false;
                        }
                    }
                });

            }
        });
    }

    private void handleInvestmentStatus(final Investment investment, int position) {
        InvestmentDAO investmentDAO = InvestmentDAO.getInstance();
        AssetStatusEnum statusEnum = AssetStatusEnum.getById(investment.getAssetStatus());

        if (statusEnum.equals(AssetStatusEnum.SELL)) {
            statusEnum = AssetStatusEnum.BUY;
        } else {
            statusEnum = AssetStatusEnum.SELL;
        }
        investmentDAO.updateSold(investment, statusEnum);

        notifyItemChanged(position);
    }

    private void changeToMoreInfoScreen(final Investment investment) {
        //TODO
        //((BaseActivity) mContext).setFragmentWithStack(, final String title)
    }

    private void showDialogRemoveInvestment(final Investment investment) {
        DialogUtil.getInstance(mContext).show(mContext, R.string.remove_investment_title, R.string.remove_investment_message,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        InvestmentDAO investmentDAO = InvestmentDAO.getInstance();
                        investmentDAO.updateActive(investment);
                        notifyDataSetChanged();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mInvestments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout circleContainer;
        private TextView abbreviationTxtView;
        private TextView statusTextView;
        private TextView yearTxtView;
        private TextView nameTxtView;
        private TextView valueTxtView;
        private TextView lastUpdateTxtView;
        private ImageView contextMenuImgView;

        public ViewHolder(View view) {
            super(view);
            circleContainer = (LinearLayout) view.findViewById(R.id.circle_container);
            abbreviationTxtView = (TextView) view.findViewById(R.id.abbreviation_text_view);
            statusTextView = (TextView) view.findViewById(R.id.status_text_view);
            yearTxtView = (TextView) view.findViewById(R.id.year_text_view);
            nameTxtView = (TextView) view.findViewById(R.id.name_text_view);
            valueTxtView = (TextView) view.findViewById(R.id.value_text_view);
            lastUpdateTxtView = (TextView) view.findViewById(R.id.last_update_text_view);
            contextMenuImgView = (ImageView) view.findViewById(R.id.context_menu_image_view);
        }
    }

}
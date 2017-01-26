package com.jumbomob.invistoo.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.jumbomob.invistoo.R;
import com.jumbomob.invistoo.model.entity.AssetTypeEnum;
import com.jumbomob.invistoo.model.entity.Goal;
import com.jumbomob.invistoo.model.persistence.GoalDAO;

import java.util.List;

/**
 * @author maiko.trindade
 * @since 06/02/2016
 */
public class GoalListAdapter extends RecyclerView.Adapter<GoalListAdapter.ViewHolder> {

    private List<Goal> mItems;
    private int mPosition;
    private Activity mActivity;

    private final static int FADE_DURATION = 300; // in milliseconds

    public GoalListAdapter(Activity activity, List<Goal> goals) {
        mActivity = activity;
        mItems = goals;
    }

    public void setItems(List<Goal> goals) {
        this.mItems = goals;
    }

    public List<Goal> getItems() {
        return mItems;
    }

    public Goal getSelectedItem() {
        return mItems.get(mPosition);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.item_goals_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //temporary
        holder.setIsRecyclable(false);

        final Goal goal = mItems.get(position);

        final Double percent = goal.getPercent();
        if (percent != null) {
            holder.percentageEditText.setText(String.valueOf(percent));
        }

        holder.percentageEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence textChanged, int start, int before, int
                    count) {
                final String percentage = textChanged.toString();
                if (!TextUtils.isEmpty(percentage)) {
                    updatePercentage(goal, Double.valueOf(percentage));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        final SpinnerAssetAdapter dataAdapter = new SpinnerAssetAdapter
                (mActivity.getBaseContext(), android.R.layout.simple_spinner_item, AssetTypeEnum.values());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.assetSpinner.setAdapter(dataAdapter);
        final int positionSpinner = dataAdapter.getPosition(goal.getAssetTypeEnum());

        holder.assetSpinner.setSelection(positionSpinner);
        holder.assetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long
                    id) {
                final long assetTypeId = dataAdapter.getItem(pos).getId();
                updateAssetType(goal, assetTypeId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        holder.removeGoalContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Goal List Adap", "remove_goal_container CLIKED");
                //TODO
                removeAt(position);
            }
        });

        setFadeAnimation(holder.itemView);
    }

    public void removeAt(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void updatePercentage(Goal goal, Double percentage) {
        GoalDAO.getInstance().updatePercentage(goal, percentage);
    }

    public void updateAssetType(Goal goal, Long assetTypeId) {
        GoalDAO.getInstance().updateAssetType(goal, assetTypeId);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private EditText percentageEditText;
        private Spinner assetSpinner;
        private LinearLayout removeGoalContainer;

        public ViewHolder(View view) {
            super(view);
            percentageEditText = (EditText) view.findViewById(R.id.percentage_edit_text);
            assetSpinner = (Spinner) view.findViewById(R.id.assets_spinner);
            removeGoalContainer = (LinearLayout) view.findViewById(R.id.remove_goal_container);
        }
    }


    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.2f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
}

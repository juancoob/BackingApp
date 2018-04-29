package com.juancoob.nanodegree.and.backingapp.adapter.Impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.juancoob.nanodegree.and.backingapp.R;
import com.juancoob.nanodegree.and.backingapp.adapter.IRecipeStepsAdapter;
import com.juancoob.nanodegree.and.backingapp.domain.model.Step;
import com.juancoob.nanodegree.and.backingapp.presentation.recipeDescriptionList.IRecipeDescriptionListContract;
import com.juancoob.nanodegree.and.backingapp.repository.RecipesRepository;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Juan Antonio Cobos Obrero on 28/04/18.
 */
public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.IngredientViewHolder> implements IRecipeStepsAdapter {

    private Context mCtx;
    private List<Step> mSteps = new ArrayList<>();
    private IRecipeDescriptionListContract mIRecipeDescriptionListContract;
    private int mPreviousSelectedStepPosition;

    public StepsAdapter(Context context, IRecipeDescriptionListContract iRecipeDescriptionListContract) {
        mCtx = context;
        mIRecipeDescriptionListContract = iRecipeDescriptionListContract;
        if(mCtx.getResources().getBoolean(R.bool.tablet)) {
            mPreviousSelectedStepPosition = RecipesRepository.getInstance().getSelectedStepPosition();
        }
    }

    @Override
    public void updateSteps(List<Step> steps) {
        mSteps.clear();
        mSteps.addAll(steps);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_step, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Step step = mSteps.get(position);
        StringBuilder builder = new StringBuilder();
        if(step.getStepId() != 0) {
            builder.append(String.format(mCtx.getString(R.string.step), step.getStepId()));
        }
        builder.append(step.getShortDescription());
        holder.recipeStepTextView.setText(builder.toString());
        if(mCtx.getResources().getBoolean(R.bool.tablet)) {
            if (mPreviousSelectedStepPosition == position) {
                holder.stepCardView.setBackgroundColor(mCtx.getResources().getColor(R.color.colorAccent));
            } else {
                holder.stepCardView.setBackgroundColor(mCtx.getResources().getColor(R.color.cardview_light_background));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }


    public class IngredientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.cv_step)
        public CardView stepCardView;

        @BindView(R.id.tv_recipe_step)
        public TextView recipeStepTextView;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mIRecipeDescriptionListContract.onClickStep(getAdapterPosition());
            if(mCtx.getResources().getBoolean(R.bool.tablet)) {
                RecipesRepository.getInstance().setSelectedStepPosition(getAdapterPosition());
                mPreviousSelectedStepPosition = getAdapterPosition();
                notifyDataSetChanged();
            }
        }
    }
}

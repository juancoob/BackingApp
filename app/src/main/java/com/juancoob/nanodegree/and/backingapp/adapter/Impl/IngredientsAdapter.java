package com.juancoob.nanodegree.and.backingapp.adapter.Impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.juancoob.nanodegree.and.backingapp.R;
import com.juancoob.nanodegree.and.backingapp.adapter.IRecipeIngredientsAdapter;
import com.juancoob.nanodegree.and.backingapp.domain.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Juan Antonio Cobos Obrero on 28/04/18.
 */
public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> implements IRecipeIngredientsAdapter {

    private Context mCtx;
    private List<Ingredient> mIngredients = new ArrayList<>();

    public IngredientsAdapter(Context context) {
        mCtx = context;
    }

    @Override
    public void updateIngredients(List<Ingredient> ingredients) {
        mIngredients.clear();
        mIngredients.addAll(ingredients);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = mIngredients.get(position);
        if (ingredient.getQuantity() == Math.round(ingredient.getQuantity())) {
            holder.recipeIngredientTextView.setText(String.format(mCtx.getString(R.string.recipe_ingredient_integer),
                    (int) ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getIngredient()));
        } else {
            holder.recipeIngredientTextView.setText(String.format(mCtx.getString(R.string.recipe_ingredient_float),
                    ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getIngredient()));
        }
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_recipe_ingredient)
        public TextView recipeIngredientTextView;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

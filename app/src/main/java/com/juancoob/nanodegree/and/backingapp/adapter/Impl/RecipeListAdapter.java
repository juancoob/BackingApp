package com.juancoob.nanodegree.and.backingapp.adapter.Impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.juancoob.nanodegree.and.backingapp.R;
import com.juancoob.nanodegree.and.backingapp.adapter.IRecipeListAdapterContract;
import com.juancoob.nanodegree.and.backingapp.domain.model.Recipe;
import com.juancoob.nanodegree.and.backingapp.presentation.recipeList.IRecipeListContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Juan Antonio Cobos Obrero on 25/04/18.
 */
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> implements IRecipeListAdapterContract {

    private Context mCtx;
    private final List<Recipe> mRecipes = new ArrayList<>();
    private final IRecipeListContract mIRecipeListContract;

    public RecipeListAdapter(Context context, IRecipeListContract iRecipeListContract) {
        mCtx = context;
        mIRecipeListContract = iRecipeListContract;
    }

    @Override
    public void updateRecipes(List<Recipe> recipes) {
        mRecipes.clear();
        mRecipes.addAll(recipes);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = mRecipes.get(position);
        if(!recipe.getImagePath().isEmpty()) {
            Picasso.with(mCtx).load(recipe.getImagePath()).into(holder.recipeNameIconImageView);
        }
        holder.recipeNameTextView.setText(String.format(mCtx.getString(R.string.recipe_name_servings), recipe.getRecipeName(), recipe.getServings()));
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_recipe_name_icon)
        public ImageView recipeNameIconImageView;

        @BindView(R.id.tv_recipe_name)
        public TextView recipeNameTextView;


        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            mIRecipeListContract.onClickRecipe(mRecipes.get(getAdapterPosition()));
        }
    }
}

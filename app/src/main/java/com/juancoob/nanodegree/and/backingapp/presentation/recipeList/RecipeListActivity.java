package com.juancoob.nanodegree.and.backingapp.presentation.recipeList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.juancoob.nanodegree.and.backingapp.R;
import com.juancoob.nanodegree.and.backingapp.domain.executor.impl.ThreadExecutor;
import com.juancoob.nanodegree.and.backingapp.domain.model.Recipe;
import com.juancoob.nanodegree.and.backingapp.domain.threading.impl.MainThreadImpl;
import com.juancoob.nanodegree.and.backingapp.presentation.recipeDescriptionList.RecipeDescriptionListActivity;
import com.juancoob.nanodegree.and.backingapp.repository.RecipesRepository;
import com.juancoob.nanodegree.and.backingapp.util.ActivityUtils;
import com.juancoob.nanodegree.and.backingapp.util.Constants;

public class RecipeListActivity extends AppCompatActivity implements IRecipeListContract {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        RecipeListFragment recipeListFragment = (RecipeListFragment) getSupportFragmentManager().findFragmentById(R.id.fl_content_frame);
        if(recipeListFragment == null) {
            recipeListFragment = com.juancoob.nanodegree.and.backingapp.presentation.recipeList.RecipeListFragment.getInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), recipeListFragment, R.id.fl_content_frame);
        }

        RecipeListPresenter recipeListPresenter = new RecipeListPresenter(
                recipeListFragment,
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                RecipesRepository.getInstance());

        recipeListFragment.setPresenter(recipeListPresenter);

    }

    @Override
    public void onClickRecipe(Recipe recipe) {
        RecipesRepository.getInstance().setRecipeIngredients(recipe.getIngredients());
        RecipesRepository.getInstance().setRecipeSteps(recipe.getSteps());
        RecipesRepository.getInstance().setSelectedStepPosition(getResources().getInteger(R.integer.default_number));
        Intent intentToDetail = new Intent(this, RecipeDescriptionListActivity.class);
        intentToDetail.putExtra(Constants.RECIPE_NAME, recipe.getRecipeName());
        startActivity(intentToDetail);
    }
}

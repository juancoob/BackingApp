package com.juancoob.nanodegree.and.backingapp.presentation.recipeList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.juancoob.nanodegree.and.backingapp.R;
import com.juancoob.nanodegree.and.backingapp.domain.executor.impl.ThreadExecutor;
import com.juancoob.nanodegree.and.backingapp.domain.model.Recipe;
import com.juancoob.nanodegree.and.backingapp.domain.threading.impl.MainThreadImpl;
import com.juancoob.nanodegree.and.backingapp.repository.RecipesRepository;
import com.juancoob.nanodegree.and.backingapp.util.ActivityUtils;

public class RecipeListActivity extends AppCompatActivity implements IRecipeListContract {

    private com.juancoob.nanodegree.and.backingapp.presentation.recipeList.RecipeListFragment mRecipeListFragment;
    private com.juancoob.nanodegree.and.backingapp.presentation.recipeList.RecipeListPresenter mRecipeListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        mRecipeListFragment = (com.juancoob.nanodegree.and.backingapp.presentation.recipeList.RecipeListFragment) getSupportFragmentManager().findFragmentById(R.id.fl_content_frame);
        if(mRecipeListFragment == null) {
            mRecipeListFragment = com.juancoob.nanodegree.and.backingapp.presentation.recipeList.RecipeListFragment.getInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mRecipeListFragment, R.id.fl_content_frame);
        }

        mRecipeListPresenter = new com.juancoob.nanodegree.and.backingapp.presentation.recipeList.RecipeListPresenter(
                mRecipeListFragment,
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                new RecipesRepository());

        mRecipeListFragment.setPresenter(mRecipeListPresenter);

    }

    @Override
    public void onClickRecipe(Recipe recipe) {
        //todo
    }
}

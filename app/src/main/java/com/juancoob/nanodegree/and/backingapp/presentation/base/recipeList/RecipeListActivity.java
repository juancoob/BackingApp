package com.juancoob.nanodegree.and.backingapp.presentation.base.recipeList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.juancoob.nanodegree.and.backingapp.R;
import com.juancoob.nanodegree.and.backingapp.domain.executor.impl.ThreadExecutor;
import com.juancoob.nanodegree.and.backingapp.domain.threading.impl.MainThreadImpl;
import com.juancoob.nanodegree.and.backingapp.repository.RecipesRepository;
import com.juancoob.nanodegree.and.backingapp.util.ActivityUtils;

public class RecipeListActivity extends AppCompatActivity {

    private RecipeListFragment mRecipeListFragment;
    private RecipeListPresenter mRecipeListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        mRecipeListFragment = (RecipeListFragment) getSupportFragmentManager().findFragmentById(R.id.fl_content_frame);
        if(mRecipeListFragment == null) {
            mRecipeListFragment = RecipeListFragment.getInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mRecipeListFragment, R.id.fl_content_frame);
        }

        mRecipeListPresenter = new RecipeListPresenter(
                mRecipeListFragment,
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                new RecipesRepository());

        mRecipeListFragment.setPresenter(mRecipeListPresenter);

    }
}

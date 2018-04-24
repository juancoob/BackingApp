package com.juancoob.nanodegree.and.backingapp.presentation.base.recipeList;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.gson.Gson;
import com.juancoob.nanodegree.and.backingapp.domain.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan Antonio Cobos Obrero on 23/04/18.
 */
public class RecipeListFragment extends Fragment implements IRecipeListContract.View {


    private RecipeListPresenter mRecipeListPresenter;
    private List<Recipe> mRecipes = new ArrayList<>();

    public static RecipeListFragment getInstance() {
        return new RecipeListFragment();
    }

    @Override
    public void showRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
        Log.d("showRecipes", new Gson().toJson(recipes));
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void noInternetConnection() {

    }

    public void setPresenter(RecipeListPresenter presenter) {
        this.mRecipeListPresenter = presenter;
    }

    public List<Recipe> getRecipes() {
        return mRecipes;
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecipeListPresenter.resume();
    }
}

package com.juancoob.nanodegree.and.backingapp.adapter;

import com.juancoob.nanodegree.and.backingapp.domain.model.Recipe;

import java.util.List;

/**
 * Created by Juan Antonio Cobos Obrero on 25/04/18.
 */
public interface IRecipeListAdapterContract {
    void updateRecipes(List<Recipe> recipes);
}

package com.juancoob.nanodegree.and.backingapp.adapter;

import com.juancoob.nanodegree.and.backingapp.domain.model.Ingredient;

import java.util.List;

/**
 * Created by Juan Antonio Cobos Obrero on 28/04/18.
 */
public interface IRecipeIngredientsAdapter {
    void updateIngredients(List<Ingredient> ingredients);
}

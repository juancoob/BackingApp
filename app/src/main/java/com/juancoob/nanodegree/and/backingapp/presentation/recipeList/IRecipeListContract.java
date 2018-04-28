package com.juancoob.nanodegree.and.backingapp.presentation.recipeList;

import com.juancoob.nanodegree.and.backingapp.domain.model.Recipe;
import com.juancoob.nanodegree.and.backingapp.presentation.base.presenters.BasePresenter;
import com.juancoob.nanodegree.and.backingapp.presentation.base.ui.BaseView;

import java.util.List;

/**
 * Created by Juan Antonio Cobos Obrero on 23/04/18.
 */
public interface IRecipeListContract {

    void onClickRecipe(Recipe recipe);

    interface View extends BaseView {
        void showRecipes(List<Recipe> recipes);
    }

    interface Presenter extends BasePresenter {
    }

}

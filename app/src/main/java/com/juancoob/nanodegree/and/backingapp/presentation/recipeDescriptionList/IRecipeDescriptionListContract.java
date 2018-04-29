package com.juancoob.nanodegree.and.backingapp.presentation.recipeDescriptionList;

import com.juancoob.nanodegree.and.backingapp.presentation.base.presenters.BasePresenter;
import com.juancoob.nanodegree.and.backingapp.presentation.base.ui.BaseView;

/**
 * Created by Juan Antonio Cobos Obrero on 28/04/18.
 */
public interface IRecipeDescriptionListContract {

    void onClickStep(int selectedPosition);

    interface View extends BaseView {
        void showIngredients();
        void showSteps();
    }

    interface Presenter extends BasePresenter {

    }

}

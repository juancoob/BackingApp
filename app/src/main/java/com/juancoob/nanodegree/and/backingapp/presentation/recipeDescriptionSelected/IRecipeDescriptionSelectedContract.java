package com.juancoob.nanodegree.and.backingapp.presentation.recipeDescriptionSelected;

import com.juancoob.nanodegree.and.backingapp.presentation.base.presenters.BasePresenter;
import com.juancoob.nanodegree.and.backingapp.presentation.base.ui.BaseView;

/**
 * Created by Juan Antonio Cobos Obrero on 28/04/18.
 */
public interface IRecipeDescriptionSelectedContract {

    interface View extends BaseView {
        void showStepDescription();
    }

    interface Presenter extends BasePresenter {
    }

}

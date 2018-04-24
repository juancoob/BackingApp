package com.juancoob.nanodegree.and.backingapp.domain.usecase;

import com.juancoob.nanodegree.and.backingapp.domain.model.Recipe;
import com.juancoob.nanodegree.and.backingapp.domain.usecase.base.UseCase;

import java.util.List;

/**
 * Created by Juan Antonio Cobos Obrero on 17/04/18.
 */
public interface FetchingRecipesUseCase extends UseCase {
    interface Callback {
        void onRecipesRetrieved(List<Recipe> recipes);
        void onNoInternetConnection();
    }
}

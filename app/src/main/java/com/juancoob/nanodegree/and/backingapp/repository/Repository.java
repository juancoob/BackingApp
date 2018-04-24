package com.juancoob.nanodegree.and.backingapp.repository;

import com.juancoob.nanodegree.and.backingapp.domain.usecase.impl.FetchingRecipesUseCaseImpl;

/**
 * Created by Juan Antonio Cobos Obrero on 22/04/18.
 */
public interface Repository {
    void fetchRecipes(FetchingRecipesUseCaseImpl fetchingRecipesUseCase);
}

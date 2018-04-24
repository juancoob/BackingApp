package com.juancoob.nanodegree.and.backingapp.domain.usecase.impl;


import com.juancoob.nanodegree.and.backingapp.domain.executor.Executor;
import com.juancoob.nanodegree.and.backingapp.domain.model.Recipe;
import com.juancoob.nanodegree.and.backingapp.domain.threading.MainThread;
import com.juancoob.nanodegree.and.backingapp.domain.usecase.FetchingRecipesUseCase;
import com.juancoob.nanodegree.and.backingapp.domain.usecase.base.AbstractUseCase;
import com.juancoob.nanodegree.and.backingapp.repository.RecipesRepository;

import java.util.List;

/**
 * Created by Juan Antonio Cobos Obrero on 23/04/18.
 */
public class FetchingRecipesUseCaseImpl extends AbstractUseCase implements FetchingRecipesUseCase {

    private final FetchingRecipesUseCase.Callback mCallback;
    private final RecipesRepository mRecipesRepository;

    public FetchingRecipesUseCaseImpl(Executor executor,
                                      MainThread mainThread,
                                      Callback callback,
                                      RecipesRepository recipesRepository) {
        super(executor, mainThread);
        mCallback = callback;
        mRecipesRepository = recipesRepository;
    }


    @Override
    public void run() {
        mRecipesRepository.fetchRecipes(this);
    }

    public void showRecipes(final List<Recipe> recipes) {
        if(recipes == null) {
            noInternetConnection();
            return;
        }

        mMainthread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRecipesRetrieved(recipes);
            }
        });
    }

    public void noInternetConnection() {
        mMainthread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onNoInternetConnection();
            }
        });
    }
}

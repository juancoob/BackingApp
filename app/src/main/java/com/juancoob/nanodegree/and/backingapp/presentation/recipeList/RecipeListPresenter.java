package com.juancoob.nanodegree.and.backingapp.presentation.recipeList;

import com.juancoob.nanodegree.and.backingapp.domain.executor.impl.ThreadExecutor;
import com.juancoob.nanodegree.and.backingapp.domain.model.Recipe;
import com.juancoob.nanodegree.and.backingapp.domain.threading.MainThread;
import com.juancoob.nanodegree.and.backingapp.domain.usecase.FetchingRecipesUseCase;
import com.juancoob.nanodegree.and.backingapp.domain.usecase.impl.FetchingRecipesUseCaseImpl;
import com.juancoob.nanodegree.and.backingapp.presentation.base.presenters.AbstractPresenter;
import com.juancoob.nanodegree.and.backingapp.repository.RecipesRepository;

import java.util.List;

/**
 * Created by Juan Antonio Cobos Obrero on 23/04/18.
 */
public class RecipeListPresenter extends AbstractPresenter implements IRecipeListContract.Presenter,
        FetchingRecipesUseCase.Callback {


    private RecipeListFragment mRecipeListFragment;
    private RecipesRepository mRecipesRepository;

    public RecipeListPresenter(RecipeListFragment recipeListFragment,
                               ThreadExecutor executor,
                               MainThread mainThread,
                               RecipesRepository recipesRepository) {
        super(executor, mainThread);
        mRecipeListFragment = recipeListFragment;
        mRecipesRepository = recipesRepository;
    }

    @Override
    public void resume() {

        mRecipeListFragment.showProgress();

        if(mRecipeListFragment.getRecipes().size() == 0) {
            fetchRecipes();
        } else {
            onRecipesRetrieved(mRecipeListFragment.getRecipes());
        }

    }

    public void fetchRecipes() {
        FetchingRecipesUseCase useCase = new FetchingRecipesUseCaseImpl(
                mExecutor,
                mMainThread,
                this,
                mRecipesRepository);
        useCase.execute();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onRecipesRetrieved(List<Recipe> recipes) {
        mRecipeListFragment.hideProgress();
        mRecipeListFragment.showRecipes(recipes);
    }

    @Override
    public void onNoInternetConnection() {
        mRecipeListFragment.hideProgress();
        mRecipeListFragment.noInternetConnection();
    }
}

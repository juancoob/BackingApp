package com.juancoob.nanodegree.and.backingapp.repository;

import android.support.annotation.NonNull;

import com.juancoob.nanodegree.and.backingapp.domain.model.Recipe;
import com.juancoob.nanodegree.and.backingapp.domain.usecase.impl.FetchingRecipesUseCaseImpl;
import com.juancoob.nanodegree.and.backingapp.repository.rest.IBackingAppAPIService;
import com.juancoob.nanodegree.and.backingapp.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Juan Antonio Cobos Obrero on 22/04/18.
 */
public class RecipesRepository implements Repository {

    @Override
    public void fetchRecipes(final FetchingRecipesUseCaseImpl fetchingRecipesUseCaseImpl) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IBackingAppAPIService iBackingAppAPIService = retrofit.create(IBackingAppAPIService.class);
        Call<List<Recipe>> responseCall = iBackingAppAPIService.getRecipes();

        if(responseCall != null) {
            responseCall.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                    if(response.isSuccessful()) {
                        fetchingRecipesUseCaseImpl.showRecipes(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                    fetchingRecipesUseCaseImpl.noInternetConnection();
                }
            });
        }

    }
}

package com.juancoob.nanodegree.and.backingapp.repository.rest;

import com.juancoob.nanodegree.and.backingapp.domain.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Juan Antonio Cobos Obrero on 23/04/18.
 */
public interface IBackingAppAPIService {

    @GET("baking.json")
    Call<List<Recipe>> getRecipes();

}

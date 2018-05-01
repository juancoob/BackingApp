package com.juancoob.nanodegree.and.backingapp.presentation.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.widget.RemoteViews;

import com.juancoob.nanodegree.and.backingapp.R;
import com.juancoob.nanodegree.and.backingapp.adapter.IRecipeListAdapterContract;
import com.juancoob.nanodegree.and.backingapp.adapter.Impl.RecipeListAdapter;
import com.juancoob.nanodegree.and.backingapp.domain.executor.impl.ThreadExecutor;
import com.juancoob.nanodegree.and.backingapp.domain.model.Ingredient;
import com.juancoob.nanodegree.and.backingapp.domain.model.Recipe;
import com.juancoob.nanodegree.and.backingapp.domain.threading.impl.MainThreadImpl;
import com.juancoob.nanodegree.and.backingapp.domain.usecase.FetchingRecipesUseCase;
import com.juancoob.nanodegree.and.backingapp.domain.usecase.impl.FetchingRecipesUseCaseImpl;
import com.juancoob.nanodegree.and.backingapp.presentation.recipeList.IRecipeListContract;
import com.juancoob.nanodegree.and.backingapp.repository.RecipesRepository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Juan Antonio Cobos Obrero on 1/05/18.
 */
public class BackingAppWidgetActivity extends AppCompatActivity implements IRecipeListContract, FetchingRecipesUseCase.Callback {

    @BindView(R.id.rv_widget_recipes)
    public RecyclerView recipesRecyclerViewWidget;
    private IRecipeListAdapterContract mAdapter;
    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private AppWidgetManager mAppWidgetManager;
    private RemoteViews mRemoteViews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backing_app_widget);
        setResult(RESULT_CANCELED);
        ButterKnife.bind(this);
        setTitle(R.string.widget_title);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initRecipesRecyclerView();
        getRecipes();
    }

    private void initRecipesRecyclerView() {
        LinearLayoutManager linearLayoutManager;
        GridLayoutManager gridLayoutManager;
        if (getResources().getBoolean(R.bool.tablet)) {
            gridLayoutManager = new GridLayoutManager(this, getNumberColumns());
            recipesRecyclerViewWidget.setLayoutManager(gridLayoutManager);
        } else {
            linearLayoutManager = new LinearLayoutManager(this);
            recipesRecyclerViewWidget.setLayoutManager(linearLayoutManager);
        }
        mAdapter = new RecipeListAdapter(this, this);
        recipesRecyclerViewWidget.setAdapter((RecyclerView.Adapter) mAdapter);
    }

    private int getNumberColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int columns = width / getResources().getInteger(R.integer.width_divider);
        if (columns >= 2) return columns;
        else return 2;
    }

    private void getRecipes() {
        FetchingRecipesUseCase useCase = new FetchingRecipesUseCaseImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                RecipesRepository.getInstance());

        useCase.execute();
    }

    @Override
    public void onRecipesRetrieved(List<Recipe> recipes) {
        mAdapter.updateRecipes(recipes);

        mAppWidgetManager = AppWidgetManager.getInstance(this);
        mRemoteViews = new RemoteViews(this.getPackageName(), R.layout.backing_app_recipe_widget);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

        }

        if (AppWidgetManager.INVALID_APPWIDGET_ID == mAppWidgetId) {
            finish();
        }
    }

    @Override
    public void onNoInternetConnection() {
    }

    @Override
    public void onClickRecipe(Recipe recipe) {

        mRemoteViews.setTextViewText(R.id.tv_widget_recipe_name, recipe.getRecipeName());
        StringBuilder stringBuilder = new StringBuilder();
        boolean notFirstTime = false;

        for (Ingredient ingredient : recipe.getIngredients()) {

            if (notFirstTime) {
                stringBuilder.append(getString(R.string.ingredients_space));
            }

            if (ingredient.getQuantity() == Math.round(ingredient.getQuantity())) {
                stringBuilder.append(getString(R.string.ingredients_dot));
                stringBuilder.append(String.format(getString(R.string.recipe_ingredient_integer),
                        (int) ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getIngredient()));
            } else {
                stringBuilder.append(getString(R.string.ingredients_dot));
                stringBuilder.append(String.format(getString(R.string.recipe_ingredient_float),
                        ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getIngredient()));
            }

            notFirstTime = true;
        }
        mRemoteViews.setTextViewText(R.id.tv_widget_ingredients, stringBuilder.toString());

        mAppWidgetManager.updateAppWidget(mAppWidgetId, mRemoteViews);

        Intent result = new Intent();
        result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, result);
        finish();
    }
}

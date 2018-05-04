package com.juancoob.nanodegree.and.backingapp.presentation.recipeDescriptionList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.juancoob.nanodegree.and.backingapp.R;
import com.juancoob.nanodegree.and.backingapp.presentation.recipeDescriptionSelected.RecipeDescriptionSelectedActivity;
import com.juancoob.nanodegree.and.backingapp.presentation.recipeDescriptionSelected.RecipeDescriptionSelectedFragment;
import com.juancoob.nanodegree.and.backingapp.util.ActivityUtils;
import com.juancoob.nanodegree.and.backingapp.util.Constants;

/**
 * Created by Juan Antonio Cobos Obrero on 28/04/18.
 */
public class RecipeDescriptionListActivity extends AppCompatActivity implements IRecipeDescriptionListContract {

    private RecipeDescriptionSelectedFragment mRecipeDescriptionSelectedFragment;
    private String mRecipeName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_description_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(Constants.RECIPE_NAME)) {
            mRecipeName = intent.getStringExtra(Constants.RECIPE_NAME);
            setTitle(mRecipeName);
        }

        RecipeDescriptionListFragment recipeDescriptionListFragment;

        if (getResources().getBoolean(R.bool.tablet)) {

            recipeDescriptionListFragment =
                    (RecipeDescriptionListFragment) getSupportFragmentManager().findFragmentById(R.id.f_recipe_steps);

            mRecipeDescriptionSelectedFragment =
                    (RecipeDescriptionSelectedFragment) getSupportFragmentManager().findFragmentById(R.id.f_recipe_step_description);

            if (recipeDescriptionListFragment == null) {
                recipeDescriptionListFragment = RecipeDescriptionListFragment.getInstance();
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), recipeDescriptionListFragment, R.id.f_recipe_steps);
            }

            if (mRecipeDescriptionSelectedFragment == null) {
                mRecipeDescriptionSelectedFragment = RecipeDescriptionSelectedFragment.getInstance();
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mRecipeDescriptionSelectedFragment, R.id.f_recipe_step_description);
            }

        } else {
            recipeDescriptionListFragment =
                    (RecipeDescriptionListFragment) getSupportFragmentManager().findFragmentById(R.id.fl_content_frame);

            if (recipeDescriptionListFragment == null) {
                recipeDescriptionListFragment = RecipeDescriptionListFragment.getInstance();
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), recipeDescriptionListFragment, R.id.fl_content_frame);
            }
        }
    }

    @Override
    public void onClickStep(int selectedStepPosition) {
        if (getResources().getBoolean(R.bool.tablet)) {
            mRecipeDescriptionSelectedFragment.goToStep(selectedStepPosition);
        } else {
            Intent intent = new Intent(this, RecipeDescriptionSelectedActivity.class);
            intent.putExtra(Constants.SELECTED_STEP_POSITION, selectedStepPosition);
            intent.putExtra(Constants.RECIPE_NAME, mRecipeName);
            startActivity(intent);
        }
    }
}

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

    private RecipeDescriptionListFragment mRecipeDescriptionListFragment;

    private RecipeDescriptionSelectedFragment mRecipeDescriptionSelectedFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_description_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getResources().getBoolean(R.bool.tablet)) {

            mRecipeDescriptionListFragment =
                    (RecipeDescriptionListFragment) getSupportFragmentManager().findFragmentById(R.id.f_recipe_steps);

            mRecipeDescriptionSelectedFragment =
                    (RecipeDescriptionSelectedFragment) getSupportFragmentManager().findFragmentById(R.id.f_recipe_step_description);

            if (mRecipeDescriptionListFragment == null) {
                mRecipeDescriptionListFragment = RecipeDescriptionListFragment.getInstance();
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mRecipeDescriptionListFragment, R.id.f_recipe_steps);
            }

            if (mRecipeDescriptionSelectedFragment == null) {
                mRecipeDescriptionSelectedFragment = RecipeDescriptionSelectedFragment.getInstance();
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mRecipeDescriptionSelectedFragment, R.id.f_recipe_step_description);
            }

        } else {
            mRecipeDescriptionListFragment =
                    (RecipeDescriptionListFragment) getSupportFragmentManager().findFragmentById(R.id.fl_content_frame);

            if (mRecipeDescriptionListFragment == null) {
                mRecipeDescriptionListFragment = RecipeDescriptionListFragment.getInstance();
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mRecipeDescriptionListFragment, R.id.fl_content_frame);
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
            startActivity(intent);
        }
    }
}

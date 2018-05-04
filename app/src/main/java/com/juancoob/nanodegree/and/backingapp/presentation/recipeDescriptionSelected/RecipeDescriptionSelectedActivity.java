package com.juancoob.nanodegree.and.backingapp.presentation.recipeDescriptionSelected;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.juancoob.nanodegree.and.backingapp.R;
import com.juancoob.nanodegree.and.backingapp.util.ActivityUtils;
import com.juancoob.nanodegree.and.backingapp.util.Constants;

/**
 * Created by Juan Antonio Cobos Obrero on 29/04/18.
 */
public class RecipeDescriptionSelectedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_description_selected);

        Intent intent = getIntent();

        RecipeDescriptionSelectedFragment recipeDescriptionSelectedFragment =
                (RecipeDescriptionSelectedFragment) getSupportFragmentManager().findFragmentById(R.id.fl_content_frame);
        if(recipeDescriptionSelectedFragment == null) {
            recipeDescriptionSelectedFragment = RecipeDescriptionSelectedFragment.getInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), recipeDescriptionSelectedFragment, R.id.fl_content_frame);
        }

        if(intent.hasExtra(Constants.SELECTED_STEP_POSITION) && intent.hasExtra(Constants.RECIPE_NAME)) {
            int selectedStepPosition = intent.getIntExtra(Constants.SELECTED_STEP_POSITION,
                    getResources().getInteger(R.integer.default_number));
            recipeDescriptionSelectedFragment.setSelectedStepPosition(selectedStepPosition);
            setTitle(intent.getStringExtra(Constants.RECIPE_NAME));
        }
    }
}

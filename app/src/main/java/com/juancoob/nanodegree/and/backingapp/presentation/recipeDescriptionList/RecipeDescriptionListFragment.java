package com.juancoob.nanodegree.and.backingapp.presentation.recipeDescriptionList;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juancoob.nanodegree.and.backingapp.R;
import com.juancoob.nanodegree.and.backingapp.adapter.IRecipeIngredientsAdapter;
import com.juancoob.nanodegree.and.backingapp.adapter.IRecipeStepsAdapter;
import com.juancoob.nanodegree.and.backingapp.adapter.Impl.IngredientsAdapter;
import com.juancoob.nanodegree.and.backingapp.adapter.Impl.StepsAdapter;
import com.juancoob.nanodegree.and.backingapp.repository.RecipesRepository;
import com.juancoob.nanodegree.and.backingapp.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Juan Antonio Cobos Obrero on 28/04/18.
 */
public class RecipeDescriptionListFragment extends Fragment implements IRecipeDescriptionListContract.View {

    @BindView(R.id.rv_ingredients)
    public RecyclerView ingredientsRecyclerView;

    @BindView(R.id.rv_steps)
    public RecyclerView stepsRecyclerView;

    private LinearLayoutManager mIngredientsLinearLayoutManager;
    private LinearLayoutManager mStepsLinearLayoutManager;
    private IRecipeIngredientsAdapter mIngredientsAdapter;
    private IRecipeStepsAdapter mStepsAdapter;
    private Parcelable mCurrentIngredientRecyclerViewState;
    private Parcelable mCurrentStepRecyclerViewState;
    private IRecipeDescriptionListContract mIRecipeDescriptionListContract;

    public static RecipeDescriptionListFragment getInstance() {
        return new RecipeDescriptionListFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_description_list, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constants.CURRENT_INGREDIENT_POSITION, mIngredientsLinearLayoutManager.onSaveInstanceState());
        outState.putParcelable(Constants.CURRENT_STEP_POSITION, mStepsLinearLayoutManager.onSaveInstanceState());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            mCurrentIngredientRecyclerViewState = savedInstanceState.getParcelable(Constants.CURRENT_INGREDIENT_POSITION);
            mCurrentStepRecyclerViewState = savedInstanceState.getParcelable(Constants.CURRENT_STEP_POSITION);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initIngredientsRecyclerView();
        initStepsRecyclerView();
    }

    private void initIngredientsRecyclerView() {
        mIngredientsLinearLayoutManager = new LinearLayoutManager(getContext());
        ingredientsRecyclerView.setLayoutManager(mIngredientsLinearLayoutManager);
        mIngredientsAdapter = new IngredientsAdapter(getContext());
        ingredientsRecyclerView.setAdapter((RecyclerView.Adapter) mIngredientsAdapter);
    }

    private void initStepsRecyclerView() {
        mStepsLinearLayoutManager = new LinearLayoutManager(getContext());
        stepsRecyclerView.setLayoutManager(mStepsLinearLayoutManager);
        mStepsAdapter = new StepsAdapter(getContext(), mIRecipeDescriptionListContract);
        stepsRecyclerView.setAdapter((RecyclerView.Adapter) mStepsAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        showIngredients();
        showSteps();
    }

    @Override
    public void showIngredients() {
        mIngredientsAdapter.updateIngredients(RecipesRepository.getInstance().getRecipeIngredients());
        if(mCurrentIngredientRecyclerViewState != null) {
            mIngredientsLinearLayoutManager.onRestoreInstanceState(mCurrentIngredientRecyclerViewState);
        }
    }

    @Override
    public void showSteps() {
        mStepsAdapter.updateSteps(RecipesRepository.getInstance().getRecipeSteps());
        if(mCurrentStepRecyclerViewState != null) {
            mStepsLinearLayoutManager.onRestoreInstanceState(mCurrentStepRecyclerViewState);
        }
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void noInternetConnection() {
        if(getContext() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.no_internet_title)
                    .setMessage(R.string.no_internet_description_two)
                    .setIcon(R.drawable.ic_signal_wifi_off_black_24dp)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof IRecipeDescriptionListContract) {
            mIRecipeDescriptionListContract = (IRecipeDescriptionListContract) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mIRecipeDescriptionListContract = null;
    }

}

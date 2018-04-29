package com.juancoob.nanodegree.and.backingapp.presentation.recipeList;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.juancoob.nanodegree.and.backingapp.R;
import com.juancoob.nanodegree.and.backingapp.adapter.IRecipeListAdapterContract;
import com.juancoob.nanodegree.and.backingapp.adapter.Impl.RecipeListAdapter;
import com.juancoob.nanodegree.and.backingapp.domain.model.Recipe;
import com.juancoob.nanodegree.and.backingapp.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Juan Antonio Cobos Obrero on 23/04/18.
 */
public class RecipeListFragment extends Fragment implements com.juancoob.nanodegree.and.backingapp.presentation.recipeList.IRecipeListContract.View {

    @BindView(R.id.rv_recipes)
    public RecyclerView recipesRecyclerView;

    @BindView(R.id.pb_recipe)
    public ProgressBar progressBarRecipe;

    private com.juancoob.nanodegree.and.backingapp.presentation.recipeList.RecipeListPresenter mRecipeListPresenter;
    private List<Recipe> mRecipes = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutManager;
    private IRecipeListAdapterContract mAdapter;
    private IRecipeListContract mIRecipeListContract;
    private Parcelable mCurrentRecyclerViewState;

    public static RecipeListFragment getInstance() {
        return new RecipeListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initRecipesRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecipeListPresenter.resume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Constants.RECIPE_LIST, (ArrayList<? extends Parcelable>) mRecipes);
        if (getResources().getBoolean(R.bool.tablet)) {
            outState.putParcelable(Constants.CURRENT_GRID_POSITION, mGridLayoutManager.onSaveInstanceState());
        } else {
            outState.putParcelable(Constants.CURRENT_LINEAR_POSITION, mLinearLayoutManager.onSaveInstanceState());
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mRecipes = savedInstanceState.getParcelableArrayList(Constants.RECIPE_LIST);
            if (getResources().getBoolean(R.bool.tablet)) {
                mCurrentRecyclerViewState = savedInstanceState.getParcelable(Constants.CURRENT_GRID_POSITION);
            } else {
                mCurrentRecyclerViewState = savedInstanceState.getParcelable(Constants.CURRENT_LINEAR_POSITION);
            }
        }

    }

    private void initRecipesRecyclerView() {
        if (getResources().getBoolean(R.bool.tablet)) {
            mGridLayoutManager = new GridLayoutManager(getContext(), getNumberColumns());
            recipesRecyclerView.setLayoutManager(mGridLayoutManager);
        } else {
            mLinearLayoutManager = new LinearLayoutManager(getContext());
            recipesRecyclerView.setLayoutManager(mLinearLayoutManager);
        }
        mAdapter = new RecipeListAdapter(getContext(), mIRecipeListContract);
        recipesRecyclerView.setAdapter((RecyclerView.Adapter) mAdapter);
    }

    private int getNumberColumns() {
        if (getActivity() != null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;
            int columns = width / getResources().getInteger(R.integer.width_divider);
            if (columns >= 2) return columns;
        }
        return 2;
    }

    @Override
    public void showRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
        mAdapter.updateRecipes(mRecipes);
        if (mCurrentRecyclerViewState != null) {
            if (getResources().getBoolean(R.bool.tablet)) {
                mGridLayoutManager.onRestoreInstanceState(mCurrentRecyclerViewState);
            } else {
                mLinearLayoutManager.onRestoreInstanceState(mCurrentRecyclerViewState);
            }
        }
    }

    @Override
    public void showProgress() {
        progressBarRecipe.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBarRecipe.setVisibility(View.GONE);
    }

    @Override
    public void noInternetConnection() {
        if(getContext() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.no_internet_title)
                    .setMessage(R.string.no_internet_description)
                    .setIcon(R.drawable.ic_signal_wifi_off_black_24dp)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mRecipeListPresenter.fetchRecipes();
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
        }
    }

    public void setPresenter(com.juancoob.nanodegree.and.backingapp.presentation.recipeList.RecipeListPresenter presenter) {
        this.mRecipeListPresenter = presenter;
    }

    public List<Recipe> getRecipes() {
        return mRecipes;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IRecipeListContract) {
            mIRecipeListContract = (IRecipeListContract) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mIRecipeListContract = null;
    }
}

package com.juancoob.nanodegree.and.backingapp.presentation.recipeDescriptionSelected;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.juancoob.nanodegree.and.backingapp.R;
import com.juancoob.nanodegree.and.backingapp.domain.model.Step;
import com.juancoob.nanodegree.and.backingapp.repository.RecipesRepository;
import com.juancoob.nanodegree.and.backingapp.util.Constants;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by Juan Antonio Cobos Obrero on 29/04/18.
 */
public class RecipeDescriptionSelectedFragment extends Fragment implements IRecipeDescriptionSelectedContract.View {

    @BindView(R.id.sepv_recipe_video)
    public PlayerView recipeVideoPlayerView;

    @BindView(R.id.tv_no_video)
    public TextView noVideoTextView;

    @BindView(R.id.iv_thumbnail)
    public ImageView thumbnailImageView;

    @Nullable
    @BindView(R.id.cv_recipe_step_description)
    public CardView recipeStepDescriptionCardView;

    @Nullable
    @BindView(R.id.tv_step_description)
    public TextView stepDescriptionTextView;

    @Nullable
    @BindView(R.id.fab_next)
    public FloatingActionButton nextFloatingActionButton;

    @Nullable
    @BindView(R.id.fab_previous)
    public FloatingActionButton previousFloatingActionButton;

    private int mSelectedStepPosition = 0;
    private SimpleExoPlayer mSimpleExoPlayer;
    private boolean mPlayWhenReady = true;
    private int mCurrentWindow = 0;
    private Long mPlayBackPosition = 0L;

    public static RecipeDescriptionSelectedFragment getInstance(){
        return new RecipeDescriptionSelectedFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_description_selected, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.SELECTED_STEP_POSITION, mSelectedStepPosition);
        // If releaseExoPlayer was called in onPause, use mPlayBackPosition
        if(Util.SDK_INT <= 23) {
            outState.putLong(Constants.PLAYBACK_POSITION, mPlayBackPosition);
        } else {
            outState.putLong(Constants.PLAYBACK_POSITION, mSimpleExoPlayer.getCurrentPosition());
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            mSelectedStepPosition = savedInstanceState.getInt(Constants.SELECTED_STEP_POSITION);
            mPlayBackPosition = savedInstanceState.getLong(Constants.PLAYBACK_POSITION);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // API level 24 and newer supports multiple windows and the app can be visible, but not active on a split window mode, so it's important initialize the player in onStart
        if(Util.SDK_INT > 23) {
            initializeExoPlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // On API 23 and lower we wait up to resources are available to initialize the player
        if(Util.SDK_INT <= 23 || mSimpleExoPlayer == null) {
            initializeExoPlayer();
        }
        showStepDescription();
    }

    private void initializeExoPlayer() {
        mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());
        recipeVideoPlayerView.setPlayer(mSimpleExoPlayer);
        mSimpleExoPlayer.setPlayWhenReady(mPlayWhenReady);
        mSimpleExoPlayer.seekTo(mCurrentWindow, mPlayBackPosition);
    }

    @Override
    public void showStepDescription() {

        Step step = RecipesRepository.getInstance().getRecipeSteps().get(mSelectedStepPosition);
        boolean hasNext = mSelectedStepPosition < RecipesRepository.getInstance().getRecipeSteps().size() - 1;
        boolean hasPrevious = mSelectedStepPosition > 0;

        if(step.getVideoURL().isEmpty()) {
            mSimpleExoPlayer.stop();
            recipeVideoPlayerView.setVisibility(View.INVISIBLE);
            if(step.getThumbnailURL().isEmpty()) {
                noVideoTextView.setVisibility(View.VISIBLE);
            } else {
                Picasso.with(getContext()).load(step.getThumbnailURL()).into(thumbnailImageView);
            }
        } else {
            recipeVideoPlayerView.setVisibility(View.VISIBLE);
            noVideoTextView.setVisibility(View.GONE);
            addVideo(Uri.parse(step.getVideoURL()));
        }

        // Populate the last fields if we are on portrait mode or using a tablet
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT
                || getResources().getBoolean(R.bool.tablet)) {
            if (step.getDescription().isEmpty()) {
                recipeStepDescriptionCardView.setVisibility(View.GONE);
            } else {
                recipeStepDescriptionCardView.setVisibility(View.VISIBLE);
                stepDescriptionTextView.setMovementMethod(new ScrollingMovementMethod());
                stepDescriptionTextView.setText(step.getDescription());
            }

            if(!getResources().getBoolean(R.bool.tablet)) {
                if (hasNext) {
                    nextFloatingActionButton.setVisibility(View.VISIBLE);
                } else {
                    nextFloatingActionButton.setVisibility(View.INVISIBLE);
                }

                if (hasPrevious) {
                    previousFloatingActionButton.setVisibility(View.VISIBLE);
                } else {
                    previousFloatingActionButton.setVisibility(View.INVISIBLE);
                }
            }
        } else {
            recipeVideoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }

    }

    private void addVideo(Uri videoUri) {
        // Add the resource
        MediaSource mediaSource = buildMediaSource(videoUri);
        mSimpleExoPlayer.prepare(mediaSource, mPlayBackPosition == 0, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(Constants.EXOPLAYER)).createMediaSource(uri);
    }

    @Optional
    @OnClick(R.id.fab_next)
    public void onClickNext() {
        mSelectedStepPosition++;
        mPlayBackPosition = 0L;
        showStepDescription();
    }

    @Optional
    @OnClick(R.id.fab_previous)
    public void onClickPrevious() {
        mSelectedStepPosition--;
        mPlayBackPosition = 0L;
        showStepDescription();
    }

    public void goToStep(int selectedStep) {
        mSelectedStepPosition = selectedStep;
        mPlayBackPosition = 0L;
        showStepDescription();
    }

    @Override
    public void onPause() {
        super.onPause();
        // There's no guarantee onStop being called on API 23 or lower, so we need to release the exoPlayer in onPause
        if(Util.SDK_INT <= 23) {
            releaseExoPlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // On API 24 and newer onStop is guaranteed to be called, but in onPause is eventually still visible
        if(Util.SDK_INT > 23) {
            releaseExoPlayer();
        }
    }

    private void releaseExoPlayer() {
        if(mSimpleExoPlayer != null) {
            mPlayBackPosition = mSimpleExoPlayer.getCurrentPosition();
            mCurrentWindow = mSimpleExoPlayer.getCurrentWindowIndex();
            mPlayWhenReady = mSimpleExoPlayer.getPlayWhenReady();
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
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
    }

    public void setSelectedStepPosition(int selectedStepPosition) {
        this.mSelectedStepPosition = selectedStepPosition;
    }

    public int getSelectedStepPosition() {
        return mSelectedStepPosition;
    }
}

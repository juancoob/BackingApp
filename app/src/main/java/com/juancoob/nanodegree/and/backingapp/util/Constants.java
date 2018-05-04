package com.juancoob.nanodegree.and.backingapp.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * Created by Juan Antonio Cobos Obrero on 18/04/18.
 */
public final class Constants {
    public static final int CORE_POOL_SIZE = 3;
    public static final int MAXIMUN_POOL_SIZE = 5;
    public static final long KEEP_ALIVE_TIME = 120L;
    public static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    public static final BlockingQueue<Runnable> WORK_QUEUE = new LinkedBlockingDeque<>();
    public static final String URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    public static final String RECIPE_LIST = "recipeList";
    public static final String RECIPE_NAME = "recipeName";
    public static final String CURRENT_GRID_POSITION = "currentGridPosition";
    public static final String CURRENT_LINEAR_POSITION = "currentLinearPosition";
    public static final String CURRENT_INGREDIENT_POSITION = "currentIngredientPosition";
    public static final String CURRENT_STEP_POSITION = "currentStepPosition";
    public static final String SELECTED_STEP_POSITION = "selectedStepPosition";
    public static final String EXOPLAYER = "exoplayer";
    public static final String PLAYBACK_POSITION = "playbackPosition";

}

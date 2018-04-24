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

}

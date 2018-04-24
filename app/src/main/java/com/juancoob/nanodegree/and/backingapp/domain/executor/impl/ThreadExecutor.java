package com.juancoob.nanodegree.and.backingapp.domain.executor.impl;

import com.juancoob.nanodegree.and.backingapp.domain.executor.Executor;
import com.juancoob.nanodegree.and.backingapp.domain.usecase.base.AbstractUseCase;
import com.juancoob.nanodegree.and.backingapp.util.Constants;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Juan Antonio Cobos Obrero on 17/04/18.
 */
public class ThreadExecutor implements Executor {

    private static final ThreadExecutor sThreadExecutor = new ThreadExecutor();
    private final ThreadPoolExecutor mThreadPoolExecutor;

    public static ThreadExecutor getInstance() {
        return sThreadExecutor;
    }

    private ThreadExecutor() {
        mThreadPoolExecutor = new ThreadPoolExecutor(
                Constants.CORE_POOL_SIZE,
                Constants.MAXIMUN_POOL_SIZE,
                Constants.KEEP_ALIVE_TIME,
                Constants.TIME_UNIT,
                Constants.WORK_QUEUE);
    }

    @Override
    public void executor(final AbstractUseCase useCase) {
        mThreadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                useCase.run();
                useCase.onFinished();
            }
        });
    }
}

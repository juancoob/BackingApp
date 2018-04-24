package com.juancoob.nanodegree.and.backingapp.presentation.base.presenters;

import com.juancoob.nanodegree.and.backingapp.domain.executor.Executor;
import com.juancoob.nanodegree.and.backingapp.domain.threading.MainThread;

/**
 * Created by Juan Antonio Cobos Obrero on 18/04/18.
 */
public abstract class AbstractPresenter {

    protected final Executor mExecutor;
    protected final MainThread mMainThread;

    public AbstractPresenter(Executor executor, MainThread mainThread) {
        mExecutor = executor;
        mMainThread = mainThread;
    }
}

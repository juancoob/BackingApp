package com.juancoob.nanodegree.and.backingapp.domain.usecase.base;

import com.juancoob.nanodegree.and.backingapp.domain.executor.Executor;
import com.juancoob.nanodegree.and.backingapp.domain.threading.MainThread;
import com.juancoob.nanodegree.and.backingapp.domain.usecase.base.UseCase;

/**
 * Created by Juan Antonio Cobos Obrero on 17/04/18.
 */
public abstract class AbstractUseCase implements UseCase {

    protected final Executor mThreadExecutor;
    protected final MainThread mMainthread;

    protected volatile boolean mIsCanceled;
    protected volatile boolean mIsRunning;

    public AbstractUseCase(Executor executor, MainThread mainThread) {
        mThreadExecutor = executor;
        mMainthread = mainThread;
    }

    public abstract void run();

    public void cancel() {
        mIsCanceled = true;
        mIsRunning = false;
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    public void onFinished() {
        mIsRunning = false;
        mIsCanceled = false;
    }

    @Override
    public void execute() {
        mIsRunning = true;
        mThreadExecutor.executor(this);
    }
}

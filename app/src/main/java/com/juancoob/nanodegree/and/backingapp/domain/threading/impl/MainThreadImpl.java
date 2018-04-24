package com.juancoob.nanodegree.and.backingapp.domain.threading.impl;

import android.os.Handler;
import android.os.Looper;

import com.juancoob.nanodegree.and.backingapp.domain.threading.MainThread;


/**
 * Created by Juan Antonio Cobos Obrero on 17/04/18.
 */
public class MainThreadImpl implements MainThread {

    private static final MainThread sMainThread = new MainThreadImpl();
    private final Handler mHandler;

    private MainThreadImpl() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static MainThread getInstance() {
        return sMainThread;
    }


    @Override
    public void post(Runnable runnable) {
        mHandler.post(runnable);
    }
}

package com.juancoob.nanodegree.and.backingapp.domain.threading;

/**
 * Created by Juan Antonio Cobos Obrero on 17/04/18.
 */
public interface MainThread {
    void post(final Runnable runnable);
}

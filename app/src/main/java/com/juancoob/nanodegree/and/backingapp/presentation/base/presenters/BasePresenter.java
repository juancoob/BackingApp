package com.juancoob.nanodegree.and.backingapp.presentation.base.presenters;

/**
 * Created by Juan Antonio Cobos Obrero on 18/04/18.
 */
public interface BasePresenter {
    /* These methods control the view's lifecycle*/

    void resume();
    void pause();
    void stop();
    void destroy();

    /*This method pass the error message to the UI*/
    void onError(String message);
}

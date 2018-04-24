package com.juancoob.nanodegree.and.backingapp.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Juan Antonio Cobos Obrero on 23/04/18.
 */
public final class ActivityUtils {

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int fragmentId) {
        if(fragmentManager != null && fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(fragmentId, fragment);
            transaction.commit();
        }
    }

}

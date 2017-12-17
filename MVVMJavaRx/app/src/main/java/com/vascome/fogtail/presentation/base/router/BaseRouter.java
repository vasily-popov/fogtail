package com.vascome.fogtail.presentation.base.router;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.vascome.fogtail.presentation.base.activity.BaseActivity;

/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

abstract public class BaseRouter<A extends BaseActivity> {
    private A activity;
    private FragmentManager fragmentManager;

    public BaseRouter(A activity) {
        this.activity = activity;
        this.fragmentManager = activity.getSupportFragmentManager();
    }


    public void replaceFragment(int content, Fragment fragment) {

        fragmentManager.beginTransaction()
                .replace(content, fragment)
                .commit();
    }

    public A getActivity() {
        return activity;
    }
}

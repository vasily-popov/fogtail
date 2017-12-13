package com.vascome.fogtail.presentation.base.fragments;

import android.support.annotation.NonNull;

import com.vascome.fogtail.utils.LeakCanaryProxy;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public abstract class BaseFragment extends DaggerFragment {

    @Inject
    LeakCanaryProxy leakCanaryProxy;

    protected void runIfFragmentAlive(@NonNull Runnable runnable) {
        if (isFragmentAlive()) {
            runnable.run();
        }
    }

    private boolean isFragmentAlive() {
        return getActivity() != null && isAdded() && !isDetached() && getView() != null && !isRemoving();
    }

    @Override
    public void onDestroy() {
        leakCanaryProxy.watch(this);
        super.onDestroy();
    }
}

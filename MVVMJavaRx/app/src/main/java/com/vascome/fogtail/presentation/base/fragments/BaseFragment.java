package com.vascome.fogtail.presentation.base.fragments;

import android.support.annotation.NonNull;

import com.vascome.fogtail.utils.LeakCanaryProxy;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public abstract class BaseFragment extends DaggerFragment {

    @Inject
    LeakCanaryProxy leakCanaryProxy;

    protected CompositeDisposable disposables = new CompositeDisposable();

    protected void runIfFragmentAlive(@NonNull Runnable runnable) {
        if (isFragmentAlive()) {
            runnable.run();
        }
    }

    private boolean isFragmentAlive() {
        return getActivity() != null && isAdded() && !isDetached() && getView() != null && !isRemoving();
    }

    @Override
    public void onPause() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        disposables = new CompositeDisposable();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        leakCanaryProxy.watch(this);
        super.onDestroy();
    }
}

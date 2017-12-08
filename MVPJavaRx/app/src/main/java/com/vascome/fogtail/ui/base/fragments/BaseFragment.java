package com.vascome.fogtail.ui.base.fragments;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.vascome.fogtail.FogtailApplication;
import com.vascome.fogtail.appmodules.ApplicationModule;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public abstract class BaseFragment extends Fragment {

    @Inject
    @Named(ApplicationModule.MAIN_THREAD_HANDLER)
    Handler mainThreadHandler;

    protected void runOnUiThreadIfFragmentAlive(@NonNull Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper() && isFragmentAlive()) {
            runnable.run();
        } else {
            mainThreadHandler.post(() -> {
                if (isFragmentAlive()) {
                    runnable.run();
                }
            });
        }
    }

    private boolean isFragmentAlive() {
        return getActivity() != null && isAdded() && !isDetached() && getView() != null && !isRemoving();
    }

    @Override
    public void onDestroy() {
        FogtailApplication.get(getContext()).appComponent().leakCanaryProxy().watch(this);
        super.onDestroy();
    }
}

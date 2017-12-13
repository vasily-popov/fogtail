package com.vascome.fogtail.presentation.base.fragments;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.vascome.fogtail.FogtailApplication;


/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public abstract class BaseFragment extends Fragment {

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
        FogtailApplication.get(getContext()).appComponent().leakCanaryProxy().watch(this);
        super.onDestroy();
    }
}

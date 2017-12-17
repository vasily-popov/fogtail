package com.vascome.fogtail.presentation.dev_settings.presenters;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by vasilypopov on 12/17/17
 * Copyright (c) 2017 MVVMJavaRx. All rights reserved.
 */

public abstract class BasePresenter<V> {

    @Nullable
    private volatile V view;

    @CallSuper
    public void bindView(@NonNull V view) {
        final V previousView = this.view;

        if (previousView != null) {
            throw new IllegalStateException("Previous view is not unbounded! previousView = " + previousView);
        }

        this.view = view;
    }

    @Nullable
    protected V view() {
        return view;
    }

    @CallSuper
    @SuppressWarnings("PMD.CompareObjectsWithEquals")
    public void unbindView(@NonNull V view) {
        final V previousView = this.view;

        if (previousView == view) {
            this.view = null;
        } else {
            throw new IllegalStateException("Unexpected view! previousView = " + previousView + ", view to unbind = " + view);
        }
    }
}
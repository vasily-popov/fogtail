package com.vascome.fogtail.developer_settings;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public class LeakCanaryProxyImpl implements LeakCanaryProxy {

    @NonNull
    private final Application qualityMattersApp;

    @Nullable
    private RefWatcher refWatcher;

    public LeakCanaryProxyImpl(@NonNull Application qualityMattersApp) {
        this.qualityMattersApp = qualityMattersApp;
    }

    @Override
    public void init() {
        refWatcher = LeakCanary.install(qualityMattersApp);
    }

    @Override
    public void watch(@NonNull Object object) {
        if (refWatcher != null) {
            refWatcher.watch(object);
        }
    }
}

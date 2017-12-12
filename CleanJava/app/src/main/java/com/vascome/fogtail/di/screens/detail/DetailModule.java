package com.vascome.fogtail.di.screens.detail;

import android.support.annotation.NonNull;

import com.vascome.fogtail.di.ActivityScope;
import com.vascome.fogtail.screens.base.router.BaseRouter;
import com.vascome.fogtail.screens.detail.DetailRouter;
import com.vascome.fogtail.screens.detail.RecAreaItemDetailActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

@Module
public class DetailModule {

    private RecAreaItemDetailActivity activity;

    public DetailModule(RecAreaItemDetailActivity activity) {
        this.activity = activity;
    }

    @Provides
    @NonNull
    @ActivityScope
    public BaseRouter provideRouter() {
        return new DetailRouter(activity);
    }
}



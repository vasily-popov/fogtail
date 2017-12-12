package com.vascome.fogtail.di.screens.main;

import android.support.annotation.NonNull;

import com.vascome.fogtail.api.FogtailRestApi;
import com.vascome.fogtail.di.ActivityScope;
import com.vascome.fogtail.screens.main.CollectionContract;
import com.vascome.fogtail.screens.main.CollectionRouter;
import com.vascome.fogtail.screens.main.MainActivity;
import com.vascome.fogtail.screens.main.RecAreaItemsModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */
@Module
public class CollectionModule {

    private MainActivity activity;

    public CollectionModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    @NonNull
    @ActivityScope
    public RecAreaItemsModel provideItemsModel(@NonNull FogtailRestApi fogtailRestApi) {
        return new RecAreaItemsModel(fogtailRestApi);
    }

    @Provides
    @NonNull
    @ActivityScope
    public CollectionContract.Router provideRouter() {
        return new CollectionRouter(activity);
    }
}
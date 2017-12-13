package com.vascome.fogtail.di.presentation.main;

import android.support.annotation.NonNull;

import com.vascome.fogtail.data.api.FogtailRestApi;
import com.vascome.fogtail.data.gateway.ItemsDataSource;
import com.vascome.fogtail.data.gateway.RecAreaGateway;
import com.vascome.fogtail.di.ActivityScope;
import com.vascome.fogtail.presentation.main.CollectionContract;
import com.vascome.fogtail.presentation.main.CollectionRouter;
import com.vascome.fogtail.presentation.main.MainActivity;

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
    public ItemsDataSource provideItemsDataSource(@NonNull FogtailRestApi fogtailRestApi) {
        return new RecAreaGateway(fogtailRestApi);
    }

    @Provides
    @NonNull
    @ActivityScope
    public CollectionContract.Router provideRouter() {
        return new CollectionRouter(activity);
    }

}
package com.vascome.fogtail.di.ui.main;

import android.support.annotation.NonNull;

import com.vascome.fogtail.api.FogtailRestApi;
import com.vascome.fogtail.di.ActivityScope;
import com.vascome.fogtail.ui.main.collectionbase.RecAreaItemsModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */
@Module
public class CollectionModule {

    @Provides
    @NonNull
    @ActivityScope
    public RecAreaItemsModel provideItemsModel(@NonNull FogtailRestApi fogtailRestApi) {
        return new RecAreaItemsModel(fogtailRestApi);
    }

}
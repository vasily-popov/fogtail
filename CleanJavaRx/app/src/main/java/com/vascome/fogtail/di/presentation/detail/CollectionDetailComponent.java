package com.vascome.fogtail.di.presentation.detail;

import android.support.annotation.NonNull;

import com.vascome.fogtail.di.ActivityScope;
import com.vascome.fogtail.presentation.detail.RecAreaItemDetailActivity;

import dagger.Subcomponent;

/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */


@ActivityScope
@Subcomponent(modules = { DetailModule.class})
public interface CollectionDetailComponent {

    @NonNull
    DetailFragmentComponent detailComponent();

    @Subcomponent.Builder
    interface Builder {
        CollectionDetailComponent.Builder detailModule(DetailModule module);
        CollectionDetailComponent build();
    }

    void inject(@NonNull RecAreaItemDetailActivity activity);
}

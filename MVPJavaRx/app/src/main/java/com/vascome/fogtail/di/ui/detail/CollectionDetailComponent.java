package com.vascome.fogtail.di.ui.detail;

import android.support.annotation.NonNull;

import com.vascome.fogtail.di.ActivityScope;
import com.vascome.fogtail.ui.detail.RecAreaItemDetailActivity;

import dagger.Subcomponent;

/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */


@Subcomponent
@ActivityScope
public interface CollectionDetailComponent {

    @NonNull
    DetailFragmentComponent detailComponent();

    @Subcomponent.Builder
    interface Builder {
        CollectionDetailComponent build();
    }

    void inject(@NonNull RecAreaItemDetailActivity activity);
}

package com.vascome.fogtail.di.presentation.main;

import android.support.annotation.NonNull;

import com.vascome.fogtail.di.ActivityScope;
import com.vascome.fogtail.di.presentation.main.carousel.CarouselFragmentComponent;
import com.vascome.fogtail.di.presentation.main.gallery.GalleryFragmentComponent;
import com.vascome.fogtail.di.presentation.main.list.ListFragmentComponent;
import com.vascome.fogtail.di.presentation.main.stack.StackFragmentComponent;
import com.vascome.fogtail.di.presentation.main.table.GridFragmentComponent;
import com.vascome.fogtail.presentation.main.MainActivity;

import dagger.Subcomponent;

/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */

@Subcomponent(modules = { CollectionModule.class})
@ActivityScope
public interface CollectionComponent {

    @NonNull
    ListFragmentComponent listComponent();

    @NonNull
    GridFragmentComponent gridComponent();

    @NonNull
    StackFragmentComponent stackComponent();

    @NonNull
    GalleryFragmentComponent galleryComponent();

    @NonNull
    CarouselFragmentComponent carouselComponent();

    @Subcomponent.Builder
    interface Builder {
        CollectionComponent.Builder collectionModule(CollectionModule module);
        CollectionComponent build();
    }


    void inject(@NonNull MainActivity mainActivity);
}


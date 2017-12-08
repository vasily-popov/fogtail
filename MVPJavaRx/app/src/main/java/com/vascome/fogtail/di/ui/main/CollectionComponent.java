package com.vascome.fogtail.di.ui.main;

import android.support.annotation.NonNull;

import com.vascome.fogtail.di.ActivityScope;
import com.vascome.fogtail.di.ui.main.carousel.CarouselFragmentComponent;
import com.vascome.fogtail.di.ui.main.gallery.GalleryFragmentComponent;
import com.vascome.fogtail.di.ui.main.list.ListFragmentComponent;
import com.vascome.fogtail.di.ui.main.stack.StackFragmentComponent;
import com.vascome.fogtail.di.ui.main.table.GridFragmentComponent;
import com.vascome.fogtail.ui.main.MainActivity;

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
        CollectionComponent.Builder collectionModule(CollectionModule firstModule);
        CollectionComponent build();
    }


    void inject(@NonNull MainActivity mainActivity);
}


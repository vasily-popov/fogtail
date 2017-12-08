package com.vascome.fogtail.di.ui.main.gallery;

import android.support.annotation.NonNull;

import com.vascome.fogtail.di.FragmentScope;
import com.vascome.fogtail.di.ui.main.basemodule.CollectionFragmentModule;
import com.vascome.fogtail.ui.main.gallery.GalleryAppFragment;

import dagger.Subcomponent;

/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */

@Subcomponent(modules = { CollectionFragmentModule.class})
@FragmentScope
public interface GalleryFragmentComponent {

    void inject(@NonNull GalleryAppFragment itemsFragment);
}


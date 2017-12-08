package com.vascome.fogtail.di.ui.main.carousel;

import android.support.annotation.NonNull;

import com.vascome.fogtail.di.FragmentScope;
import com.vascome.fogtail.di.ui.main.CollectionComponent;
import com.vascome.fogtail.di.ui.main.basemodule.CollectionFragmentModule;
import com.vascome.fogtail.ui.main.carousel.CarouselAppFragment;

import dagger.Component;

/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */

@Component(dependencies = CollectionComponent.class,
        modules = {
                CollectionFragmentModule.class,
        })
@FragmentScope
public interface CarouselFragmentComponent {
    void inject(@NonNull CarouselAppFragment itemsFragment);
}
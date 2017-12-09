package com.vascome.fogtail.di.ui.main;

import com.vascome.fogtail.di.FragmentScope;
import com.vascome.fogtail.ui.main.carousel.CarouselAppFragment;
import com.vascome.fogtail.ui.main.gallery.GalleryAppFragment;
import com.vascome.fogtail.ui.main.list.ListAppFragment;
import com.vascome.fogtail.ui.main.stack.StackAppFragment;
import com.vascome.fogtail.ui.main.table.GridAppFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */
@Module
public abstract class MainActivityModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract ListAppFragment listAppFragment();

    @FragmentScope
    @ContributesAndroidInjector
    abstract GridAppFragment gridAppFragment();


    @FragmentScope
    @ContributesAndroidInjector
    abstract GalleryAppFragment galleryAppFragment();


    @FragmentScope
    @ContributesAndroidInjector
    abstract StackAppFragment stackAppFragment();


    @FragmentScope
    @ContributesAndroidInjector
    abstract CarouselAppFragment carouselAppFragment();
}
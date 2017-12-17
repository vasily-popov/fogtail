package com.vascome.fogtail.di.presentation.main;

import android.support.annotation.NonNull;

import com.vascome.fogtail.data.gateway.ItemsDataSource;
import com.vascome.fogtail.data.gateway.RecAreaGateway;
import com.vascome.fogtail.di.ActivityScope;
import com.vascome.fogtail.di.FragmentScope;
import com.vascome.fogtail.presentation.main.fragment.carousel.CarouselAppFragment;
import com.vascome.fogtail.presentation.main.fragment.gallery.GalleryAppFragment;
import com.vascome.fogtail.presentation.main.fragment.list.ListAppFragment;
import com.vascome.fogtail.presentation.main.fragment.stack.StackAppFragment;
import com.vascome.fogtail.presentation.main.fragment.table.GridAppFragment;

import dagger.Binds;
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

    @Binds
    @NonNull
    @ActivityScope
    public abstract ItemsDataSource provideMainActivityDataSource(RecAreaGateway gateway);

}
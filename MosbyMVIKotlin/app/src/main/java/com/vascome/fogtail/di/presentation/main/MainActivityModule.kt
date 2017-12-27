package com.vascome.fogtail.di.presentation.main

import com.vascome.fogtail.data.gateway.ItemsDataSource
import com.vascome.fogtail.data.gateway.RecAreaGateway
import com.vascome.fogtail.di.ActivityScope
import com.vascome.fogtail.di.FragmentScope
import com.vascome.fogtail.presentation.main.fragment.carousel.CarouselAppFragment
import com.vascome.fogtail.presentation.main.fragment.gallery.GalleryAppFragment
import com.vascome.fogtail.presentation.main.fragment.list.ListAppFragment
import com.vascome.fogtail.presentation.main.fragment.stack.StackAppFragment
import com.vascome.fogtail.presentation.main.fragment.table.GridAppFragment

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */
@Module
abstract class MainActivityModule {

    /*private MainActivity activity;

    public MainActivityModule(MainActivity activity) {
        this.activity = activity;
    }
    */


    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun listAppFragment(): ListAppFragment

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun gridAppFragment(): GridAppFragment


    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun galleryAppFragment(): GalleryAppFragment


    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun stackAppFragment(): StackAppFragment


    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun carouselAppFragment(): CarouselAppFragment

    @Binds
    @ActivityScope
    abstract fun provideMainActivityDataSource(gateway: RecAreaGateway): ItemsDataSource

}
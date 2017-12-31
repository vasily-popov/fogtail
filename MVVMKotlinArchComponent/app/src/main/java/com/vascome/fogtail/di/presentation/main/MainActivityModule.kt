package com.vascome.fogtail.di.presentation.main

import android.arch.lifecycle.ViewModel
import com.vascome.fogtail.data.gateway.ItemsDataSource
import com.vascome.fogtail.data.gateway.RecAreaGateway
import com.vascome.fogtail.di.ActivityScope
import com.vascome.fogtail.di.FragmentScope
import com.vascome.fogtail.di.ViewModelKey
import com.vascome.fogtail.presentation.main.CollectionViewModel
import com.vascome.fogtail.presentation.main.fragment.carousel.CarouselAppFragment
import com.vascome.fogtail.presentation.main.fragment.gallery.GalleryAppFragment
import com.vascome.fogtail.presentation.main.fragment.list.ListAppFragment
import com.vascome.fogtail.presentation.main.fragment.stack.StackAppFragment
import com.vascome.fogtail.presentation.main.fragment.table.GridAppFragment
import dagger.Binds

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap


/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */
@Module(includes = [(CollectionViewModelsModule::class)])
abstract class MainActivityModule {

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
    @IntoMap
    @ViewModelKey(CollectionViewModel::class)
    internal abstract fun bindCollectionViewModel(viewModel: CollectionViewModel): ViewModel

    @Binds
    @ActivityScope
    abstract fun provideDataSource(gateway: RecAreaGateway): ItemsDataSource
}
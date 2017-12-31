package com.vascome.fogtail.di.presentation.detail

import android.arch.lifecycle.ViewModel
import com.vascome.fogtail.di.FragmentScope
import com.vascome.fogtail.di.ViewModelKey
import com.vascome.fogtail.di.presentation.main.CollectionViewModelsModule
import com.vascome.fogtail.presentation.detail.DetailViewModel
import com.vascome.fogtail.presentation.detail.RecAreaDetailFragment
import dagger.Binds

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

@Module(includes = [(CollectionViewModelsModule::class)])
abstract class DetailActivityModule {

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun detailFragment(): RecAreaDetailFragment

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    internal abstract fun bindDetailViewModel(viewModel: DetailViewModel): ViewModel
}



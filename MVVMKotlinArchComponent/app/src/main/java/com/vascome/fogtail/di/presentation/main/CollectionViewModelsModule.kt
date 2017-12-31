package com.vascome.fogtail.di.presentation.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.vascome.fogtail.data.gateway.ItemsDataSource
import com.vascome.fogtail.data.gateway.RecAreaGateway
import com.vascome.fogtail.di.ActivityScope
import com.vascome.fogtail.di.ViewModelKey
import com.vascome.fogtail.presentation.detail.DetailViewModel
import com.vascome.fogtail.presentation.main.CollectionViewModel
import com.vascome.fogtail.presentation.main.CollectionViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by vasilypopov on 12/30/17
 * Copyright (c) 2017 MVVMKotlinArchComponent. All rights reserved.
 *
 *
 */

@Module
internal abstract class CollectionViewModelsModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: CollectionViewModelFactory): ViewModelProvider.Factory

}


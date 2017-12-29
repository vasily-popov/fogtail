package com.vascome.fogtail.di.presentation.main

import com.vascome.fogtail.data.gateway.ItemsDataSource
import com.vascome.fogtail.data.gateway.RecAreaGateway
import com.vascome.fogtail.di.ActivityScope
import dagger.Binds
import dagger.Module



/**
 * Created by vasilypopov on 12/29/17
 * Copyright (c) 2017 MosbyMVPConductorKotlin. All rights reserved.
 *
 *
 */
@Module
abstract class CollectionModule {

    @Binds
    @ActivityScope
    abstract fun provideDataSource(gateway: RecAreaGateway): ItemsDataSource
}

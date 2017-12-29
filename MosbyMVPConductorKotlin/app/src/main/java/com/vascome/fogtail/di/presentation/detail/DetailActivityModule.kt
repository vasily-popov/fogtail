package com.vascome.fogtail.di.presentation.detail

import com.vascome.fogtail.di.ControllerScope
import com.vascome.fogtail.presentation.detail.DetailController

import dagger.Module

/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

@Module
abstract class DetailActivityModule {

    @ControllerScope
    //@ContributesAndroidInjector
    internal abstract fun detailFragment(): DetailController
}



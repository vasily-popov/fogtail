package com.vascome.fogtail.di.presentation.detail

import com.vascome.fogtail.di.ActivityScope
import com.vascome.fogtail.di.FragmentScope
import com.vascome.fogtail.presentation.base.router.BaseRouter
import com.vascome.fogtail.presentation.detail.DetailRouter
import com.vascome.fogtail.presentation.detail.RecAreaDetailFragment

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

@Module
abstract class DetailActivityModule {

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun detailFragment(): RecAreaDetailFragment
}



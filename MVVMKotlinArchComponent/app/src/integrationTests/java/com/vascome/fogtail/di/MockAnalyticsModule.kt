package com.vascome.fogtail.di

import android.app.Application

import com.vascome.fogtail.utils.AnalyticsModel

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

import org.mockito.Mockito.mock

/**
 * Created by vasilypopov on 12/14/17
 * Copyright (c) 2017 CleanJavaRx. All rights reserved.
 */
@Module
class MockAnalyticsModule {
    @Provides
    @Singleton
    fun provideAnalyticsModel(app: Application): AnalyticsModel {
        return mock(AnalyticsModel::class.java)
    }

}


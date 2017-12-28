package com.vascome.fogtail.di.appmodules

import com.vascome.fogtail.presentation.base.other.NoOpViewModifier
import com.vascome.fogtail.presentation.base.other.ViewModifier
import com.vascome.fogtail.presentation.devsettings.DeveloperSettingsModel
import com.vascome.fogtail.utils.LeakCanaryProxy
import com.vascome.fogtail.utils.NoOpLeakCanaryProxy

import javax.inject.Named
import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

@Module
class DeveloperSettingsModule {

    @Provides
    @Named(MAIN_ACTIVITY_VIEW_MODIFIER)
    fun provideMainActivityViewModifier(): ViewModifier {
        return NoOpViewModifier()
    }

    @Provides
    fun provideDeveloperSettingsModel(): DeveloperSettingsModel=
        object : DeveloperSettingsModel {
            override fun apply() {
            }
        }

    @Provides
    @Singleton
    fun provideLeakCanaryProxy(): LeakCanaryProxy {
        return NoOpLeakCanaryProxy()
    }

    companion object {

        const val MAIN_ACTIVITY_VIEW_MODIFIER = "main_activity_view_modifier"
    }
}
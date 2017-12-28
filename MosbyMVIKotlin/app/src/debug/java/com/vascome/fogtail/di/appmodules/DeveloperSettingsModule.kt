package com.vascome.fogtail.di.appmodules

import android.app.Application
import android.content.Context.MODE_PRIVATE
import com.github.pedrovgs.lynx.LynxConfig
import com.vascome.fogtail.di.presentation.devsettings.DevMenuModule
import com.vascome.fogtail.presentation.base.other.ViewModifier
import com.vascome.fogtail.presentation.devsettings.DeveloperSettingsModel
import com.vascome.fogtail.presentation.devsettings.model.DeveloperSettings
import com.vascome.fogtail.presentation.devsettings.model.DeveloperSettingsModelImpl
import com.vascome.fogtail.presentation.devsettings.views.MainActivityViewModifier
import com.vascome.fogtail.utils.LeakCanaryProxy
import com.vascome.fogtail.utils.LeakCanaryProxyImpl
import dagger.Module
import dagger.Provides
import hu.supercluster.paperwork.Paperwork
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

@Module(includes = [(DevMenuModule::class)])
class DeveloperSettingsModule {

    @Provides
    @Named(MAIN_ACTIVITY_VIEW_MODIFIER)
    fun provideMainActivityViewModifier(): ViewModifier {
        return MainActivityViewModifier()
    }

    @Provides
    fun provideDeveloperSettingsModel(developerSettingsModelImpl: DeveloperSettingsModelImpl): DeveloperSettingsModel {
        return developerSettingsModelImpl
    }

    @Provides
    @Singleton
    fun provideDeveloperSettings(application: Application): DeveloperSettings {
        return DeveloperSettings(application.getSharedPreferences("developer_settings", MODE_PRIVATE))
    }

    @Provides
    @Singleton
    fun provideLeakCanaryProxy(application: Application): LeakCanaryProxy {
        return LeakCanaryProxyImpl(application)
    }

    @Provides
    @Singleton
    fun providePaperwork(application: Application): Paperwork {
        return Paperwork(application)
    }

    @Provides
    fun provideLynxConfig(): LynxConfig {
        return LynxConfig()
    }

    companion object {

        const val MAIN_ACTIVITY_VIEW_MODIFIER = "main_activity_view_modifier"
    }

}
package com.vascome.fogtail.presentation.dev_settings.presenters

import com.vascome.fogtail.presentation.dev_settings.model.DeveloperSettingsModelImpl
import com.vascome.fogtail.presentation.dev_settings.views.DeveloperSettingsView
import com.vascome.fogtail.utils.AnalyticsModel

import javax.inject.Inject

import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class DeveloperSettingsPresenter
@Inject constructor(private val developerSettingsModel: DeveloperSettingsModelImpl,
            private val analyticsModel: AnalyticsModel) : BasePresenter<DeveloperSettingsView>() {


    override fun bindView(view: DeveloperSettingsView) {
        super.bindView(view)
        syncDeveloperSettings()
    }

    fun syncDeveloperSettings() {
        val view = view()
        view?.changeGitSha(developerSettingsModel.gitSha)
        view?.changeBuildDate(developerSettingsModel.buildDate)
        view?.changeBuildVersionCode(developerSettingsModel.buildVersionCode)
        view?.changeBuildVersionName(developerSettingsModel.buildVersionName)
        view?.changeStethoState(developerSettingsModel.isStethoEnabled)
        view?.changeLeakCanaryState(developerSettingsModel.isLeakCanaryEnabled)
        view?.changeTinyDancerState(developerSettingsModel.isTinyDancerEnabled)
        view?.changeHttpLoggingLevel(developerSettingsModel.httpLoggingLevel)
    }

    fun changeStethoState(enabled: Boolean) {
        if (developerSettingsModel.isStethoEnabled == enabled) {
            return  // no-op
        }

        analyticsModel.sendEvent("developer_settings_stetho_" + booleanToEnabledDisabled(enabled))

        val stethoWasEnabled = developerSettingsModel.isStethoEnabled

        developerSettingsModel.changeStethoState(enabled)

        val view = view()
        view?.showMessage("Stetho was " + booleanToEnabledDisabled(enabled))

        if (stethoWasEnabled) {
            view?.showAppNeedsToBeRestarted()
        }
    }

    fun changeLeakCanaryState(enabled: Boolean) {
        if (developerSettingsModel.isLeakCanaryEnabled == enabled) {
            return  // no-op
        }

        analyticsModel.sendEvent("developer_settings_leak_canary_" + booleanToEnabledDisabled(enabled))

        developerSettingsModel.changeLeakCanaryState(enabled)

        val view = view()
        view?.showMessage("LeakCanary was " + booleanToEnabledDisabled(enabled))
        view?.showAppNeedsToBeRestarted() // LeakCanary can not be enabled on demand (or it's possible?)
    }

    fun changeTinyDancerState(enabled: Boolean) {
        if (developerSettingsModel.isTinyDancerEnabled == enabled) {
            return  // no-op
        }

        analyticsModel.sendEvent("developer_settings_tiny_dancer_" + booleanToEnabledDisabled(enabled))

        developerSettingsModel.changeTinyDancerState(enabled)

        view()?.showMessage("TinyDancer was " + booleanToEnabledDisabled(enabled))
    }

    fun changeHttpLoggingLevel(loggingLevel: HttpLoggingInterceptor.Level) {
        if (developerSettingsModel.httpLoggingLevel == loggingLevel) {
            return  // no-op
        }

        analyticsModel.sendEvent("developer_settings_http_logging_level_" + loggingLevel)

        developerSettingsModel.changeHttpLoggingLevel(loggingLevel)

        view()?.showMessage("Http logging level was changed to " + loggingLevel.toString())
    }

    private fun booleanToEnabledDisabled(enabled: Boolean): String {
        return if (enabled) "enabled" else "disabled"
    }
}


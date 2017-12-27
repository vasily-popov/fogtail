package com.vascome.fogtail.presentation.devsettings.presenters

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.vascome.fogtail.presentation.devsettings.model.DeveloperSettingsModelImpl
import com.vascome.fogtail.presentation.devsettings.views.DeveloperSettingsContract
import com.vascome.fogtail.utils.AnalyticsModel

import javax.inject.Inject

import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class DeveloperSettingsPresenter
@Inject constructor(private val developerSettingsModel: DeveloperSettingsModelImpl,
            private val analyticsModel: AnalyticsModel)
        : MvpBasePresenter<DeveloperSettingsContract.View>(),
            DeveloperSettingsContract.Presenter {

    override fun syncDeveloperSettings() {

        ifViewAttached{ view->view.changeGitSha(developerSettingsModel.gitSha) }
        ifViewAttached{ view->view.changeBuildDate(developerSettingsModel.buildDate) }
        ifViewAttached{ view->view.changeBuildVersionCode(developerSettingsModel.buildVersionCode) }
        ifViewAttached{ view->view.changeBuildVersionName(developerSettingsModel.buildVersionName) }
        ifViewAttached{ view->view.changeStethoState(developerSettingsModel.isStethoEnabled) }
        ifViewAttached{ view->view.changeLeakCanaryState(developerSettingsModel.isLeakCanaryEnabled) }
        ifViewAttached{ view->view.changeTinyDancerState(developerSettingsModel.isTinyDancerEnabled) }
        ifViewAttached{ view->view.changeHttpLoggingLevel(developerSettingsModel.httpLoggingLevel) }
    }

    override fun changeStethoState(enabled: Boolean) {
        if (developerSettingsModel.isStethoEnabled == enabled) {
            return  // no-op
        }

        analyticsModel.sendEvent("developer_settings_stetho_" + booleanToEnabledDisabled(enabled))

        val stethoWasEnabled = developerSettingsModel.isStethoEnabled

        developerSettingsModel.changeStethoState(enabled)

        ifViewAttached { view->
            view.showMessage("Stetho was " + booleanToEnabledDisabled(enabled))
            if (stethoWasEnabled) {
                view.showAppNeedsToBeRestarted()
            }
        }
    }

    override fun changeLeakCanaryState(enabled: Boolean) {
        if (developerSettingsModel.isLeakCanaryEnabled == enabled) {
            return  // no-op
        }

        analyticsModel.sendEvent("developer_settings_leak_canary_" + booleanToEnabledDisabled(enabled))

        developerSettingsModel.changeLeakCanaryState(enabled)

        ifViewAttached { view->
            view.showMessage("LeakCanary was " + booleanToEnabledDisabled(enabled))
            view.showAppNeedsToBeRestarted()
        }
    }

    override fun changeTinyDancerState(enabled: Boolean) {
        if (developerSettingsModel.isTinyDancerEnabled == enabled) {
            return  // no-op
        }

        analyticsModel.sendEvent("developer_settings_tiny_dancer_" + booleanToEnabledDisabled(enabled))

        developerSettingsModel.changeTinyDancerState(enabled)

        ifViewAttached { view->
            view.showMessage("TinyDancer was " + booleanToEnabledDisabled(enabled))
        }
    }

    override fun changeHttpLoggingLevel(loggingLevel: HttpLoggingInterceptor.Level) {
        if (developerSettingsModel.httpLoggingLevel == loggingLevel) {
            return  // no-op
        }

        analyticsModel.sendEvent("developer_settings_http_logging_level_" + loggingLevel)

        developerSettingsModel.changeHttpLoggingLevel(loggingLevel)

        ifViewAttached { view->
            view.showMessage("Http logging level was changed to " + loggingLevel.toString())
        }
    }

    private fun booleanToEnabledDisabled(enabled: Boolean): String {
        return if (enabled) "enabled" else "disabled"
    }
}


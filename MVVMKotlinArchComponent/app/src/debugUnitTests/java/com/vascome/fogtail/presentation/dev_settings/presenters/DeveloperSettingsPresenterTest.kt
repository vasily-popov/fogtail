package com.vascome.fogtail.presentation.dev_settings.presenters

import com.vascome.fogtail.presentation.dev_settings.model.DeveloperSettingsModelImpl
import com.vascome.fogtail.presentation.dev_settings.views.DeveloperSettingsView
import com.vascome.fogtail.utils.AnalyticsModel

import org.junit.Before
import org.junit.Test

import okhttp3.logging.HttpLoggingInterceptor

import org.mockito.Matchers.anyBoolean
import org.mockito.Matchers.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@Suppress("FunctionName")
class DeveloperSettingsPresenterTest {

    lateinit private var developerSettingsModel: DeveloperSettingsModelImpl
    lateinit private var developerSettingsPresenter: DeveloperSettingsPresenter
    lateinit private var developerSettingsView: DeveloperSettingsView

    @Before
    fun beforeEachTest() {
        developerSettingsModel = mock(DeveloperSettingsModelImpl::class.java)
        developerSettingsPresenter = DeveloperSettingsPresenter(developerSettingsModel, mock(AnalyticsModel::class.java))
        developerSettingsView = mock(DeveloperSettingsView::class.java)
    }

    @Test
    fun bindView_shouldSendGitShaToTheView() {
        `when`(developerSettingsModel.gitSha).thenReturn("test git sha")

        developerSettingsPresenter.bindView(developerSettingsView)
        verify<DeveloperSettingsView>(developerSettingsView).changeGitSha("test git sha")
    }

    @Test
    fun bindView_shouldSendBuildDateToTheView() {
        `when`(developerSettingsModel.buildDate).thenReturn("test build date")

        developerSettingsPresenter.bindView(developerSettingsView)
        verify<DeveloperSettingsView>(developerSettingsView).changeBuildDate("test build date")
    }

    @Test
    fun bindView_shouldSendBuildVersionCodeToTheView() {
        `when`(developerSettingsModel.buildVersionCode).thenReturn("test build version code")

        developerSettingsPresenter.bindView(developerSettingsView)
        verify<DeveloperSettingsView>(developerSettingsView).changeBuildVersionCode("test build version code")
    }

    @Test
    fun bindView_shouldSendBuildVersionNameToTheView() {
        `when`(developerSettingsModel.buildVersionName).thenReturn("test build version name")

        developerSettingsPresenter.bindView(developerSettingsView)
        verify<DeveloperSettingsView>(developerSettingsView).changeBuildVersionName("test build version name")
    }

    @Test
    fun bindView_shouldSendStethoEnabledStateToTheView() {
        `when`(developerSettingsModel.isStethoEnabled).thenReturn(true)

        developerSettingsPresenter.bindView(developerSettingsView)
        verify<DeveloperSettingsView>(developerSettingsView).changeStethoState(true)
        verify<DeveloperSettingsModelImpl>(developerSettingsModel).isStethoEnabled
    }

    @Test
    fun bindView_shouldSendStethoDisabledStateToTheView() {
        `when`(developerSettingsModel.isStethoEnabled).thenReturn(false)

        developerSettingsPresenter.bindView(developerSettingsView)
        verify<DeveloperSettingsView>(developerSettingsView).changeStethoState(false)
        verify<DeveloperSettingsModelImpl>(developerSettingsModel).isStethoEnabled
    }

    @Test
    fun bindView_shouldSendLeakCanaryEnabledStateToTheView() {
        `when`(developerSettingsModel.isLeakCanaryEnabled).thenReturn(true)

        developerSettingsPresenter.bindView(developerSettingsView)
        verify<DeveloperSettingsView>(developerSettingsView).changeLeakCanaryState(true)
        verify<DeveloperSettingsModelImpl>(developerSettingsModel).isLeakCanaryEnabled
    }

    @Test
    fun bindView_shouldSendLeakCanaryDisabledStateToTheView() {
        `when`(developerSettingsModel.isLeakCanaryEnabled).thenReturn(false)

        developerSettingsPresenter.bindView(developerSettingsView)
        verify<DeveloperSettingsView>(developerSettingsView).changeLeakCanaryState(false)
        verify<DeveloperSettingsModelImpl>(developerSettingsModel).isLeakCanaryEnabled
    }

    @Test
    fun bindView_shouldSendTinyDancerEnabledStateToTheView() {
        `when`(developerSettingsModel.isTinyDancerEnabled).thenReturn(true)

        developerSettingsPresenter.bindView(developerSettingsView)
        verify<DeveloperSettingsView>(developerSettingsView).changeTinyDancerState(true)
        verify<DeveloperSettingsModelImpl>(developerSettingsModel).isTinyDancerEnabled
    }

    @Test
    fun bindView_shouldSendTinyDancerDisabledStateToTheView() {
        `when`(developerSettingsModel.isTinyDancerEnabled).thenReturn(false)

        developerSettingsPresenter.bindView(developerSettingsView)
        verify<DeveloperSettingsView>(developerSettingsView).changeTinyDancerState(false)
        verify<DeveloperSettingsModelImpl>(developerSettingsModel).isTinyDancerEnabled
    }

    @Test
    fun bindView_shouldSendHttpLoggingLevelToTheView() {
        for (loggingLevel in HttpLoggingInterceptor.Level.values()) {
            `when`(developerSettingsModel.httpLoggingLevel).thenReturn(loggingLevel)

            developerSettingsPresenter.bindView(developerSettingsView)
            verify<DeveloperSettingsView>(developerSettingsView).changeHttpLoggingLevel(loggingLevel)
            verify<DeveloperSettingsModelImpl>(developerSettingsModel).httpLoggingLevel

            developerSettingsModel = mock(DeveloperSettingsModelImpl::class.java)
            developerSettingsView = mock(DeveloperSettingsView::class.java)
            developerSettingsPresenter = DeveloperSettingsPresenter(developerSettingsModel, mock(AnalyticsModel::class.java))
        }
    }

    @Test
    fun changeStethoState_shouldNoOpIfStateAlreadySameAndEnabled() {
        `when`(developerSettingsModel.isStethoEnabled).thenReturn(true)

        developerSettingsPresenter.bindView(developerSettingsView)
        developerSettingsPresenter.changeStethoState(true)

        verify<DeveloperSettingsModelImpl>(developerSettingsModel, never()).changeStethoState(anyBoolean())
        verify<DeveloperSettingsView>(developerSettingsView, never()).showMessage(anyString())
        verify<DeveloperSettingsView>(developerSettingsView, never()).showAppNeedsToBeRestarted()
    }

    @Test
    fun changeStethoState_shouldNoOpIfStateAlreadySameAndDisabled() {
        `when`(developerSettingsModel.isStethoEnabled).thenReturn(false)

        developerSettingsPresenter.bindView(developerSettingsView)
        developerSettingsPresenter.changeStethoState(false)

        verify<DeveloperSettingsModelImpl>(developerSettingsModel, never()).changeStethoState(anyBoolean())
        verify<DeveloperSettingsView>(developerSettingsView, never()).showMessage(anyString())
        verify<DeveloperSettingsView>(developerSettingsView, never()).showAppNeedsToBeRestarted()
    }

    @Test
    fun changeStethoState_shouldEnableStethoAndNotifyView() {
        developerSettingsPresenter.bindView(developerSettingsView)

        developerSettingsPresenter.changeStethoState(true)
        verify<DeveloperSettingsModelImpl>(developerSettingsModel).changeStethoState(true)
        verify<DeveloperSettingsView>(developerSettingsView).showMessage("Stetho was enabled")
        verify<DeveloperSettingsView>(developerSettingsView, never()).showAppNeedsToBeRestarted()
    }

    @Test
    fun changeStethoState_shouldDisableStethoAndNotifyView() {
        developerSettingsPresenter.bindView(developerSettingsView)

        `when`(developerSettingsModel.isStethoEnabled).thenReturn(true)
        developerSettingsPresenter.changeStethoState(false)
        verify<DeveloperSettingsModelImpl>(developerSettingsModel).changeStethoState(false)
        verify<DeveloperSettingsView>(developerSettingsView).showMessage("Stetho was disabled")
        verify<DeveloperSettingsView>(developerSettingsView).showAppNeedsToBeRestarted()
    }

    @Test
    fun changeStethoState_shouldDisableStethoAndNotifyViewAndAskAppRestartIfStethoWasAlreadyEnabled() {
        developerSettingsPresenter.bindView(developerSettingsView)

        `when`(developerSettingsModel.isStethoEnabled).thenReturn(true)

        developerSettingsPresenter.changeStethoState(false)
        verify<DeveloperSettingsModelImpl>(developerSettingsModel).changeStethoState(false)
        verify<DeveloperSettingsView>(developerSettingsView).showMessage("Stetho was disabled")

        verify<DeveloperSettingsView>(developerSettingsView).showAppNeedsToBeRestarted()
    }

    @Test
    fun changeLeakCanaryState_shouldNoOpIfStateAlreadySameAndEnabled() {
        `when`(developerSettingsModel.isLeakCanaryEnabled).thenReturn(true)

        developerSettingsPresenter.bindView(developerSettingsView)
        developerSettingsPresenter.changeLeakCanaryState(true)

        verify<DeveloperSettingsModelImpl>(developerSettingsModel, never()).changeLeakCanaryState(anyBoolean())
        verify<DeveloperSettingsView>(developerSettingsView, never()).showMessage(anyString())
        verify<DeveloperSettingsView>(developerSettingsView, never()).showAppNeedsToBeRestarted()
    }

    @Test
    fun changeLeakCanaryState_shouldNoOpIfStateAlreadySameAndDisabled() {
        `when`(developerSettingsModel.isLeakCanaryEnabled).thenReturn(false)

        developerSettingsPresenter.bindView(developerSettingsView)
        developerSettingsPresenter.changeLeakCanaryState(false)

        verify<DeveloperSettingsModelImpl>(developerSettingsModel, never()).changeLeakCanaryState(anyBoolean())
        verify<DeveloperSettingsView>(developerSettingsView, never()).showMessage(anyString())
        verify<DeveloperSettingsView>(developerSettingsView, never()).showAppNeedsToBeRestarted()
    }

    @Test
    fun changeLeakCanaryState_shouldEnableLeakCanaryAndNotifyView() {
        developerSettingsPresenter.bindView(developerSettingsView)

        developerSettingsPresenter.changeLeakCanaryState(true)
        verify<DeveloperSettingsModelImpl>(developerSettingsModel).changeLeakCanaryState(true)
        verify<DeveloperSettingsView>(developerSettingsView).showMessage("LeakCanary was enabled")
        verify<DeveloperSettingsView>(developerSettingsView).showAppNeedsToBeRestarted()
    }

    @Test
    fun changeLeakCanaryState_shouldDisableLeakCanaryAndNotifyView() {
        developerSettingsPresenter.bindView(developerSettingsView)

        `when`(developerSettingsModel.isLeakCanaryEnabled).thenReturn(true)

        developerSettingsPresenter.changeLeakCanaryState(false)
        verify<DeveloperSettingsModelImpl>(developerSettingsModel).changeLeakCanaryState(false)
        verify<DeveloperSettingsView>(developerSettingsView).showMessage("LeakCanary was disabled")
        verify<DeveloperSettingsView>(developerSettingsView).showAppNeedsToBeRestarted()
    }

    @Test
    fun changeTinyDancerState_shouldNoOpIfStateAlreadySameAndEnabled() {
        `when`(developerSettingsModel.isTinyDancerEnabled).thenReturn(true)

        developerSettingsPresenter.bindView(developerSettingsView)
        developerSettingsPresenter.changeTinyDancerState(true)

        verify<DeveloperSettingsModelImpl>(developerSettingsModel, never()).changeTinyDancerState(anyBoolean())
        verify<DeveloperSettingsView>(developerSettingsView, never()).showMessage(anyString())
        verify<DeveloperSettingsView>(developerSettingsView, never()).showAppNeedsToBeRestarted()
    }

    @Test
    fun changeTinyDancerState_shouldNoOpIfStateAlreadySameAndDisabled() {
        `when`(developerSettingsModel.isTinyDancerEnabled).thenReturn(false)

        developerSettingsPresenter.bindView(developerSettingsView)
        developerSettingsPresenter.changeTinyDancerState(false)

        verify<DeveloperSettingsModelImpl>(developerSettingsModel, never()).changeTinyDancerState(anyBoolean())
        verify<DeveloperSettingsView>(developerSettingsView, never()).showMessage(anyString())
        verify<DeveloperSettingsView>(developerSettingsView, never()).showAppNeedsToBeRestarted()
    }

    @Test
    fun changeTinyDancerState_shouldEnableTinyDancerAndNotifyView() {
        developerSettingsPresenter.bindView(developerSettingsView)

        developerSettingsPresenter.changeTinyDancerState(true)
        verify<DeveloperSettingsModelImpl>(developerSettingsModel).changeTinyDancerState(true)
        verify<DeveloperSettingsView>(developerSettingsView).showMessage("TinyDancer was enabled")
        verify<DeveloperSettingsView>(developerSettingsView, never()).showAppNeedsToBeRestarted()
    }

    @Test
    fun changeTinyDancerState_shouldDisableTinyDancerAndNotifyView() {
        `when`(developerSettingsModel.isTinyDancerEnabled).thenReturn(true)
        developerSettingsPresenter.bindView(developerSettingsView)

        developerSettingsPresenter.changeTinyDancerState(false)
        verify<DeveloperSettingsModelImpl>(developerSettingsModel).changeTinyDancerState(false)
        verify<DeveloperSettingsView>(developerSettingsView).showMessage("TinyDancer was disabled")
        verify<DeveloperSettingsView>(developerSettingsView, never()).showAppNeedsToBeRestarted()
    }

    @Test
    fun changeHttpLoggingLevel_shouldChangeLevelAndNotifyView() {
        for (loggingLevel in HttpLoggingInterceptor.Level.values()) {
            developerSettingsPresenter.bindView(developerSettingsView)

            developerSettingsPresenter.changeHttpLoggingLevel(loggingLevel)
            verify<DeveloperSettingsModelImpl>(developerSettingsModel).changeHttpLoggingLevel(loggingLevel)
            verify<DeveloperSettingsView>(developerSettingsView).showMessage("Http logging level was changed to " + loggingLevel.toString())

            developerSettingsModel = mock(DeveloperSettingsModelImpl::class.java)
            developerSettingsView = mock(DeveloperSettingsView::class.java)
            developerSettingsPresenter = DeveloperSettingsPresenter(developerSettingsModel, mock(AnalyticsModel::class.java))
        }
    }
}
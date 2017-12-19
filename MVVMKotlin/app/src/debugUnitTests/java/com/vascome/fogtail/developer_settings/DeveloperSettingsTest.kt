package com.vascome.fogtail.developer_settings

import com.vascome.fogtail.BuildConfig
import com.vascome.fogtail.FogtailRobolectricUnitTestRunner
import com.vascome.fogtail.FogtailUnitTestApp
import com.vascome.fogtail.presentation.dev_settings.model.DeveloperSettings

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

import okhttp3.logging.HttpLoggingInterceptor

import android.content.Context.MODE_PRIVATE
import org.assertj.core.api.Assertions.assertThat

@Suppress("FunctionName")
@RunWith(FogtailRobolectricUnitTestRunner::class)
@Config(constants = BuildConfig::class, sdk = [26], application = FogtailUnitTestApp::class)
class DeveloperSettingsTest {

    lateinit private var developerSettings: DeveloperSettings

    @Before
    fun beforeEachTest() {
        developerSettings = DeveloperSettings(RuntimeEnvironment.application.getSharedPreferences("developer_settings", MODE_PRIVATE))
    }

    @Test
    fun isStethoEnabled_shouldReturnFalseByDefault() {
        assertThat(developerSettings.isStethoEnabled).isFalse()
    }

    @Test
    fun saveIsStethoEnabled_isStethoEnabled() {
        developerSettings.saveIsStethoEnabled(true)
        assertThat(developerSettings.isStethoEnabled).isTrue()

        developerSettings.saveIsStethoEnabled(false)
        assertThat(developerSettings.isStethoEnabled).isFalse()
    }

    @Test
    fun isLeakCanaryEnabled_shouldReturnFalseByDefault() {
        assertThat(developerSettings.isLeakCanaryEnabled).isFalse()
    }

    @Test
    fun saveIsLeakCanaryEnabled_isLeakCanaryEnabled() {
        developerSettings.saveIsLeakCanaryEnabled(true)
        assertThat(developerSettings.isLeakCanaryEnabled).isTrue()

        developerSettings.saveIsLeakCanaryEnabled(false)
        assertThat(developerSettings.isLeakCanaryEnabled).isFalse()
    }

    @Test
    fun isTinyDancerEnabled_shouldReturnFalseByDefault() {
        assertThat(developerSettings.isTinyDancerEnabled).isFalse()
    }

    @Test
    fun saveIsTinyDancerEnabled_isTinyDancerEnabled() {
        developerSettings.saveIsTinyDancerEnabled(true)
        assertThat(developerSettings.isTinyDancerEnabled).isTrue()

        developerSettings.saveIsTinyDancerEnabled(false)
        assertThat(developerSettings.isTinyDancerEnabled).isFalse()
    }

    @Test
    fun getHttpLoggingLevel_shouldReturnBaseByDefault() {
        assertThat(developerSettings.httpLoggingLevel).isEqualTo(HttpLoggingInterceptor.Level.BASIC)
    }

    @Test
    fun saveHttpLoggingLevel_getHttpLoggingLevel() {
        for (loggingLevel in HttpLoggingInterceptor.Level.values()) {
            developerSettings.saveHttpLoggingLevel(loggingLevel)
            assertThat(developerSettings.httpLoggingLevel).isEqualTo(loggingLevel)
        }
    }
}
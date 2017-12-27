package com.vascome.fogtail.developer_settings

import com.vascome.fogtail.FogtailApplication
import com.vascome.fogtail.presentation.devsettings.model.DeveloperSettings
import com.vascome.fogtail.presentation.devsettings.model.DeveloperSettingsModelImpl
import com.vascome.fogtail.utils.LeakCanaryProxy

import org.junit.Before
import org.junit.Test

import java.text.SimpleDateFormat
import java.util.Date

import hu.supercluster.paperwork.Paperwork
import okhttp3.logging.HttpLoggingInterceptor

import org.assertj.core.api.Assertions.assertThat
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.Mockito.`when`

@Suppress("FunctionName")
class DeveloperSettingsModelImplTest {
    private lateinit var developerSettingsModel: DeveloperSettingsModelImpl
    private lateinit var developerSettings: DeveloperSettings
    private lateinit var paperwork: Paperwork

    @Before
    fun beforeEachTest() {
        developerSettings = mock(DeveloperSettings::class.java)
        paperwork = mock(Paperwork::class.java)

        developerSettingsModel = DeveloperSettingsModelImpl(
                mock(FogtailApplication::class.java),
                developerSettings,
                HttpLoggingInterceptor(),
                mock(LeakCanaryProxy::class.java),
                paperwork
        )
    }

    @Test
    fun testGetGitSha() {
        `when`(paperwork.get("gitSha")).thenReturn("abc123")

        assertThat(developerSettingsModel.gitSha).isEqualTo("abc123")
        verify<Paperwork>(paperwork).get("gitSha")
        verifyNoMoreInteractions(paperwork)
    }

    @Test
    fun getGitSha_shouldReturnSameResultForSeveralCalls() {
        `when`(paperwork.get("gitSha")).thenReturn("abc123")

        val sha1 = developerSettingsModel.gitSha
        val sha2 = developerSettingsModel.gitSha
        val sha3 = developerSettingsModel.gitSha

        assertThat(sha1).isEqualTo(sha2).isEqualTo(sha3).isEqualTo("abc123")
    }

    @Test
    fun testGetBuildDate() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = dateFormat.format(Date())
        `when`(paperwork.get("buildDate")).thenReturn(date)

        assertThat(developerSettingsModel.buildDate).isEqualTo(date)
        verify<Paperwork>(paperwork).get("buildDate")
        verifyNoMoreInteractions(paperwork)
    }

    @Test
    fun getBuildDate_shouldReturnSameResultForSeveralCalls() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = dateFormat.format(Date())
        `when`(paperwork.get("buildDate")).thenReturn(date)

        val buildDate1 = developerSettingsModel.buildDate
        val buildDate2 = developerSettingsModel.buildDate
        val buildDate3 = developerSettingsModel.buildDate

        assertThat(buildDate1).isEqualTo(buildDate2).isEqualTo(buildDate3).isEqualTo(date)
    }

    @Test
    fun getBuildVersionCode_shouldNotBeNull() {
        assertThat(developerSettingsModel.buildVersionCode).isNotNull()
    }

    @Test
    fun getBuildVersionCode_shouldReturnSameResultForSeveralCalls() {
        val buildVersionCode1 = developerSettingsModel.buildVersionCode
        val buildVersionCode2 = developerSettingsModel.buildVersionCode
        val buildVersionCode3 = developerSettingsModel.buildVersionCode

        assertThat(buildVersionCode1).isEqualTo(buildVersionCode2).isEqualTo(buildVersionCode3)
    }

    @Test
    fun getBuildVersionName_shouldNotBeNull() {
        assertThat(developerSettingsModel.buildVersionName).isNotNull()
    }

    @Test
    fun getBuildVersionName_shouldReturnSameResultForSeveralCalls() {
        val buildVersionName1 = developerSettingsModel.buildVersionName
        val buildVersionName2 = developerSettingsModel.buildVersionName
        val buildVersionName3 = developerSettingsModel.buildVersionName

        assertThat(buildVersionName1).isEqualTo(buildVersionName2).isEqualTo(buildVersionName3)
    }

    @Test
    fun isStethoEnabled_shouldReturnValueFromDeveloperSettings() {
        `when`(developerSettings.isStethoEnabled).thenReturn(true)
        assertThat(developerSettingsModel.isStethoEnabled).isTrue()
        verify<DeveloperSettings>(developerSettings).isStethoEnabled

        `when`(developerSettings.isStethoEnabled).thenReturn(false)
        assertThat(developerSettingsModel.isStethoEnabled).isFalse()
        verify<DeveloperSettings>(developerSettings, times(2)).isStethoEnabled
    }

    @Test
    fun isLeakCanaryEnabled_shouldReturnValueFromDeveloperSettings() {
        `when`(developerSettings.isLeakCanaryEnabled).thenReturn(true)
        assertThat(developerSettingsModel.isLeakCanaryEnabled).isTrue()
        verify<DeveloperSettings>(developerSettings).isLeakCanaryEnabled

        `when`(developerSettings.isLeakCanaryEnabled).thenReturn(false)
        assertThat(developerSettingsModel.isLeakCanaryEnabled).isFalse()
        verify<DeveloperSettings>(developerSettings, times(2)).isLeakCanaryEnabled
    }

    @Test
    fun isTinyDancerEnabled_shouldReturnValueFromDeveloperSettings() {
        `when`(developerSettings.isTinyDancerEnabled).thenReturn(true)
        assertThat(developerSettingsModel.isTinyDancerEnabled).isTrue()
        verify<DeveloperSettings>(developerSettings).isTinyDancerEnabled

        `when`(developerSettings.isTinyDancerEnabled).thenReturn(false)
        assertThat(developerSettingsModel.isTinyDancerEnabled).isFalse()
        verify<DeveloperSettings>(developerSettings, times(2)).isTinyDancerEnabled
    }

    @Test
    fun getHttpLoggingInterceptor_shouldReturnValueFromDeveloperSettings() {
        for (loggingLevel in HttpLoggingInterceptor.Level.values()) {
            `when`(developerSettings.httpLoggingLevel).thenReturn(loggingLevel)
            assertThat(developerSettingsModel.httpLoggingLevel).isEqualTo(loggingLevel)
        }
    }

    // To test apply() method we will need a lof of abstractions over the libraries used
    // for Developer Settings, because most of them initialized statically and hardly mockable/verifiable :(
    // So, sorry, no tests for apply(). But, feel free to PR!
}
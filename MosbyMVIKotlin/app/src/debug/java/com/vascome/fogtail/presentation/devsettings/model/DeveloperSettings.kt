package com.vascome.fogtail.presentation.devsettings.model

import android.content.SharedPreferences

import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

/**
 * Wrapper over [android.content.SharedPreferences] to store developer settings.
 */

class DeveloperSettings(private val sharedPreferences: SharedPreferences) {

    val isStethoEnabled: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_STETHO_ENABLED, false)

    val isLeakCanaryEnabled: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_LEAK_CANARY_ENABLED, false)

    val isTinyDancerEnabled: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_TINY_DANCER_ENABLED, false)

    // After OkHttp update old logging level may be removed/renamed so we should handle such case.
    val httpLoggingLevel: HttpLoggingInterceptor.Level
        get() {
            val savedHttpLoggingLevel = sharedPreferences.getString(KEY_HTTP_LOGGING_LEVEL, null)

            try {
                if (savedHttpLoggingLevel != null) {
                    return HttpLoggingInterceptor.Level.valueOf(savedHttpLoggingLevel)
                }
            } catch (noSuchLoggingLevel: IllegalArgumentException) {
                Timber.w("No such Http logging level in current version of the app. Saved loggingLevel = %s", savedHttpLoggingLevel)
            }

            return HttpLoggingInterceptor.Level.BASIC
        }

    fun saveIsStethoEnabled(isStethoEnabled: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_STETHO_ENABLED, isStethoEnabled).apply()
    }

    fun saveIsLeakCanaryEnabled(isLeakCanaryEnabled: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_LEAK_CANARY_ENABLED, isLeakCanaryEnabled).apply()
    }

    fun saveIsTinyDancerEnabled(isTinyDancerEnabled: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_TINY_DANCER_ENABLED, isTinyDancerEnabled).apply()
    }

    fun saveHttpLoggingLevel(loggingLevel: HttpLoggingInterceptor.Level) {
        sharedPreferences.edit().putString(KEY_HTTP_LOGGING_LEVEL, loggingLevel.toString()).apply()
    }

    companion object {

        private val KEY_IS_STETHO_ENABLED = "is_stetho_enabled"
        private val KEY_IS_LEAK_CANARY_ENABLED = "is_leak_canary_enabled"
        private val KEY_IS_TINY_DANCER_ENABLED = "is_tiny_dancer_enabled"
        private val KEY_HTTP_LOGGING_LEVEL = "key_http_logging_level"
    }

}

package com.vascome.fogtail.utils

/**
 * Common interface for Analytics systems like: Yandex App Metrics, Google Analytics, Flurry, etc.
 */

interface AnalyticsModel {

    fun init()
    fun sendEvent(eventName: String)
    fun sendError(message: String, error: Throwable)

}

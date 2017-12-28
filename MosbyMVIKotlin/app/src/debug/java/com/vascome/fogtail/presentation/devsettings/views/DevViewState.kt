package com.vascome.fogtail.presentation.devsettings.views

import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by vasilypopov on 12/27/17
 * Copyright (c) 2017 MosbyMVPKotlin. All rights reserved.
 *
 *
 */

class DevViewState private constructor(val gitSha: String,
                                       val buildDate: String,
                                       val buildVersionCode: String,
                                       val buildVersionName: String,
                                       val isStethoEnabled: Boolean,
                                       val isLeakCanaryEnabled: Boolean,
                                       val isTinyDancerEnabled: Boolean,
                                       val httpLoggingLevel: HttpLoggingInterceptor.Level) {


    fun builder(): Builder {
        return Builder(this)
    }

    class Builder {
        private var gitSha: String = ""
        private var buildDate: String = ""
        private var buildVersionCode: String = ""
        private var buildVersionName: String = ""
        private var isStethoEnabled: Boolean = false
        private var isLeakCanaryEnabled: Boolean = false
        private var isTinyDancerEnabled: Boolean = false
        private var httpLoggingLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.NONE

        fun gitSha(gitSha: String): Builder {
            this.gitSha = gitSha
            return this
        }

        fun buildDate(buildDate: String): Builder {
            this.buildDate = buildDate
            return this
        }

        fun buildVersionCode(buildVersionCode: String): Builder {
            this.buildVersionCode = buildVersionCode
            return this
        }

        fun buildVersionName(buildVersionName: String): Builder {
            this.buildVersionName = buildVersionName
            return this
        }

        fun stethoEnabled(stethoEnabled: Boolean): Builder {
            this.isStethoEnabled = stethoEnabled
            return this
        }

        fun leakCanaryEnabled(leakCanaryEnabled: Boolean): Builder {
            this.isLeakCanaryEnabled = leakCanaryEnabled
            return this
        }

        fun tinyDancerEnabled(tinyDancerEnabled: Boolean): Builder {
            this.isTinyDancerEnabled = tinyDancerEnabled
            return this
        }

        fun httpLoggingLevel(level: HttpLoggingInterceptor.Level): Builder {
            this.httpLoggingLevel = level
            return this
        }

        fun build(): DevViewState {
            return DevViewState(gitSha,buildDate,
                    buildVersionCode,buildVersionName,
                    isStethoEnabled,isLeakCanaryEnabled,
                    isTinyDancerEnabled, httpLoggingLevel)
        }

        constructor()

        constructor(from: DevViewState) {
            this.gitSha = from.gitSha
            this.buildDate = from.buildDate
            this.buildVersionCode = from.buildVersionCode
            this.buildVersionName = from.buildVersionName
            this.isLeakCanaryEnabled = from.isLeakCanaryEnabled
            this.isStethoEnabled = from.isStethoEnabled
            this.isTinyDancerEnabled = from.isTinyDancerEnabled
            this.httpLoggingLevel = from.httpLoggingLevel
        }

    }
}
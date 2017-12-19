package com.vascome.fogtail.utils

import android.app.Application

import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class LeakCanaryProxyImpl(private val qualityMattersApp: Application) : LeakCanaryProxy {

    private var refWatcher: RefWatcher? = null

    override fun init() {
        refWatcher = LeakCanary.install(qualityMattersApp)
    }

    override fun watch(anyObject: Any) {
        refWatcher?.watch(anyObject)
    }
}

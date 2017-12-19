package com.vascome.fogtail.presentation.dev_settings.presenters

import android.support.annotation.CallSuper

/**
 * Created by vasilypopov on 12/17/17
 * Copyright (c) 2017 MVVMJavaRx. All rights reserved.
 */

abstract class BasePresenter<V> {

    @Volatile private var view: V? = null

    @CallSuper
    open fun bindView(view: V) {
        val previousView = this.view

        if (previousView != null) {
            throw IllegalStateException("Previous view is not unbounded! previousView = " + previousView)
        }

        this.view = view
    }

    fun view(): V? {
        return view
    }

    @CallSuper
    fun unbindView(view: V) {
        val previousView = this.view

        if (previousView === view) {
            this.view = null
        } else {
            throw IllegalStateException("Unexpected view! previousView = $previousView, view to unbind = $view")
        }
    }
}
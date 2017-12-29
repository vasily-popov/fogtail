package com.vascome.fogtail.presentation.base.presenters

import android.support.annotation.CallSuper

/**
 * Base presenter implementation.
 *
 * @param <V> view.
</V> */
abstract class BasePresenter<V> {

    private var view: V? = null

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

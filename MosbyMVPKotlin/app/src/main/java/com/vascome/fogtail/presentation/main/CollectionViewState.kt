package com.vascome.fogtail.presentation.main

import android.os.Bundle
import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem

/**
 * Created by vasilypopov on 12/27/17
 * Copyright (c) 2017 MosbyMVPKotlin. All rights reserved.
 *
 *
 */
class CollectionViewState : RestorableViewState<CollectionContract.View> {
    companion object {
        private val KEY_DATA = "com.vascome.fogtail.CollectionViewState_data"
        private val STATE_DO_NOTHING = 0
        private val STATE_SHOW_DATA = 1
        private val STATE_SHOW_ERROR = 2
        private val STATE_SHOW_LOADING = 3
    }

    private var state = STATE_DO_NOTHING
    private var data: ArrayList<RecAreaItem>? = null

    override fun saveInstanceState(out: Bundle) {
        out.putParcelableArrayList(KEY_DATA, data)
    }

    override fun restoreInstanceState(bundle: Bundle?): RestorableViewState<CollectionContract.View>? {
        if (bundle == null) {
            return null
        }

        data = bundle.getParcelableArrayList(KEY_DATA)
        return this
    }

    fun setData(data: List<RecAreaItem>) {
        state = STATE_SHOW_DATA
        this.data = ArrayList(data)
    }

    fun setShowLoading() {
        state = STATE_SHOW_LOADING
    }

    fun setError() {
        state = STATE_SHOW_ERROR
    }

    override fun apply(view: CollectionContract.View, retained: Boolean) {
        when (state) {
            STATE_SHOW_DATA -> view.showItems(data!!)
            STATE_SHOW_LOADING -> view.setLoadingIndicator(true)
            STATE_SHOW_ERROR ->  view.showError()
        }
    }

    fun isInProgress(): Boolean {
        return state == STATE_SHOW_LOADING
    }
}
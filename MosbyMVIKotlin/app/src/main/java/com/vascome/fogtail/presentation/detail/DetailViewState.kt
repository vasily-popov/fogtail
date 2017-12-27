package com.vascome.fogtail.presentation.detail

import android.os.Bundle
import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState

/**
 * Created by vasilypopov on 12/27/17
 * Copyright (c) 2017 MosbyMVPKotlin. All rights reserved.
 *
 *
 */

class DetailViewState : RestorableViewState<DetailContract.View> {
    override fun apply(view: DetailContract.View?, retained: Boolean) {
    }

    override fun saveInstanceState(out: Bundle) {
    }

    override fun restoreInstanceState(bundle: Bundle?): RestorableViewState<DetailContract.View>? {
        if (bundle == null) {
            return null
        }
        return this
    }

}
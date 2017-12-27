package com.vascome.fogtail.presentation.base.other

import android.view.View

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class NoOpViewModifier : ViewModifier {
    override fun <T : View> modify(view: T): T {
        // no-op
        return view
    }
}


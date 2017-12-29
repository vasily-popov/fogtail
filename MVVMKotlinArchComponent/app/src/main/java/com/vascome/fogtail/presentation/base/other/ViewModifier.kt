package com.vascome.fogtail.presentation.base.other

import android.view.View

/**
 * Simple function that modifies a [View] and returns modified one so the consumer should use modifier version.
 */
interface ViewModifier {

    fun <T : View> modify(view: T): T
}


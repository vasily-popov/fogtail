package com.vascome.fogtail.presentation.base.other

import android.view.View

import org.junit.Test

import org.assertj.core.api.Assertions.assertThat
import org.mockito.Mockito.mock
import org.mockito.Mockito.verifyZeroInteractions

class NoOpViewModifierTest {

    @Test
    fun modify_shouldReturnPassedViewAndNotInteractWithIt() {
        val view = mock(View::class.java)

        assertThat(NoOpViewModifier().modify(view)).isSameAs(view)
        verifyZeroInteractions(view)
    }
}
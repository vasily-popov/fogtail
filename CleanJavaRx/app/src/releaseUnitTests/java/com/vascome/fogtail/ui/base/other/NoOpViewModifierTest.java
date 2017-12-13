package com.vascome.fogtail.screens.base.other;

import android.view.View;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

public class NoOpViewModifierTest {

    @Test
    public void modify_shouldReturnPassedViewAndNotInteractWithIt() {
        View view = mock(View.class);

        assertThat(new NoOpViewModifier().modify(view)).isSameAs(view);
        verifyZeroInteractions(view);
    }
}
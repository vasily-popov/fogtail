package com.vascome.fogtail.presentation.base.presenters

import com.vascome.fogtail.presentation.dev_settings.presenters.BasePresenter
import org.junit.Before
import org.junit.Test

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown

@Suppress("FunctionName")
class PresenterTest {
    lateinit private var presenter: BasePresenter<Any>
    lateinit private var view: Any

    @Before
    fun beforeEachTest() {
        view = Any()
        presenter = object : BasePresenter<Any>() {}
    }

    @Test
    fun bindView_shouldAttachViewToThePresenter() {
        presenter.bindView(view)
        assertThat(presenter.view()).isSameAs(view)
    }

    @Test
    fun bindView_shouldThrowIfPreviousViewIsNotUnbounded() {
        presenter.bindView(view)

        try {
            presenter.bindView(Any())
            failBecauseExceptionWasNotThrown(IllegalStateException::class.java)
        } catch (expected: IllegalStateException) {
            assertThat(expected).hasMessage("Previous view is not unbounded! previousView = " + view)
        }

    }

    @Test
    fun view_shouldReturnNullByDefault() {
        assertThat(presenter.view()).isNull()
    }

    @Test
    fun unsubscribeOnUnbindView_shouldWorkAccordingItsContract() {
        presenter.bindView(view)
        /*
        Subscription subscription1 = mock(Subscription.class);
        Subscription subscription2 = mock(Subscription.class);
        Subscription subscription3 = mock(Subscription.class);

        presenter.unsubscribeOnUnbindView(subscription1, subscription2, subscription3);
        verify(subscription1, never()).unsubscribe();
        verify(subscription2, never()).unsubscribe();
        verify(subscription3, never()).unsubscribe();

        presenter.unbindView(view);
        verify(subscription1).unsubscribe();
        verify(subscription2).unsubscribe();
        verify(subscription3).unsubscribe();
        */
    }

    @Test
    fun unbindView_shouldNullTheViewReference() {
        presenter.bindView(view)
        assertThat(presenter.view()).isSameAs(view)

        presenter.unbindView(view)
        assertThat(presenter.view()).isNull()
    }

    @Test
    fun unbindView_shouldThrowIfPreviousViewIsNotSameAsExpected() {
        presenter.bindView(view)
        val unexpectedView = Any()

        try {
            presenter.unbindView(unexpectedView)
            failBecauseExceptionWasNotThrown(IllegalStateException::class.java)
        } catch (expected: IllegalStateException) {
            assertThat(expected).hasMessage("Unexpected view! previousView = $view, view to unbind = $unexpectedView")
        }

    }
}
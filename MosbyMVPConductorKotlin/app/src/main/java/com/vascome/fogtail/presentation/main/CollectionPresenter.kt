package com.vascome.fogtail.presentation.main

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem
import com.vascome.fogtail.presentation.main.domain.usecase.LoadItemsUseCase
import com.vascome.fogtail.utils.AnalyticsModel

import javax.inject.Inject

import io.reactivex.observers.DisposableObserver

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class CollectionPresenter
@Inject constructor(private val usecase: LoadItemsUseCase,
            private val analyticsModel: AnalyticsModel)
    : MvpBasePresenter<CollectionContract.View>(), CollectionContract.Presenter {

    override var isInProgress: Boolean = false

    private fun showViewLoading() {
        ifViewAttached{ view->view.setLoadingIndicator(true) }
    }

    private fun hideViewLoading() {
        ifViewAttached{ view->view.setLoadingIndicator(false) }
        isInProgress = false
    }

    private fun showErrorMessage() {
        ifViewAttached{view->view.showError()}
    }

    private fun showItems(items: List<RecAreaItem>) {
        ifViewAttached{ view->view.showItems(items) }
    }


    override fun reloadItems() {
        showViewLoading()
        isInProgress = true

        usecase.execute(object : DisposableObserver<List<RecAreaItem>>() {
            override fun onNext(items: List<RecAreaItem>) {
                showItems(items)
            }

            override fun onError(e: Throwable) {
                analyticsModel.sendError("error", e)
                hideViewLoading()
                showErrorMessage()
            }

            override fun onComplete() {
                hideViewLoading()
            }

        } , null)
    }

    override fun destroy() {
        usecase.dispose()
        super.destroy()
    }
}

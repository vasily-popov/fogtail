package com.vascome.fogtail.presentation.main

import android.support.v4.app.Fragment
import com.vascome.fogtail.presentation.base.presenters.BasePresenter
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
            private val analyticsModel: AnalyticsModel,
            private val activityRouter: CollectionRouter)
    : BasePresenter<CollectionContract.View>(), CollectionContract.Presenter {

    private fun showViewLoading() {
        view()?.setLoadingIndicator(true)
    }

    private fun hideViewLoading() {
        view()?.setLoadingIndicator(false)
    }

    private fun showErrorMessage() {
        view()?.showError()
    }

    private fun showItems(items: List<RecAreaItem>) {
        view()?.showItems(items)
    }


    override fun reloadItems() {

        showViewLoading()
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
        }, null)
    }

    override fun openItemDetail(item: RecAreaItem) {
        activityRouter.openDetailForItem(item)
    }

    override fun resume() {

    }

    override fun pause() {

    }

    override fun destroy() {
        usecase.dispose()
    }
}

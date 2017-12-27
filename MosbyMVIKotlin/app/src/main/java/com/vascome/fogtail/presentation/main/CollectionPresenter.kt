package com.vascome.fogtail.presentation.main

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.vascome.fogtail.data.gateway.ItemsDataSource
import com.vascome.fogtail.utils.AnalyticsModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class CollectionPresenter
@Inject constructor(private val source: ItemsDataSource,
                    private val analyticsModel: AnalyticsModel)
    : MviBasePresenter<CollectionView, CollectionViewState>() {

    override fun bindIntents() {

        val submit = ObservableTransformer<Boolean, CollectionViewState> { trigger ->
            trigger.flatMap { source.items.toObservable()
                    .subscribeOn(Schedulers.io())
                    .delay(2, TimeUnit.SECONDS)
                    .map { items -> CollectionViewState.Builder().data(items).build() }
                    .onErrorReturn { error ->  CollectionViewState.Builder().error(error).build() }
                    .delay(2, TimeUnit.SECONDS)
                    .startWith(CollectionViewState.Builder().loading(true).build())
            }
        }


        val initLoad = intent(CollectionView::loadOnStartIntent)
                .doOnNext({ _ -> Timber.d("intent: initial load") })
                .compose(submit)

        val pullToRefresh = intent(CollectionView::pullToRefreshIntent)
                .doOnNext({ _ -> Timber.d("intent: pull to refresh") })
                .compose(submit)

        val retryButtonClicked = intent(CollectionView::retryButtonClickIntent)
                .doOnNext({ _ -> Timber.d("intent: button clicked") })
                .compose(submit)

        val mergedObservable = Observable.merge(initLoad, pullToRefresh, retryButtonClicked)
                .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(mergedObservable, CollectionView::render)
    }
}

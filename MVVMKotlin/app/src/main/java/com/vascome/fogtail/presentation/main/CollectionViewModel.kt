package com.vascome.fogtail.presentation.main

import android.annotation.SuppressLint

import com.jakewharton.rxrelay2.PublishRelay
import com.vascome.fogtail.data.gateway.ItemsDataSource
import com.vascome.fogtail.data.thread.ExecutionScheduler
import com.vascome.fogtail.presentation.main.dto.RecAreaItem

import java.util.concurrent.TimeUnit

import javax.inject.Inject

import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

@Suppress("CanBeParameter")
@SuppressLint("RxSubscribeOnError")
class CollectionViewModel
@Inject constructor(private val source: ItemsDataSource,
            private val scheduler: ExecutionScheduler,
            private val router: CollectionRouter) {

    private val composite: CompositeDisposable = CompositeDisposable()


    /**
     * Send item to this command to trigger refresh
     */
    val refreshCommand: PublishRelay<Boolean> = PublishRelay.create<Boolean>()

    /**
     * Send item to this command to open detail
     */
    val openDetailCommand: PublishRelay<RecAreaItem> = PublishRelay.create<RecAreaItem>()


    // Members

    private val refreshSubject = BehaviorSubject.create<List<RecAreaItem>>()
    private val progress = PublishRelay.create<Boolean>()

    /**
     * Emits the received items.
     */
    val items: Observable<List<RecAreaItem>> = refreshSubject.hide()

    /**
     * Emits a value if the progress should be shown or hidden.
     */
    val inProgress: Observable<Boolean> = progress.hide()


    init {

        val submit = ObservableTransformer<Boolean, List<RecAreaItem>> { trigger ->
            trigger.debounce(100, TimeUnit.MILLISECONDS, scheduler.IO())
                    .flatMap { _ -> source.items
                            .onErrorResumeNext(Observable.just(emptyList()))
                            .onExceptionResumeNext(Observable.just(emptyList()))
                    }
        }

        // Bind refresh
        refreshCommand
                .doOnNext { progress.accept(true) }
                .observeOn(scheduler.IO()) //https://github.com/JakeWharton/RxBinding/issues/368
                .compose(submit)
                .observeOn(scheduler.UI())
                .doOnNext { progress.accept(false) }
                .subscribeOn(scheduler.IO())
                .subscribe({ refreshSubject.onNext(it) })
                .addTo(composite)

        openDetailCommand.toFlowable(BackpressureStrategy.LATEST)
                .observeOn(scheduler.UI())
                .subscribeOn(scheduler.UI())
                .subscribe({ router.openDetailForItem(it) })
                .addTo(composite)
    }

    fun destroy() {
        composite.clear()
    }
}

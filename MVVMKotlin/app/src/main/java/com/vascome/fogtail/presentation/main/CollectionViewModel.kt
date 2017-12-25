package com.vascome.fogtail.presentation.main

import android.annotation.SuppressLint
import com.jakewharton.rxrelay2.PublishRelay
import com.vascome.fogtail.data.gateway.ItemsDataSource
import com.vascome.fogtail.data.thread.ExecutionScheduler
import com.vascome.fogtail.presentation.main.dto.RecAreaItem
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

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

    private val itemSubject = PublishSubject.create<CollectionStateModel>()

    /**
     * Emits the received items.
     */
    val items: Observable<CollectionStateModel> = itemSubject.hide()


    init {
        val submit = ObservableTransformer<Boolean, CollectionStateModel> { trigger ->
                    trigger.flatMap { source.items.toObservable()
                                .delay(1000, TimeUnit.MILLISECONDS, scheduler.IO())
                                .map { items ->  CollectionStateModel.success(items)}
                                .onErrorReturn { t ->  CollectionStateModel.failure(t.message) }
                                .delay(1000, TimeUnit.MILLISECONDS, scheduler.IO())
                                .startWith(CollectionStateModel.inProgress())
                            }
                        }

        // Bind refresh
        refreshCommand
                .observeOn(scheduler.IO()) //https://github.com/JakeWharton/RxBinding/issues/368
                .compose(submit)
                .subscribeOn(scheduler.IO())
                //.subscribe(refreshSubject::onNext)
                //.addTo(composite)

                .subscribeWith(object : DisposableObserver<CollectionStateModel>() {
                    override fun onError(e: Throwable) {
                        throw IllegalStateException(
                                "ViewState observable must not reach error state - onError()", e)
                    }

                    override fun onComplete() {
                        throw IllegalStateException(
                                "ViewState observable must not reach error state - onError()")
                    }

                    override fun onNext(t: CollectionStateModel) {
                        itemSubject.onNext(t)
                    }
                }).addTo(composite)

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

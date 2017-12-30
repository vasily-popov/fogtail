package com.vascome.fogtail.presentation.main

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import com.vascome.fogtail.data.gateway.ItemsDataSource
import com.vascome.fogtail.data.thread.ExecutionScheduler
import io.reactivex.BackpressureStrategy
import io.reactivex.FlowableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import timber.log.Timber
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
            private val scheduler: ExecutionScheduler) : ViewModel() {

    private val composite: CompositeDisposable = CompositeDisposable()
    /**
     * Send item to this command to trigger refresh
     */
    private val refreshCommand: BehaviorRelay<Boolean> = BehaviorRelay.createDefault(true)


    private var modelLiveData = MutableLiveData<CollectionStateModel>()
    val items: LiveData<CollectionStateModel>  =  modelLiveData

    fun loadItems() {
        refreshCommand.accept(true)
    }

    init {
        val submit = FlowableTransformer<Boolean, CollectionStateModel> { trigger ->
            trigger.flatMap {
                source.items.toFlowable()
                        .delay(2000, TimeUnit.MILLISECONDS, scheduler.IO())
                        .map { items -> CollectionStateModel.success(items) }
                        .onErrorReturn { t -> CollectionStateModel.failure(t.message) }
                        .delay(2000, TimeUnit.MILLISECONDS, scheduler.IO())
                        .startWith(CollectionStateModel.inProgress())
            }
        }

        refreshCommand.toFlowable(BackpressureStrategy.LATEST)
                .observeOn(scheduler.IO()) //https://github.com/JakeWharton/RxBinding/issues/368
                .compose(submit)
                .observeOn(scheduler.UI())
                .subscribeOn(scheduler.IO())
                .subscribe(
                        { value -> modelLiveData.value = value },
                        { t -> Timber.e(t)  }
                )
                .addTo(composite)
    }

    override fun onCleared() {
        if(composite.isDisposed.not()) {
            composite.dispose()
        }
    }
}

package com.vascome.fogtail.presentation.detail

import com.jakewharton.rxrelay2.PublishRelay
import com.vascome.fogtail.presentation.main.dto.RecAreaItem
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

class DetailViewModel
@Inject constructor() {

    private val descriptionSubject = BehaviorSubject.create<String>()
    private val nameSubject = BehaviorSubject.create<String>()
    private val imageSubject = BehaviorSubject.create<String?>()
    private val disposables: CompositeDisposable = CompositeDisposable()

    /**
     * Send an item to this command to update the text etc.
     */
    val setItemCommand: PublishRelay<RecAreaItem> = PublishRelay.create<RecAreaItem>()


    init {

        val itemObservable = setItemCommand.share()

        disposables.add(
                itemObservable.map({ it.shortDescription })
                        .subscribe({ descriptionSubject.onNext(it) })
        )

        disposables.add(
                itemObservable.map({ it.name })
                        .subscribe({ nameSubject.onNext(it) })
        )

        disposables.add(
                itemObservable.map({ it.imageUrl })
                        .subscribe({ it?.let { it1 -> imageSubject.onNext(it1) } })
        )


    }

    fun itemDescription(): Observable<String> {
        return descriptionSubject.hide()
    }

    fun name(): Observable<String> {
        return nameSubject.hide()
    }

    fun imageUrl(): Observable<String?> {
        return imageSubject.hide()
    }

    fun destroy() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }
}

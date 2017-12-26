package com.vascome.fogtail.presentation.base.domain.interactor

import com.vascome.fogtail.presentation.base.domain.executor.ExecutionThread
import com.vascome.fogtail.presentation.base.domain.executor.ThreadExecutor

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver

/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

abstract class UseCase<T, in Params>(private val threadExecutor: ThreadExecutor, private val executionThread: ExecutionThread) {
    private val disposables: CompositeDisposable = CompositeDisposable()

    /**
     * Builds an [Observable] which will be used when executing the current [UseCase].
     */
    protected abstract fun buildUseCaseObservable(params: Params?): Observable<T>

    /**
     * Executes the current use case.
     *
     * @param observer [DisposableObserver] which will be listening to the observable build
     * by [.buildUseCaseObservable] ()} method.
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    fun execute(observer: DisposableObserver<T>, params: Params?) {
        val observable = this.buildUseCaseObservable(params)
                .subscribeOn(executionThread.ioScheduler)
                //.subscribeOn(Schedulers.from(threadExecutor)) //just leave as example here
                .observeOn(executionThread.uiScheduler)
        addDisposable(observable.subscribeWith(observer))
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    fun dispose() {
        disposables.clear()
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    private fun addDisposable(disposable: Disposable?) {
        if (disposable != null) {
            disposables.add(disposable)
        }
    }
}

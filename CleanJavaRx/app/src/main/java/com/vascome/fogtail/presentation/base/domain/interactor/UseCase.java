package com.vascome.fogtail.presentation.base.domain.interactor;

import com.vascome.fogtail.presentation.base.domain.executor.ExecutionThread;
import com.vascome.fogtail.presentation.base.domain.executor.ThreadExecutor;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

public abstract class UseCase<T, Params> {

    private final ThreadExecutor threadExecutor;
    private final ExecutionThread executionThread;
    private final CompositeDisposable disposables;

    public UseCase(ThreadExecutor threadExecutor, ExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.executionThread = postExecutionThread;
        this.disposables = new CompositeDisposable();
    }

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
     */
    protected abstract Observable<T> buildUseCaseObservable(Params params);

    /**
     * Executes the current use case.
     *
     * @param observer {@link DisposableObserver} which will be listening to the observable build
     * by {@link #buildUseCaseObservable(Params)} ()} method.
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    public void execute(DisposableObserver<T> observer, Params params) {
        final Observable<T> observable = this.buildUseCaseObservable(params)
                .subscribeOn(executionThread.getIOScheduler())
                //.subscribeOn(Schedulers.from(threadExecutor)) //just leave as example here
                .observeOn(executionThread.getUIScheduler());
        addDisposable(observable.subscribeWith(observer));
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    private void addDisposable(Disposable disposable) {
        if(disposable != null) {
            disposables.add(disposable);
        }
    }
}

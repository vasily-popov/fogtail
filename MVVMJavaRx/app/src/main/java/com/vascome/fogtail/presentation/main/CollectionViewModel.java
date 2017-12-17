package com.vascome.fogtail.presentation.main;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;
import com.vascome.fogtail.data.gateway.ItemsDataSource;
import com.vascome.fogtail.data.thread.ExecutionScheduler;
import com.vascome.fogtail.presentation.main.dto.RecAreaItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public final class CollectionViewModel {

    private final CompositeDisposable disposables;
    private ItemsDataSource source;
    private ExecutionScheduler scheduler;
    private CollectionRouter router;

    /**
     * Emits the received items.
     */
    public final Observable<List<RecAreaItem>> items() {
        return refreshSubject.hide();
    }

    /**
     * Emits a value if the progress should be shown or hidden.
     */
    public final Observable<Boolean> showProgress() {
        return loadingSubject.hide();
    }


    /**
     * Send item to this command to trigger refresh
     */
    public final PublishRelay<Boolean> refreshCommand = PublishRelay.create();

    /**
     * Send item to this command to open detail
     */
    public final PublishRelay<RecAreaItem> openDetailCommand = PublishRelay.create();


    // Members

    private final BehaviorSubject<List<RecAreaItem>> refreshSubject = BehaviorSubject.create();
    private final BehaviorRelay<Boolean> loadingSubject = BehaviorRelay.createDefault(false);


    @SuppressLint("RxSubscribeOnError")
    @Inject
    public CollectionViewModel(@NonNull ItemsDataSource source,
                               @NonNull ExecutionScheduler scheduler,
                               @NonNull CollectionRouter router) {
        this.source = source;
        this.scheduler = scheduler;
        this.router = router;
        disposables = new CompositeDisposable();
        // Bind refresh
        disposables.add(refreshCommand
                /* Discard the old signal if a new one comes in within 500 ms. */
                .debounce(500, TimeUnit.MILLISECONDS, scheduler.IO())
                .doOnNext(ignored -> loadingSubject.accept(true))
                /* Start the refreshing */
                .switchMap(ignored -> source.getItems()
                        .onErrorResumeNext(Observable.just(new ArrayList<>()))
                        .onExceptionResumeNext(Observable.just(new ArrayList<>())))
                //.startWith(loadingSubject.hide())
                .doOnEach(ignored -> loadingSubject.accept(false))
                .subscribeOn(scheduler.IO())
                .subscribe(refreshSubject::onNext)
        );

        disposables.add(
            openDetailCommand.toFlowable(BackpressureStrategy.LATEST)
                    .observeOn(scheduler.UI())
                    .subscribeOn(scheduler.UI())
                    .subscribe(router::openDetailForItem)
        );
    }

    public void destroy() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }
}

package com.vascome.fogtail.presentation.detail;

import com.jakewharton.rxrelay2.PublishRelay;
import com.vascome.fogtail.presentation.main.dto.RecAreaItem;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

public class DetailViewModel {

    private BehaviorSubject<String> descriptionSubject = BehaviorSubject.create();
    private BehaviorSubject<String> nameSubject = BehaviorSubject.create();
    private BehaviorSubject<String> imageSubject = BehaviorSubject.create();
    private final CompositeDisposable disposables;


    @Inject
    public DetailViewModel() {

        disposables = new CompositeDisposable();
        Observable<RecAreaItem>  itemObservable = setItemCommand.share();

        disposables.add(
            itemObservable.map(RecAreaItem::shortDescription)
                    .subscribe(descriptionSubject::onNext)
        );

        disposables.add(
            itemObservable.map(RecAreaItem::name)
                    .subscribe(nameSubject::onNext)
        );

        disposables.add(
            itemObservable.map(RecAreaItem::imageUrl)
                 .subscribe(imageSubject::onNext)
        );


    }

    /**
     * Send an item to this command to update the text etc.
     */
    public final PublishRelay<RecAreaItem> setItemCommand = PublishRelay.create();

    public final Observable<String> itemDescription() {
        return descriptionSubject.hide();
    }

    public final Observable<String>  name() {
        return nameSubject.hide();
    }

    public final Observable<String> imageUrl() {
        return imageSubject.hide();
    }

    public void destroy() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }
}

package com.vascome.fogtail.ui.collectionbase;

import android.support.annotation.NonNull;

import com.vascome.fogtail.models.AnalyticsModel;
import com.vascome.fogtail.models.RecAreaItemsModel;
import com.vascome.fogtail.ui.base.presenters.BasePresenter;
import com.vascome.fogtail.utils.schedulers.SchedulerProvider;

import io.reactivex.disposables.Disposable;

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public class CollectionPresenter extends BasePresenter<ICollectionView> {

    private final RecAreaItemsModel itemsModel;
    private final AnalyticsModel analyticsModel;
    private final SchedulerProvider schedulerProvider;

    public CollectionPresenter(@NonNull RecAreaItemsModel itemsModel,
                              @NonNull AnalyticsModel analyticsModel,
                               @NonNull SchedulerProvider schedulerProvider) {
        this.itemsModel = itemsModel;
        this.analyticsModel = analyticsModel;
        this.schedulerProvider = schedulerProvider;
    }

    public void reloadData() {

        {
            // Tip: in Kotlin you can use ? to operate with nullable values.
            final ICollectionView view = view();

            if (view != null) {
                view.setLoadingIndicator(true);
            }
        }

        Disposable disposable = itemsModel.getItems()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        // onNext
                        items -> {
                            final ICollectionView view = view();
                            if (view != null) {
                                if (items != null) {
                                    view.showItems(items);
                                }
                                else {
                                    view.showError();
                                }
                            }
                        },
                        // onError
                        throwable -> {
                            analyticsModel.sendError("ItemsPresenter.reloadData failed", throwable);
                            // Tip: in Kotlin you can use ? to operate with nullable values.
                            final ICollectionView view = view();
                            if (view != null) {
                                view.showError();
                            }
                        });
        compositeDisposable.add(disposable);
    }
}

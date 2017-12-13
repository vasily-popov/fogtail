package com.vascome.fogtail.presentation.main;

import android.support.annotation.NonNull;

import com.vascome.fogtail.presentation.base.presenters.BasePresenter;
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem;
import com.vascome.fogtail.presentation.main.domain.usecase.LoadItemsUseCase;
import com.vascome.fogtail.utils.AnalyticsModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public class CollectionPresenter extends BasePresenter<CollectionContract.View> implements CollectionContract.Presenter {

    private final LoadItemsUseCase usecase;
    private final AnalyticsModel analyticsModel;
    private final CollectionContract.Router activityRouter;

    @Inject
    public CollectionPresenter(@NonNull LoadItemsUseCase usecase,
                               @NonNull AnalyticsModel analyticsModel,
                               @NonNull CollectionContract.Router activityRouter) {
        this.usecase = usecase;
        this.analyticsModel = analyticsModel;
        this.activityRouter = activityRouter;
    }

    private void showViewLoading() {
        final CollectionContract.View view = view();
        if (view != null) {
            view.setLoadingIndicator(true);
        }
    }

    private void hideViewLoading() {
        final CollectionContract.View view = view();
        if (view != null) {
            view.setLoadingIndicator(false);
        }
    }

    private void showErrorMessage() {
        final CollectionContract.View view = view();
        if (view != null) {
            view.showError();
        }
    }

    private void showItems(List<RecAreaItem> items) {
        final CollectionContract.View view = view();
        if (view != null) {
            view.showItems(items);
        }
    }


    @Override
    public void reloadItems() {

        showViewLoading();
        usecase.execute(new DisposableObserver<List<RecAreaItem>>() {
            @Override
            public void onNext(List<RecAreaItem> items) {
                showItems(items);
            }

            @Override
            public void onError(Throwable e) {
                analyticsModel.sendError("error", e);
                hideViewLoading();
                showErrorMessage();
            }

            @Override
            public void onComplete() {
                hideViewLoading();
            }
        }, null);
    }

    @Override
    public void openItemDetail(@NonNull RecAreaItem item) {
        activityRouter.openDetailForItem(item);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        usecase.dispose();
    }
}

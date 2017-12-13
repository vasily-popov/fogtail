package com.vascome.fogtail.screens.main;

import android.support.annotation.NonNull;

import com.vascome.fogtail.screens.base.domain.UseCase;
import com.vascome.fogtail.screens.base.domain.UseCaseHandler;
import com.vascome.fogtail.screens.base.presenters.BasePresenter;
import com.vascome.fogtail.screens.main.domain.model.RecAreaItem;
import com.vascome.fogtail.screens.main.domain.usecase.GetItemsTask;
import com.vascome.fogtail.utils.AnalyticsModel;

import java.util.List;

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public class CollectionPresenter extends BasePresenter<CollectionContract.View> implements CollectionContract.Presenter {

    private final GetItemsTask usecase;
    private final AnalyticsModel analyticsModel;
    private final CollectionContract.Router activityRouter;
    private final UseCaseHandler useCaseHandler;

    public CollectionPresenter(@NonNull GetItemsTask usecase,
                               @NonNull AnalyticsModel analyticsModel,
                               @NonNull CollectionContract.Router activityRouter,
                               @NonNull UseCaseHandler useCaseHandler) {
        this.usecase = usecase;
        this.analyticsModel = analyticsModel;
        this.activityRouter = activityRouter;
        this.useCaseHandler = useCaseHandler;
    }

    @Override
    public void reloadItems() {

        {
            // Tip: in Kotlin you can use ? to operate with nullable values.
            final CollectionContract.View view = view();

            if (view != null) {
                view.setLoadingIndicator(true);
            }
        }
        GetItemsTask.RequestValues requestValue = new GetItemsTask.RequestValues();
        useCaseHandler.execute(usecase, requestValue,
                new UseCase.UseCaseCallback<GetItemsTask.ResponseValue>() {
                    @Override
                    public void onSuccess(GetItemsTask.ResponseValue response) {
                        List<RecAreaItem> items = response.getItems();
                        // Tip: in Kotlin you can use ? to operate with nullable values.
                        final CollectionContract.View view = view();

                        if (view != null) {
                            if(items != null && items.size() > 0) {
                                view.showItems(items);
                            }
                            else {
                                view.showError();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        analyticsModel.sendError("CollectionPresenter.reloadItems failed", t);
                        // Tip: in Kotlin you can use ? to operate with nullable values.
                        final CollectionContract.View view = view();
                        if (view != null) {
                            view.showError();
                        }
                    }
                });
    }

    @Override
    public void openItemDetail(@NonNull RecAreaItem item) {
        activityRouter.openDetailForItem(item);
    }
}

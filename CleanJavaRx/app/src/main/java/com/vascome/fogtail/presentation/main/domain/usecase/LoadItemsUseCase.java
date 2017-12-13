package com.vascome.fogtail.presentation.main.domain.usecase;

import com.vascome.fogtail.data.gateway.ItemsDataSource;
import com.vascome.fogtail.presentation.base.domain.executor.ExecutionThread;
import com.vascome.fogtail.presentation.base.domain.executor.ThreadExecutor;
import com.vascome.fogtail.presentation.base.domain.interactor.UseCase;
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

public class LoadItemsUseCase extends UseCase<List<RecAreaItem>, Void> {

    private final ItemsDataSource source;

    @Inject
    LoadItemsUseCase(ItemsDataSource source, ThreadExecutor threadExecutor,
                ExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.source = source;
    }

    @Override
    protected Observable<List<RecAreaItem>> buildUseCaseObservable(Void unused) {
        return source.getItems();
    }
}

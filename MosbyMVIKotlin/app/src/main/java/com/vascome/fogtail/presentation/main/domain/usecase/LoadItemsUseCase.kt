package com.vascome.fogtail.presentation.main.domain.usecase

import com.vascome.fogtail.data.gateway.ItemsDataSource
import com.vascome.fogtail.presentation.base.domain.executor.ExecutionThread
import com.vascome.fogtail.presentation.base.domain.executor.ThreadExecutor
import com.vascome.fogtail.presentation.base.domain.interactor.UseCase
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

class LoadItemsUseCase
@Inject internal constructor(private val source: ItemsDataSource, threadExecutor: ThreadExecutor,
                     postExecutionThread: ExecutionThread)
    : UseCase<List<RecAreaItem>, Void>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Void?): Observable<List<RecAreaItem>> {
        return source.items.toObservable()
    }
}

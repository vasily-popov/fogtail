package com.vascome.fogtail.presentation.base.domain.executor;

import java.util.concurrent.Executor;

/**
 * Created by vasilypopov on 12/13/17
 * Copyright (c) 2017 CleanJavaRx. All rights reserved.
 */

/**
 * Executor implementation can be based on different frameworks or techniques of asynchronous
 * execution, but every implementation will execute the
 * {@link com.vascome.fogtail.presentation.base.domain.interactor.UseCase} out of the UI thread.
 */

public interface ThreadExecutor extends Executor {
}

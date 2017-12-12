package com.vascome.fogtail.screens.base.domain;

import android.os.Handler;

import com.vascome.fogtail.data.thread.ThreadScheduler;


/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

public class UseCaseHandler {

    private final Handler handler;
    private final ThreadScheduler useCaseScheduler;

    public UseCaseHandler(ThreadScheduler useCaseScheduler, Handler handler) {
        this.useCaseScheduler = useCaseScheduler;
        this.handler = handler;
    }

    public <T extends UseCase.RequestValues, R extends UseCase.ResponseValue> void execute(
            final UseCase<T, R> useCase, T values, UseCase.UseCaseCallback<R> callback) {
        useCase.setRequestValues(values);
        //useCase.setUseCaseCallback(callback);
        useCase.setUseCaseCallback(new UiCallbackWrapper(callback, handler));

        useCaseScheduler.execute(() -> useCase.run());
    }

    private static final class UiCallbackWrapper<V extends UseCase.ResponseValue> implements
            UseCase.UseCaseCallback<V> {
        private final UseCase.UseCaseCallback<V> callback;
        private final Handler handler;

        public UiCallbackWrapper(UseCase.UseCaseCallback<V> callback,
                                 Handler handler) {
            this.callback = callback;
            this.handler = handler;
        }

        @Override
        public void onSuccess(V response) {
            handler.post(()->callback.onSuccess(response));
        }

        @Override
        public void onError(Throwable t) {
            handler.post(()->callback.onError(t));
        }
    }

}

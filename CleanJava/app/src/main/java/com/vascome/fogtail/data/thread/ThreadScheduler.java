package com.vascome.fogtail.data.thread;

/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

public interface ThreadScheduler {

    void execute(Runnable runnable);

}

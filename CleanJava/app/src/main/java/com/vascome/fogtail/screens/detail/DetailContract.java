package com.vascome.fogtail.screens.detail;

import android.support.v4.app.Fragment;

import com.vascome.fogtail.api.entities.RecAreaItem;

import java.util.List;

/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

public interface DetailContract {

    interface View {
        void showItems(List<RecAreaItem> items);
    }

    interface Presenter {
    }
}

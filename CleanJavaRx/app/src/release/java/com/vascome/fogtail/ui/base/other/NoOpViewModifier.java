package com.vascome.fogtail.screens.base.other;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public class NoOpViewModifier implements ViewModifier {
    @NonNull
    @Override
    public <T extends View> T modify(@NonNull T view) {
        // no-op
        return view;
    }
}


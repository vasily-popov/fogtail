package com.vascome.fogtail.di.presentation.detail;

import android.support.annotation.NonNull;

import com.vascome.fogtail.di.FragmentScope;
import com.vascome.fogtail.presentation.detail.RecAreaDetailFragment;

import dagger.Subcomponent;

/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

@Subcomponent
@FragmentScope
public interface DetailFragmentComponent {

    void inject(@NonNull RecAreaDetailFragment fragment);
}

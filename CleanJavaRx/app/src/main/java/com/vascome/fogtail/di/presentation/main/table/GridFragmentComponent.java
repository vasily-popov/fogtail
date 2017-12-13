package com.vascome.fogtail.di.presentation.main.table;

import android.support.annotation.NonNull;

import com.vascome.fogtail.di.FragmentScope;
import com.vascome.fogtail.presentation.main.fragment.table.GridAppFragment;

import dagger.Subcomponent;

/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */

@Subcomponent
@FragmentScope
public interface GridFragmentComponent {

    void inject(@NonNull GridAppFragment itemsFragment);
}
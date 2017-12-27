package com.vascome.fogtail.presentation.main

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

interface CollectionView : MvpView {

    /**
     * The intent to load stuff on start
     *
     * @return The emitted item boolean can be ignored because it is always true
     */
    fun loadOnStartIntent(): Observable<Boolean>

    /**
     * The intent to react on pull-to-refresh
     *
     * @return The emitted item boolean can be ignored because it is always true
     */
    fun pullToRefreshIntent(): Observable<Boolean>

    /**
     * The intent to react on retry button click
     *
     * @return The emitted item boolean can be ignored because it is always true
     */
    fun retryButtonClickIntent(): Observable<Boolean>


    /**
     * Renders the viewState
     */
    fun render(viewState: CollectionViewState)
}
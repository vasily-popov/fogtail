package com.vascome.fogtail.presentation.detail

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter

import javax.inject.Inject

/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

class DetailPresenter
@Inject constructor() : MviBasePresenter<DetailContract.View, DetailViewState>() {
    override fun bindIntents() {
        //nothing here
    }
}
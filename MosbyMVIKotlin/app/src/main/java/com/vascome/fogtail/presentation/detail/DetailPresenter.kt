package com.vascome.fogtail.presentation.detail

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.vascome.fogtail.presentation.base.presenters.BasePresenter

import javax.inject.Inject

/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

class DetailPresenter
@Inject constructor() : MvpBasePresenter<DetailContract.View>(), DetailContract.Presenter

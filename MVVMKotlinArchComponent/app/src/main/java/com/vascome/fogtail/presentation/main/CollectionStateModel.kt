package com.vascome.fogtail.presentation.main

import com.vascome.fogtail.presentation.main.dto.RecAreaItem

/**
 * Created by vasilypopov on 12/25/17
 * Copyright (c) 2017 MVVMKotlin. All rights reserved.
 *
 *
 */
class CollectionStateModel
private constructor(val success: Boolean, val errorMessage: String?, val inProgress: Boolean) {

    lateinit var items: List<RecAreaItem>

    private constructor(success: Boolean, items: List<RecAreaItem>) : this(success, null, false) {
        this.items = items
    }

    companion object {

        fun inProgress() : CollectionStateModel {
            return CollectionStateModel(false, null, true)
        }

        fun success(items: List<RecAreaItem>): CollectionStateModel {
            return CollectionStateModel(true, items)
        }

        fun failure(errorMessage: String?) : CollectionStateModel {
            return CollectionStateModel(false, errorMessage, false)
        }
    }

}

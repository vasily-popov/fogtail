package com.vascome.fogtail.presentation.main

import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem
import java.util.*

/**
 * Created by vasilypopov on 12/27/17
 * Copyright (c) 2017 MosbyMVPKotlin. All rights reserved.
 *
 *
 */
class CollectionViewState private constructor(val loading:Boolean, val error:Throwable?, val data: List<RecAreaItem>?) {


    fun builder(): Builder {
        return Builder(this)
    }

    class Builder {
        private var loading: Boolean = false
        private var error: Throwable? = null
        private var data: List<RecAreaItem>? = null

        constructor()

        constructor(from: CollectionViewState) {
            if(from.data != null) {
                this.data = ArrayList(from.data)
            }
            this.error = from.error
            this.loading = from.loading
        }

        fun loading(loading: Boolean): Builder {
            this.loading = loading
            return this
        }

        fun error(error: Throwable): Builder {
            this.error = error
            return this
        }

        fun data(data: List<RecAreaItem>): Builder {
            this.data = data
            return this
        }

        fun build(): CollectionViewState {
            return CollectionViewState(loading,error,data)
        }
    }
}
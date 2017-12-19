package com.vascome.fogtail.data.network

import com.google.gson.TypeAdapterFactory
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

@GsonTypeAdapterFactory
abstract class EntityTypeAdapterFactory : TypeAdapterFactory {
    companion object {

        @JvmStatic fun create(): TypeAdapterFactory {
            return AutoValueGson_EntityTypeAdapterFactory()
        }
    }
}


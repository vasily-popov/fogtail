package com.vascome.fogtail.appmodules;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

@GsonTypeAdapterFactory
public abstract class EntityTypeAdapterFactory implements TypeAdapterFactory {

    public static TypeAdapterFactory create() {
        return new AutoValueGson_EntityTypeAdapterFactory();
    }
}


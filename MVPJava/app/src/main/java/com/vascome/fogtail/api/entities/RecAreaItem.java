package com.vascome.fogtail.api.entities;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;


/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

// This class is immutable, it has correctly implemented hashCode and equals.
// Thanks to AutoValue https://github.com/google/auto/tree/master/value.
@AutoValue
public abstract class RecAreaItem {
    private static final String JSON_PROPERTY_ID = "RecAreaID";
    private static final String JSON_PROPERTY_NAME = "RecAreaName";
    private static final String JSON_PROPERTY_PHONE = "RecAreaPhone";
    private static final String JSON_PROPERTY_IMAGE_URL = "imageURL";
    private static final String JSON_PROPERTY_DESCRIPTION = "RecAreaDescription";
    private static final String JSON_PROPERTY_DIRECTIONS = "RecAreaDirections";
    private static final String JSON_PROPERTY_AREA_LONGITUDE = "RecAreaLongitude";
    private static final String JSON_PROPERTY_AREA_LATITUDE = "RecAreaLatitude";


    @NonNull
    public static Builder builder() {
        return new AutoValue_RecAreaItem.Builder();
    }

    @NonNull
    public static TypeAdapter<RecAreaItem> typeAdapter(Gson gson) {
        return new AutoValue_RecAreaItem.GsonTypeAdapter(gson);
    }

    @NonNull
    @SerializedName(JSON_PROPERTY_ID)
    public abstract String id();

    @SerializedName(JSON_PROPERTY_IMAGE_URL)
    public abstract String imageUrl();

    @SerializedName(JSON_PROPERTY_NAME)
    public abstract String name();

    @SerializedName(JSON_PROPERTY_PHONE)
    public abstract String phone();

    @SerializedName(JSON_PROPERTY_DESCRIPTION)
    public abstract String shortDescription();

    @SerializedName(JSON_PROPERTY_DIRECTIONS)
    public abstract String directions();

    @SerializedName(JSON_PROPERTY_AREA_LONGITUDE)
    public abstract Double longitude();

    @SerializedName(JSON_PROPERTY_AREA_LATITUDE)
    public abstract Double latitude();

    @AutoValue.Builder
    public static abstract class Builder {

        @NonNull
        public abstract Builder id(@NonNull String id);

        @NonNull
        public abstract Builder imageUrl(@NonNull String imageUrl);

        @NonNull
        public abstract Builder name(@NonNull String name);

        @NonNull
        public abstract Builder shortDescription(@NonNull String shortDescription);

        @NonNull
        public abstract Builder phone(@NonNull String phone);

        @NonNull
        public abstract Builder directions(@NonNull String directions);

        @NonNull
        public abstract Builder longitude(@NonNull Double longitude);

        @NonNull
        public abstract Builder latitude(@NonNull Double latitude);

        @NonNull
        public abstract RecAreaItem build();
    }
}

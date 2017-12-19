package com.vascome.fogtail.presentation.main.dto;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
public abstract class RecAreaItem implements Parcelable {
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
        return new $$AutoValue_RecAreaItem.Builder();
    }

    @NonNull
    public static TypeAdapter<RecAreaItem> typeAdapter(Gson gson) {
        return new $AutoValue_RecAreaItem.GsonTypeAdapter(gson);
    }

    @NonNull
    @SerializedName(JSON_PROPERTY_ID)
    public abstract String id();

    @Nullable
    @SerializedName(JSON_PROPERTY_IMAGE_URL)
    public abstract String imageUrl();

    @SerializedName(JSON_PROPERTY_NAME)
    public abstract String name();

    @Nullable
    @SerializedName(JSON_PROPERTY_PHONE)
    public abstract String phone();

    @SerializedName(JSON_PROPERTY_DESCRIPTION)
    public abstract String shortDescription();

    @Nullable
    @SerializedName(JSON_PROPERTY_DIRECTIONS)
    public abstract String directions();

    @Nullable
    @SerializedName(JSON_PROPERTY_AREA_LONGITUDE)
    public abstract Double longitude();

    @Nullable
    @SerializedName(JSON_PROPERTY_AREA_LATITUDE)
    public abstract Double latitude();


    @AutoValue.Builder
    public static abstract class Builder {

        public abstract Builder id(String id);

        public abstract Builder imageUrl(String imageUrl);

        public abstract Builder name(String name);

        public abstract Builder shortDescription(String shortDescription);

        public abstract Builder phone(String phone);

        public abstract Builder directions(String directions);

        public abstract Builder longitude(Double longitude);

        public abstract Builder latitude(Double latitude);

        public abstract RecAreaItem build();
    }
}

package com.vascome.fogtail.presentation.main.dto

import android.annotation.SuppressLint
import android.os.Parcelable

import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

@SuppressLint("ParcelCreator")
@Parcelize
data class RecAreaItem(
    @field:Json(name = JSON_PROPERTY_ID) val id: String,
    @field:Json(name = JSON_PROPERTY_IMAGE_URL) val imageUrl: String?,
    @field:Json(name = JSON_PROPERTY_NAME) val name: String,
    @field:Json(name = JSON_PROPERTY_PHONE) val phone: String?,
    @field:Json(name = JSON_PROPERTY_DESCRIPTION) val shortDescription: String,
    @field:Json(name = JSON_PROPERTY_DIRECTIONS) val directions: String?,
    @field:Json(name = JSON_PROPERTY_AREA_LONGITUDE) val longitude: Double?,
    @field:Json(name = JSON_PROPERTY_AREA_LATITUDE) val latitude: Double?
) : Parcelable {

    private companion object {

        private const val JSON_PROPERTY_ID = "RecAreaID"
        private const val JSON_PROPERTY_NAME = "RecAreaName"
        private const val JSON_PROPERTY_PHONE = "RecAreaPhone"
        private const val JSON_PROPERTY_IMAGE_URL = "imageURL"
        private const val JSON_PROPERTY_DESCRIPTION = "RecAreaDescription"
        private const val JSON_PROPERTY_DIRECTIONS = "RecAreaDirections"
        private const val JSON_PROPERTY_AREA_LONGITUDE = "RecAreaLongitude"
        private const val JSON_PROPERTY_AREA_LATITUDE = "RecAreaLatitude"
    }
}

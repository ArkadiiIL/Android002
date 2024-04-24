package com.arkadii.android002.api.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BodyFavoriteDto(
    @SerializedName("media_type")
    @Expose
    val mediaType: String,

    @SerializedName("media_id")
    @Expose
    val mediaId: Int,

    @SerializedName("favorite")
    @Expose
    val favorite: Boolean
)

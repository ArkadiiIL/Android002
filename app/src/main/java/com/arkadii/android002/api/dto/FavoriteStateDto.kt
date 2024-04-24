package com.arkadii.android002.api.dto

import com.google.gson.annotations.SerializedName

data class FavoriteStateDto(
    @SerializedName("favorite")
    val isFavorite: Boolean
)

package com.arkadii.android002.api.dto

import com.google.gson.annotations.SerializedName

data class GenreDto(
    @SerializedName("name")
    val genreName: String
)
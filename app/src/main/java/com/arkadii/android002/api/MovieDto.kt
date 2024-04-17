package com.arkadii.android002.api

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id")
    val id: Number,
    @SerializedName("title")
    val name: String,
    @SerializedName("poster_path")
    val posterPath: String
)

package com.arkadii.android002.api

import com.google.gson.annotations.SerializedName

data class TvDto(
    @SerializedName("id")
    val id : Number,
    @SerializedName("name")
    val name : String,
    @SerializedName("poster_path")
    val posterPath : String
)

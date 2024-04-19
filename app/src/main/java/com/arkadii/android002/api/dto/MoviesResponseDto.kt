package com.arkadii.android002.api.dto

import com.google.gson.annotations.SerializedName

data class MoviesResponseDto(
    @SerializedName("results")
    val results: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPages: Number
)
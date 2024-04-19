package com.arkadii.android002.api.dto

import com.google.gson.annotations.SerializedName

data class TvResponseDto(
    @SerializedName("results")
    val results: List<TvDto>,
    @SerializedName("total_pages")
    val totalPages: Number
)
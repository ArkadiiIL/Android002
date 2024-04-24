package com.arkadii.android002.api.dto

import com.google.gson.annotations.SerializedName

data class FavoriteResponseDto(
    @SerializedName("success")
    val isSuccess: Boolean,
    @SerializedName("status_code")
    val statusCode: Int
)

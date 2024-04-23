package com.arkadii.android002.api.dto

import com.google.gson.annotations.SerializedName

data class TokenResponseDto(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("request_token")
    val requestToken: String?
)
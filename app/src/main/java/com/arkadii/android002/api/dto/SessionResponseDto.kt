package com.arkadii.android002.api.dto

import com.google.gson.annotations.SerializedName

data class SessionResponseDto(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("session_id")
    val sessionId: String?,
)
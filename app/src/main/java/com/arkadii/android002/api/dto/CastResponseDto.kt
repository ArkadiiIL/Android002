package com.arkadii.android002.api.dto

import com.google.gson.annotations.SerializedName

data class CastResponseDto(
    @SerializedName("cast")
    val castList: List<CastDto>
)

data class CastDto(
    @SerializedName("name")
    val name: String
)

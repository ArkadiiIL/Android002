package com.arkadii.android002.api.dto

import com.google.gson.annotations.SerializedName

data class AccountDetailsDto(
    @SerializedName("avatar")
    val avatar: AvatarDto,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("include_adult")
    val includeAdult: Boolean,
    @SerializedName("username")
    val username: String
)
data class AvatarDto(
    @SerializedName("tmdb")
    val avatarPath: AvatarPathDto
)

data class AvatarPathDto(
    @SerializedName("avatar_path")
    val avatarPath: String?
)
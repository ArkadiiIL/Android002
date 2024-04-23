package com.arkadii.android002.domain.data

data class User(
    val id: Int,
    val name: String,
    val userName: String,
    val includeAdult: Boolean,
    val avatarPath: String?
)
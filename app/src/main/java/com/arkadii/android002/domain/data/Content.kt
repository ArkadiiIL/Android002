package com.arkadii.android002.domain.data

data class Content(
    val id: Number,
    val name: String,
    val posterPath: String?,
    val popularity: Double,
    val isMovie: Boolean
)
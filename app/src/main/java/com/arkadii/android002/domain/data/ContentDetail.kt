package com.arkadii.android002.domain.data

data class ContentDetail(
    val name: String,
    val releaseYear: String,
    val runtime: String?,
    val numberOfEpisodes: String?,
    val posterPath: String?,
    val genres: String,
    val overview: String,
    val cast: String,
    val isMovie: Boolean
)

package com.arkadii.android002.api.dto

import com.google.gson.annotations.SerializedName

data class MovieDetailDto(
    @SerializedName("title")
    val name: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("genres")
    val genres: List<GenreDto>
)
package com.arkadii.android002.api.dto

import com.google.gson.annotations.SerializedName

data class TvDetailDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("first_air_date")
    val releaseDate: String,
    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("genres")
    val genres: List<GenreDto>
)

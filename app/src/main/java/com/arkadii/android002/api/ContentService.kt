package com.arkadii.android002.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ContentService {
    @GET("movie/popular")
    suspend fun getMoviePopularList(@Query("api_key") apiKey: String) : List<MovieDto>

    @GET("tv/popular")
    suspend fun getTvPopularList(@Query("api_key") apiKey: String) : List<TvDto>
}
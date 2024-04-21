package com.arkadii.android002.api

import com.arkadii.android002.api.dto.MoviesResponseDto
import com.arkadii.android002.api.dto.TvResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ContentService {
    @GET("movie/popular")
    suspend fun getMoviePopularList(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<MoviesResponseDto>

    @GET("tv/popular")
    suspend fun getTvPopularList(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<TvResponseDto>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("query") query: String
    ): Response<MoviesResponseDto>

    @GET("search/tv")
    suspend fun searchTv(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("query") query: String
    ): Response<TvResponseDto>
}
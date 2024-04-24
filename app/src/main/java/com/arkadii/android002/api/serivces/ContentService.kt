package com.arkadii.android002.api.serivces

import com.arkadii.android002.api.dto.CastResponseDto
import com.arkadii.android002.api.dto.MovieDetailDto
import com.arkadii.android002.api.dto.MoviesResponseDto
import com.arkadii.android002.api.dto.TvDetailDto
import com.arkadii.android002.api.dto.TvResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movie_id: Long,
        @Query("api_key") apiKey: String
    ): Response<MovieDetailDto>

    @GET("tv/{series_id}")
    suspend fun getTvDetail(
        @Path("series_id") series_id: Long,
        @Query("api_key") apiKey: String
    ): Response<TvDetailDto>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movie_id: Long,
        @Query("api_key") apiKey: String
    ): Response<CastResponseDto>

    @GET("tv/{series_id}/credits")
    suspend fun getTvCast(
        @Path("series_id") series_id: Long,
        @Query("api_key") apiKey: String
    ): Response<CastResponseDto>

    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavoriteMovies(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Query("api_key") apiKey: String
    ): Response<MoviesResponseDto>

    @GET("account/{account_id}/favorite/tv")
    suspend fun getFavoriteTv(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Query("api_key") apiKey: String
    ): Response<TvResponseDto>
}
package com.arkadii.android002.api.serivces

import com.arkadii.android002.api.dto.BodyFavoriteDto
import com.arkadii.android002.api.dto.FavoriteResponseDto
import com.arkadii.android002.api.dto.FavoriteStateDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FavoriteService {
    @POST("account/{account_id}/favorite")
    suspend fun updateFavoriteStatus(
        @Path("account_id") accountId: Int,
        @Body bodyFavorite: BodyFavoriteDto,
        @Query("session_id") sessionId: String,
        @Query("api_key") apiKey: String,
    ): Response<FavoriteResponseDto>

    @GET("movie/{movie_id}/account_states")
    suspend fun isFavoriteMovie(
        @Path("movie_id") movieId: Int,
        @Query("session_id") sessionId: String,
        @Query("api_key") apiKey: String
    ): Response<FavoriteStateDto>

    @GET("tv/{series_id}/account_states")
    suspend fun isFavoriteTv(
        @Path("series_id") seriesId: Int,
        @Query("session_id") sessionId: String,
        @Query("api_key") apiKey: String
    ): Response<FavoriteStateDto>
}
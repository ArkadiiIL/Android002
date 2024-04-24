package com.arkadii.android002.api.repositories

import com.arkadii.android002.BuildConfig
import com.arkadii.android002.api.dto.BodyFavoriteDto
import com.arkadii.android002.api.errors.FavoriteError
import com.arkadii.android002.api.serivces.FavoriteService
import com.arkadii.android002.domain.repositories.FavoriteRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FavoriteApiRepository : FavoriteRepository {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = BuildConfig.API_KEY
    private const val MEDIA_TYPE_MOVIE = "movie"
    private const val MEDIA_TYPE_TV = "tv"
    private const val ADD_TO_FAVORITE = true
    private const val REMOVE_FROM_FAVORITE = false

    private val favoriteService: FavoriteService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(FavoriteService::class.java)
    }

    override suspend fun addToFavorite(
        accountId: Int,
        sessionId: String,
        isMovie: Boolean,
        mediaId: Long
    ): Boolean {
        val bodyFavorite = BodyFavoriteDto(
            mediaType = if (isMovie) MEDIA_TYPE_MOVIE else MEDIA_TYPE_TV,
            mediaId = mediaId.toInt(),
            favorite = ADD_TO_FAVORITE
        )
        val response = favoriteService.updateFavoriteStatus(
            accountId = accountId,
            sessionId = sessionId,
            apiKey = API_KEY,
            bodyFavorite = bodyFavorite
        )
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.isSuccess
        } else throw FavoriteError.RequestFavoriteError
    }

    override suspend fun removeFromFavorite(
        accountId: Int,
        sessionId: String,
        isMovie: Boolean,
        mediaId: Long
    ): Boolean {
        val bodyFavorite = BodyFavoriteDto(
            mediaType = if (isMovie) MEDIA_TYPE_MOVIE else MEDIA_TYPE_TV,
            mediaId = mediaId.toInt(),
            favorite = REMOVE_FROM_FAVORITE
        )
        val response = favoriteService.updateFavoriteStatus(
            accountId = accountId,
            sessionId = sessionId,
            apiKey = API_KEY,
            bodyFavorite = bodyFavorite
        )
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.isSuccess
        } else throw FavoriteError.RequestFavoriteError
    }

    override suspend fun isFavorite(
        sessionId: String,
        isMovie: Boolean,
        mediaId: Long
    ): Boolean {
        val response = if (isMovie) {
            favoriteService.isFavoriteMovie(
                movieId = mediaId.toInt(),
                sessionId = sessionId,
                apiKey = API_KEY
            )
        } else {
            favoriteService.isFavoriteTv(
                seriesId = mediaId.toInt(),
                sessionId = sessionId,
                apiKey = API_KEY
            )
        }
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.isFavorite
        } else throw FavoriteError.RequestFavoriteError
    }
}
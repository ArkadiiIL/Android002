package com.arkadii.android002.domain.repositories

interface FavoriteRepository {
    suspend fun addToFavorite(
        accountId: Int,
        sessionId: String,
        isMovie: Boolean,
        mediaId: Long
    ): Boolean

    suspend fun removeFromFavorite(
        accountId: Int,
        sessionId: String,
        isMovie: Boolean,
        mediaId: Long
    ): Boolean

    suspend fun isFavorite(
        sessionId: String,
        isMovie: Boolean,
        mediaId: Long
    ): Boolean
}
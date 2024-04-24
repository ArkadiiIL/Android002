package com.arkadii.android002.domain.usecases

import com.arkadii.android002.domain.repositories.FavoriteRepository

class RemoveFromFavoriteUseCase(private val repository: FavoriteRepository) {
    suspend operator fun invoke(
        accountId: Int,
        sessionId: String,
        isMovie: Boolean,
        mediaId: Long
    ) = repository.removeFromFavorite(accountId, sessionId, isMovie, mediaId)
}
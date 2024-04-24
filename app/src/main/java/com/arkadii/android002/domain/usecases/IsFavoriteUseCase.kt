package com.arkadii.android002.domain.usecases

import com.arkadii.android002.domain.repositories.FavoriteRepository

class IsFavoriteUseCase(private val repository: FavoriteRepository) {
    suspend operator fun invoke(
        sessionId: String,
        isMovie: Boolean,
        mediaId: Long
    ) = repository.isFavorite(sessionId, isMovie, mediaId)
}
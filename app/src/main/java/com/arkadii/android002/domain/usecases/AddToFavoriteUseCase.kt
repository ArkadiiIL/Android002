package com.arkadii.android002.domain.usecases

import com.arkadii.android002.domain.repositories.FavoriteRepository

class AddToFavoriteUseCase(private val repository: FavoriteRepository) {
    suspend operator fun invoke(
        accountId: Int,
        sessionId: String,
        isMovie: Boolean,
        mediaId: Long
    ) = repository.addToFavorite(accountId, sessionId, isMovie, mediaId)
}
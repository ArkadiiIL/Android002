package com.arkadii.android002.domain.usecases

import com.arkadii.android002.domain.repositories.ContentRepository

class GetFavoriteMoviesListUseCase(private val repository: ContentRepository) {
    operator fun invoke(accountId: Int, sessionId: String) =
        repository.getFavoriteMoviesList(accountId, sessionId)
}
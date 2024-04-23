package com.arkadii.android002.domain.usecases

import com.arkadii.android002.domain.repositories.ContentRepository

class GetMoviesListByTitleUseCase(private val repository: ContentRepository) {
    operator fun invoke(title: String) = repository.getMoviesListByTitle(title)
}
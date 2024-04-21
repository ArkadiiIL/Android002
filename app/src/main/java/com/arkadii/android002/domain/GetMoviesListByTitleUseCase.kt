package com.arkadii.android002.domain

class GetMoviesListByTitleUseCase(private val repository: ContentRepository) {
    operator fun invoke(title: String) = repository.getMoviesListByTitle(title)
}
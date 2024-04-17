package com.arkadii.android002.domain

class GetPopularMoviesListUseCase(private val repository: ContentRepository) {
    operator fun invoke() = repository.getPopularMoviesList()
}
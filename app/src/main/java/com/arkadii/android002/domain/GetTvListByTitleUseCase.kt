package com.arkadii.android002.domain

class GetTvListByTitleUseCase(private val repository: ContentRepository) {
    operator fun invoke(title: String) = repository.getTvListByTitle(title)
}
package com.arkadii.android002.domain

class GetPopularTvListUseCase(private val repository: ContentRepository) {
    operator fun invoke() = repository.getPopularTvList()
}
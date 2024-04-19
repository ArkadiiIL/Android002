package com.arkadii.android002.domain

class GetPopularContentListUseCase(private val repository: ContentRepository) {
    operator fun invoke() = repository.getPopularContentList()
}
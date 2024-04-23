package com.arkadii.android002.domain.usecases

import com.arkadii.android002.domain.repositories.ContentRepository

class GetPopularContentListUseCase(private val repository: ContentRepository) {
    operator fun invoke() = repository.getPopularContentList()
}
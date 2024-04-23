package com.arkadii.android002.domain.usecases

import com.arkadii.android002.domain.repositories.ContentRepository

class GetContentDetailUseCase(private val repository: ContentRepository) {
    suspend operator fun invoke(content_id: Long, isMovie: Boolean) =
        repository.getContentDetail(content_id, isMovie)
}
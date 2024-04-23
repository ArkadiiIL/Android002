package com.arkadii.android002.domain.usecases

import com.arkadii.android002.domain.repositories.AuthenticationRepository

class GetUserUseCase(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(sessionId: String) = repository.getUser(sessionId)
}